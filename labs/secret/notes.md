# Secret
## Enumeration

 * 22   (openssh 8.2p1)
 * 80   (nginx 1.18.0)
 * 3000 (node/express)

80/3000 seem to be serving the same content -- "DumbDocs" some kind of API documentation page.

Potentially interesting tidbits:

 * "Xioaying Riley at 3rd Wave Media"
 * there's a search bar, although it seems to redirect to the main page (check for sqli though)
 * there's a download for the source
 * there's instructions in the docs for how to self-register, login, and check role

## Foothold

When downloading the files, it's a git repo with "removed .env for security reasons" hahahaha. Sure enough the .env file has the token secret, so the path to admin is now straightforward: register, login, then rewrite the jwt.

With a working exploit to get admin, it's time to look through the source code to find out what kind of privileges admin has.
 * /logs gets git logs for a requested file
	const getLogs = `git log --oneline ${file}`;

We have RCE via command injection! Just have the value of 'file' be '&& <cmd-to-inject>'

Make the injected command write an ssh key to authorized_keys and we have a foothold as 'dasith'.

## Escalation

Enumerate again:
 * No sudo -l without password privilege.
 * linpeas
 * pspy: nothing interesting :(
 * Can connect to mongodb and extract a few hashes. Run them all through john and work on other stuff. None of them crack. :(

/opt/count is a setuid binary -- looking at the code it doesn't appear directly vulnerable to buffer overflows, but it can produce a coredump while running as root.

See: https://alephsecurity.com/2021/02/16/apport-lpe/ for possibly useful information.

Steps:
 1. run /opt/count until it gets to the question about writing to a file (at this point it should be dumpable per the code)
 2. From another terminal, kill the process with -QUIT to dump it to /var/crash
 3. Use apport-unpack to unpack the crash file into a directory.
 4. ??? spend way too long trying to find interesting info via gdb.
 5. run `strings CoreDump` and observe that there is root's ssh key directly in the coredump!
