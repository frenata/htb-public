# Horizontall
## Enumeration

`nmap -sV -O $TARGET`

 * 22 (ssh, later)
 * 80 (nginx 1.14.0)
 * 8080 (php 7.4.22)

nginx vuln: https://www.cybersecurity-help.cz/vdb/SB2021052543 but looks difficult to exploit.

Given the presense of nginx, probably 8080 is only accessible from inside the machine and the first target is a web exploit

forwarding notes:
 * forward the ssh connection via the pentest server, as such
   `sudo ssh -L 80:10.10.11.105:80 kali@<pentest-ip>` then on the host configure a etc/hosts file to route horizontall.htb to 127.0.0.1

Unfortunately the website (which requires JS) doesn't seem to have any working parts. There's something that looks like a form but submitting it doesn't trigger any network requests -- it's just html with no action specified. Time to scan for more interesting paths on the web server.

`gobuster dir -u horizontall.htb -x php -r -w /usr/share/wordlists/dirb/common.txt`

Does gobuster recursively scan directories? Trying `feroxbuster` instead.

Nothing, although feroxbuster is nice.

Finally, poking around in a browser I can access the webpack js files. There's an interesting line in App.vue:

`axios.get('http://api-prod.horizontall.htb/reviews')`

We now have a new URL to attack after adding the subdomain to /etc/hosts

Accessing that site returns some json. The headers include `X-Powered-By: Strapi <strapi.io>` which might interesting to follow up on. The json doesn't look that interesting, so run directory scan on this new URL. It reveals /admin/

Strapi.io is a javascript based CMS. Can I find version info somewhere? If so there may be an exploit here. https://www.exploit-db.com/exploits/50239 looks promising!

More details of how exactly to exploit strapi: https://bittherapy.net/post/strapi-framework-remote-code-execution/

## Foothold

And with the strapi exploit, a simple nc listener and a OpenBSD nc connection via: https://highon.coffee/blog/reverse-shell-cheat-sheet/, we have a foothold as user strapi. The user seems to have ssh access so we can write an ssh key to authorized_keys and get a more stable shell.

user flag is in /home/developer -- nothing else there is accessible but there are some interesting looking files. Probably need to pivot to that user before escalating to root.

After exfiltrating some files, we have some mysql database credentials for the development environment for user `developer`. Can try to login to mysql or even directly switch users.

The database has a password hash for the admin user. Will try to use john to crack it.

Hashing ran dry -- finally remember to check for other open ports! Within the system with `netstat --listen` and see something on :8000. A couple of tunnels later and I see laravel. Look for an exploit.

## Escalation

Laravel does look exploitable, see https://www.ambionics.io/blog/laravel-debug-rce

This works fairly straightforwardly although the versions of python the exploit was written for didn't match the server so some light rework was necessary. The phar I prepped inserts my attack ssh key into root's authorized_key file which is an easy win.
