## Foothold

Testing/Main sites
Looking at the testing site, there's an old settings file that's readable -- the password is valid for the admin on the main site.

Given an admin login, msfconsole can generate and execute an exploit payload.

## PrivEsc

Looking around, this appears to be a ChromeOS box, given the presence of `/home/chronos`
`linpeas.sh` reveals a plaintext password in /etc/autologin/passwd, it's `katie`.

## Root

katie has sudo access to initctl, so what can we do with it?
Apparently start/stop services as defined in /etc/init, a bunch of which are 'test' services and writeable by katie.

The services are executed by the init script aka root. It would easy to get a full shell at this point (pop a rev shell as root), but it's enough to just cat out root.txt to a readable location. :)
