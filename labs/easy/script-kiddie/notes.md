## Enum
ssh and http over port 5000

## RCE

Can run commands on server:
 * nmap
 * searchsploit
 * msfvenom

msfvenom allows a template file, which some googling reveals an available command injection exploit for: https://www.exploit-db.com/exploits/49491

With that script, a first POC to get it to create a file under `static` is easy.
`nc` and friends don't work directly, so use RCE to do discovery work with `ls`.

## Foothold

After observing that `.ssh/authorizedd_keys` exists and is writable, generate a keypair and inject the public half, then gain a secure ssh foothold as `kid`.

## Privilege Escalation

`kid` doesn't seem to have *too* many privileges, but we can extract the web page application logic at this point. Certain malformed form submissions write to a file logs/hackers and give a warning that 'you'll be hacked back'.

Poking around `/home` the `pwn` user looks interesting and runs a script (with cron/inotify?) that *reads* from logs/hacker and uses the read data inside a `sh -c` block. More RCE!

`nc` doesn't work because this version doesn't have `-e`, but a python reverse shell placed in the hacker log can interrupt the expected command and gain a reverse shell.

## Root
As `pwn`, `sudo -l` happily succeeds and reports that we can run `msfconsole` as root without a password prompt. :) Once in, we can run arbitrary commands in msfconsole, notably reading `/root/root.txt`. Gaining a full shell wouldn't be too much harder -- we'd just have to cat a pubkey to authorized_keys.
