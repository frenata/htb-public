import requests

session = requests.Session()

res = None
while res is None or res.status_code != 200:
    res = session.post("http://printerv2.quick.htb:9001/index.php",
                    data={"email": "srvadm@quick.htb",
                            "password": "Quick4cc3$$"})

#print(res)
#print(res.text)

#res = session.get("http://printerv2.quick.htb:9001/printers.php?title=test&job=print")

# print(res)
# print(res.text)

res = session.post("http://printerv2.quick.htb:9001/job.php",
                   data={"title":"test",
                         "desc": "foo",
                         "submit": "submit"})

print(res)
print(res.text)
