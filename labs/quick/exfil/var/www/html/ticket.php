<?php
include("db.php");
session_start();
if($_SESSION["loggedin"])
{
		if($_SERVER['REQUEST_METHOD'] === 'POST')
		{
			$msg=$_POST["msg"];
			$title=$_POST["title"];
			$status="open";
			$id = "%{$_POST['id']}%";
			$stmt=$conn->prepare("select id,title,description,status from tickets where id like ?");
			$stmt->bind_param("s",$id);
			$stmt->execute();
			$result = $stmt->get_result();
			$num_rows = $result->num_rows;
			if($num_rows > 0)
			{
				echo '<script>alert("This ticket exists. Please raise new one");window.location.href="/ticket.php";</script>';
			}
			else
			{
				$id=$_POST["id"];
				$stmt1=$conn->prepare("insert into tickets values(?, ?, ?, ?)");
				$stmt1->bind_param("ssss",$id,$title,$msg,$status);
				$stmt1->execute();
				echo '<script>alert("Ticket NO : \"'.$id.'\" raised. We will answer you as soon as possible");window.location.href="/home.php";</script>';
			}
		}
		else
		{
			$id=rand(1000,10000);
			$id = "TKT-".$id;
?>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Quick | Ticketing System</title>
<style>
/*
0 - 600px:      Phone
600 - 900px:    Tablet portrait
900 - 1200px:   Tablet landscape
[1200 - 1800] is where our normal styles apply
1800px + :      Big desktop

$breakpoint arguement choices:
- phone
- tab-port
- tab-land
- big-desktop

ORDER: Base + typography > general layout + grid > page layout > components

1em = 16px
*/
*,
*::before,
*::after {
  padding: 0;
  margin: 0;
  box-sizing: inherit;
}

html {
  font-size: 62.5%;
}
@media only screen and (max-width: 75em) {
  html {
    font-size: 56.25%;
  }
}
@media only screen and (max-width: tab-land) {
  html {
    font-size: 56.25%;
  }
}
@media only screen and (max-width: 56.25em) {
  html {
    font-size: 50%;
  }
}
@media only screen and (max-width: tab-port) {
  html {
    font-size: 50%;
  }
}
@media only screen and (max-width: 112.5em) {
  html {
    font-size: 75%;
  }
}
@media only screen and (max-width: big-desktop) {
  html {
    font-size: 75%;
  }
}

body {
  min-height: 100vh;
  overflow-x: hidden;
  box-sizing: border-box;
  background: #fff;
  line-height: 1.5;
  font-family: "Helvetica","Arial",sans-serif;
}

ul {
  list-style: none;
}

table {
  font-family: arial, sans-serif;
  border-collapse: collapse;
  width: 100%;
}

img {
  max-width: 100%;
  display: block;
}

.nav__container {
  padding: 2rem 4rem;
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.nav__left {
  color: #7548f6;
  font-weight: 400;
  font-size: 2rem;
}
.nav__list {
  display: flex;
  align-items: center;
}
.nav__list--item:not(:last-child) {
  margin-right: .3rem;
}
.nav__list--link {
  text-decoration: none;
  color: rgba(0, 0, 0, 0.7);
  font-size: 1.6rem;
  font-weight: 400;
  padding: .6rem .8rem;
  transition: all .3s linear;
  font-family: 'Cairo';
}
.nav__list--link:hover {
  color: #7548f6;
}
.nav__response {
  display: flex;
  flex: 1;
  align-items: center;
  justify-content: space-between;
}

.wrapper {
  width: 100%;
  max-width: 1350px;
  margin: 0 auto;
}

h1 {
  color: #fff;
  font-weight: 400;
  font-size: 1.8rem;
}

.header {
  background: linear-gradient(90deg, #9C7CF8, #7548F6) 100% 30%/70px 60px no-repeat, linear-gradient(90deg, rgba(156, 124, 248, 0.1), rgba(117, 72, 246, 0.1)) 100% 28%/90px 60px no-repeat, linear-gradient(80deg, #1F1737, #1C0D43) 0 0/100% calc(100% - 20px) no-repeat, linear-gradient(90deg, #9C7CF8, #7548F6) 0 100%/35vw 20px no-repeat, linear-gradient(90deg, rgba(156, 124, 248, 0.12), rgba(117, 72, 246, 0.12)) 0 calc(100% - 5px)/39vw 20px no-repeat;
  height: 40rem;
  margin: 0rem 4rem;
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
  border-radius: 2rem;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}
.header__heading {
  font-weight: 500;
  color: #fff;
  font-size: 4rem;
  letter-spacing: 1px;
  font-family: "Roboto";
}
.header__content {
  font-weight: 300;
  color: #96A1B0;
  width: 30rem;
  text-align: center;
  font-size: 1.4rem;
  padding: 1rem;
}
.header__search {
  display: flex;
  align-items: center;
}

.btn {
  padding: .7rem 1.6rem;
  font-weight: 600;
  font-size: 1.4rem;
  color: #fff;
  background: #7548f6;
  border: none;
  letter-spacing: 1px;
  border-radius: .6rem;
  cursor: pointer;
  outline: none;
  transition: all 0.3s ease;
  line-height: 1.8;
  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.15), inset 0 1px 2px 0 rgba(255, 255, 255, 0.35);
  font-family: 'Cairo';
}
.btn.white {
  color: #7548f6;
  background: #fff;
}
.btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 4px 0 rgba(0, 0, 0, 0.2), inset 0 1px 1px 0 rgba(255, 255, 255, 0.5);
}
.btn:active {
  transform: translateY(0);
  box-shadow: 0 1px 1px 0 rgba(255, 255, 255, 0.2), inset 0 2px 4px 0 rgba(0, 0, 0, 0.4);
}

table {
  font-family: arial, sans-serif;
  border-collapse: collapse;
  width: 40%;
}

td, th {
  border: 1px solid #dddddd;
  text-align: left;
  padding: 8px;
}

tr:nth-child(even) {
  background-color: #dddddd;
}

.presentaion {
  padding: 4rem;
  display: flex;
  justify-content: space-between;
}
.presentaion .card__body {
  padding: 4rem 2rem;
  width: 25rem;
  background: #fff;
  box-shadow: 0 4px 4px 0 rgba(0, 0, 0, 0.2), inset 0 1px 1px 0 rgba(255, 255, 255, 0.5);
  display: flex;
  flex-direction: column;
  align-items: center;
  border-radius: 1rem;
}
.presentaion .card__logo svg {
  width: 5rem;
  height: 5rem;
  fill: #7548f6;
}
.presentaion .card__header {
  font-size: 1.8rem;
  color: rgba(0, 0, 0, 0.9);
  font-weight: 400;
  text-transform: capitalize;
  font-family: 'lato';
  padding: 1rem 0;
}
.presentaion .card__content {
  font-size: 1.4rem;
  text-align: center;
}
.presentaion .card__title {
  font-size: 1.8rem;
  font-weight: 400;
  color: #7548f6;
  text-align: center;
  padding: 0 0 2rem 0;
}
.presentaion .card.title {
  width: 40rem;
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>
<script>
  window.console = window.console || function(t) {};
</script>
<script>
  if (document.location.search.match(/type=embed/gi)) {
    window.parent.postMessage("resize", "*");
  }
</script>
</head>
<body translate="no">
<nav class="nav">
<div class="wrapper">
<div class="nav__container">
<div class="nav__left">
Quick | Ticketing System
</div>
<div class="nav__center">
<ul class="nav__list">
<li class="nav__list--item">
<a href="home.php" class="nav__list--link">Home</a>
<a href="ticket.php" class="nav__list--link">Raise Ticket</a>
</li>
</ul>
</div>
<div class="nav__right">
<button class="btn">
Loggedin as Elisa
</button>
</div>
</div>
</div>
</nav>
</div><form action="" method="POST">
<center><br /><br /><table  border="0"><tr><td align="center" >Title:</td><td align="right" ><input type="text" name="title"/></td></tr>
<tr><td align="center">Message:</td><td align="right" ><textarea rows="7" cols="50" name="msg">Describe your query</textarea></td></tr>
<tr><td align="center" colspan="3"><input type="submit"  value="Submit"/></td></tr>
</table>
<input type="hidden" name="id" value="<?php echo $id?>"/>
</form>
</div>
</header>
</div>
</div>
</body>
</html>


<?php }
}
else
{
	echo '<script>alert("Invalid Username/Password");window.location.href="/login.php";</script>';
}
?>
