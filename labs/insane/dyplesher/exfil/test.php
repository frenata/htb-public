<?php

// connect to your memcache server
$m = new Memcached;
$m->setOption(Memcached::OPT_BINARY_PROTOCOL, true);
$m->addServer("10.10.10.190", "11211");
$m->setSaslAuthData("felamos", "zxcvbnm");

$m->set('foo', 'bar');
echo $m->get('bundled');
echo "\n";
#$keys = $m->getAllKeys();
#var_dump($keys);
#var_dump($m->getStats());

?>
