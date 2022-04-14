rpcclient -U "" -N $TARGET -c enumdomusers | awk '{$a=substr($2,6); $b=substr($a,1,length($a)-1); print $b}' > .tmp

while read -r rid
do
  rpcclient -U "" -N $TARGET -c "queryuser $rid" >> user-details.txt
done < .tmp
rm .tmp
