## Enum

22, 80, 10010 (unknown and filtered)

## Foothold

80 has a webserver that you can register for and login. After registration there's a note adding app  -- notably the cookie decodes with an admin=false and a URL to the signing key on localhost:7070. If we can exfiltrate this key we can create admin credentials.

Other users notes are also readable without auth, but they are all driven via URL so there will be no easy way to discover which user is which.

There must be a way to do a SSI of the keyfile.
