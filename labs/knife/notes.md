## Enum

Not too much, just 22 and 80.

Apaches 2.4.41 and PHP 8.1.0-dev

The latter has a CVE!
https://packetstormsecurity.com/files/162749/PHP-8.1.0-dev-Backdoor-Remote-Command-Injection.html

## Foothold

Given the exploit CVE, `id` user, exfiltration their ssh key and add their pubkey to authorized_keys on the server.

## Root

James can run Chef's knife command with sudo privileges, which among other things allows arbitrary exec: 
https://docs.chef.io/workstation/knife_exec/

So the following command owns the box: `sudo knife exec -E 'system("bash")'`
