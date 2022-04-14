#!/bin/bash
set -xe

log=$1

cat $log | cut -d' ' -f3- | sort -u | while read ip; do
   # sh -c "nmap --top-ports 10 -oN recon/${ip}.nmap ${ip} " &
    sh -c "nmap --top-ports 10 -oN recon/${ip}.nmap ${ip} 2>&1 >/dev/null" &
done
