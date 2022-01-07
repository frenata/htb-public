#!/bin/bash


# First we pull the log files from all three application servers
scp -o StrictHostKeyChecking=no -i /root/.ssh/id_rsa qtc@172.28.0.3:/opt/fatty/tar/logs.tar /root/client1/even
scp -o StrictHostKeyChecking=no -i /root/.ssh/id_rsa qtc@172.28.0.4:/opt/fatty/tar/logs.tar /root/client2/even
scp -o StrictHostKeyChecking=no -i /root/.ssh/id_rsa qtc@172.28.0.5:/opt/fatty/tar/logs.tar /root/client3/even

# In a real world scenario, we would just extract the contents directly. However, we need
# to clean our directories from time to time to allow exploitation after e.g. a misplaced
# symlink or something like this. Since the intended exploit requires two runs, cleaning
# the directories could interupt an exploit attempt. Therfore use a second directory, which
# triggers in the cleaning phase from the other one and vice versa. This way, each exploit
# attempt should be successful.
cp /root/client1/even/logs.tar /root/client1/odd/logs.tar
cp /root/client2/even/logs.tar /root/client2/odd/logs.tar
cp /root/client3/even/logs.tar /root/client3/odd/logs.tar

if [ -f /root/.clean-clients ]; then
        # If the .clean-clients file is present, we clean the even directories and extract the
        # .tar files from the odd directories.
        rm -rf /root/client1/even
        mkdir /root/client1/even
        rm -rf /root/client2/even
        mkdir /root/client2/even
        rm -rf /root/client3/even
        mkdir /root/client3/even

        cd /root/client1/odd/
        tar -xvf /root/client1/odd/logs.tar
        cd /root/client2/odd/
        tar -xvf /root/client2/odd/logs.tar
        cd /root/client3/odd/
        tar -xvf /root/client3/odd/logs.tar

        # Finally remove the .clean-clients file to switch cases
        rm /root/.clean-clients
else

        # If .cliean-clients is not present, we clean the odd directories and extract the
        # .tar files from the even directories
        rm -rf /root/client1/odd
        mkdir /root/client1/odd
        rm -rf /root/client2/odd
        mkdir /root/client2/odd
        rm -rf /root/client3/odd
        mkdir /root/client3/odd

        cd /root/client1/even/
        tar -xvf /root/client1/even/logs.tar
        cd /root/client2/even/
        tar -xvf /root/client2/even/logs.tar
        cd /root/client3/even/
        tar -xvf /root/client3/even/logs.tar

        # Finally we create the .clean-clients file to switch cases
        touch /root/.clean-clients
fi

