## Enum

webapp built with AdminLTE

Vulnerable to SQLI, can extract some usernames and an (apparently uncrackable) hash

Another webapp at staging.love.htb, at /beta.php it will GET an arbitrary URL, so we can upload arbitrary files. If we knew where they were located, that would be enough for a rev shell.

There also seem to be HTTP servers running at 443 and 5000 but they return 403.
/examples on all pages returns 503, which is interesting, but not obviously exploitable.

## Foothold

Abuse file scanner by specifying a localhost address, and try to retrieve a page not otherwise accessible from outside the network

Tell it to scan: 127.0.0.1:5000/index.php, and this prints the admin creds. :)
Now love.htb/admin is enterable with these creds.

Location of at least one file on the server:
C:\xampp\htdocs\omrs\admin\voters_edit.php

Adding a new voter lets us specify a picture for them, so we'll add a php rev shell, to be executed by visiting love.htb/images/shell

RCE!

## Root

winpeas flags 'always install elevated', can't get the msi installed but can use msfconsole to apply the exploit.

https://pentestlab.blog/2017/02/28/always-install-elevated/
