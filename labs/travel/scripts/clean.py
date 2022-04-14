import ldap3
server = ldap3.Server("ldap.travel.htb", get_info=ldap3.ALL)
connection = ldap3.Connection(server, 'cn=lynik-admin,dc=travel,dc=htb', 'Theroadlesstraveled', auto_bind=True)
connection.modify('uid=christopher,ou=users,ou=linux,ou=servers,dc=travel,dc=htb',{'givenName': [(ldap3.MODIFY_REPLACE, ['Chris'])], 'homeDirectory': [(ldap3.MODIFY_REPLACE, ['/home/christopher'])],  'uidNumber' : [(ldap3.MODIFY_REPLACE, ['5003'])], 'userPassword': [(ldap3.MODIFY_DELETE, ['1stepcloser'])], 'uid': [(ldap3.MODIFY_REPLACE, ['christopher'])],})
connection.result
