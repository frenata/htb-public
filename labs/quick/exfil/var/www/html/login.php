<?php 
include("db.php");
if(isset($_POST["email"]) && isset($_POST["password"]))
{
	$email=$_POST["email"];
	$password = $_POST["password"];
	$password = md5(crypt($password,'fa'));
        $stmt=$conn->prepare("select email,password from users where email=? and password=?");
        $stmt->bind_param("ss",$email,$password);
        $stmt->execute();
        $result = $stmt->get_result();
        $num_rows = $result->num_rows;
        if($num_rows > 0)
	{
		session_start();
		$_SESSION["loggedin"]=$email;
		header("location: home.php");
	}
	else
	{
		echo '<script>alert("Invalid Credentials");window.location.href="/login.php";</script>';
	}
}
else
{?>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Quick | Login Page</title>
<link href="https://fonts.googleapis.com/css?family=Raleway:300,400,600,700,800&display=swap" rel="stylesheet">
<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.1/css/all.min.css'>
<style>
body {
  background: #f9f9f9; /* fallback for old browsers */
  font-family: "Raleway", sans-serif;
  color: #151515;
}
a {
  color: black;
  font-weight: 600;
  font-size: 0.85em;
  text-decoration: none;
}
label {
  color: black;
  font-weight: 600;
  font-size: 0.85em;
}
input:focus {
  outline: none;
}
.container {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100vh;
}
.form {
  display: flex;
  width: auto;
  background: #fff;
  margin: 15px;
  padding: 25px;
  border-radius: 25px;
  box-shadow: 0px 10px 25px 5px #0000000f;
}
.sign-in-section {
  padding: 30px;
}
.sign-in-section h6 {
  margin-top: 0px;
  font-size: 0.75em;
}
.sign-in-section h1 {
  text-align: center;
  font-weight: 700;
  position: relative;
}
.sign-in-section h1:after {
  position: absolute;
  content: "";
  height: 5px;
  bottom: -15px;
  margin: 0 auto;
  left: 0;
  right: 0;
  width: 40px;
  background: #7F00FF;
  background: -webkit-linear-gradient(to right, #E100FF, #7F00FF);
  background: linear-gradient(to right, #E100FF, #7F00FF);
  -o-transition: 0.25s;
  -ms-transition: 0.25s;
  -moz-transition: 0.25s;
  -webkit-transition: 0.25s;
  transition: 0.25s;
}
.sign-in-section h1:hover:after {
  width: 100px;
}
.sign-in-section ul {
  list-style: none;
  padding: 0;
  margin: 0;
  text-align: center;
}
.sign-in-section ul > li {
  display: inline-block;
  padding: 10px;
  font-size: 15px;
  width: 20px;
  text-align: center;
  text-decoration: none;
  margin: 5px 2px;
  border-radius: 50%;
  box-shadow: 0px 3px 1px #0000000f;
  border: 1px solid #e2e2e2;
}
.sign-in-section p {
  text-align: center;
  font-size: 0.85em;
}
.form-field {
  display: block;
  width: 300px;
  margin: 10px auto;
}
.form-field label {
  display: block;
  margin-bottom: 10px;
}
.form-field input[type="email"],
input[type="password"] {
  width: -webkit-fill-available;
  padding: 15px;
  border-radius: 10px;
  border: 1px solid #e8e8e8;
}
.form-field input::placeholder {
  color: #e8e8e8;
}
.form-field input:focus {
  border: 1px solid #AE00FF;
}
.form-field input[type="checkbox"] {
  display: inline-block;
}
.form-options {
  display: block;
  margin: auto;
  width: 300px;
}
.checkbox-field {
  display: inline-block;
  float: left;
}
.form-options a {
  float: right;
  text-decoration: none;
}
.btn {
  padding: 15px;
  font-size: 1em;
  width: 100%;
  border-radius: 25px;
  border: none;
  margin: 20px 0px;
}
.btn-signin {
  cursor: pointer;
  background: #7F00FF;
  background: -webkit-linear-gradient(to right, #E100FF, #7F00FF);
  background: linear-gradient(to right, #E100FF, #7F00FF);
  box-shadow: 0px 5px 15px 5px #840fe440;
  color: #fff;
}
.links a:nth-child(1) {
  float: left;
}
.links a:nth-child(2) {
  float: right;
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
<div class="container">
<div class="form">
<div class="sign-in-section">
<h1>Ticketing System | Log in</h1>
 <ul>
<li><i class="fab fa-facebook-f"></i></li>
<li><i class="fab fa-linkedin-in"></i></li>
<li><i class="fab fa-twitter"></i></li>
</ul>
<p>or use your email</p>
<form action="" method="POST">
<div class="form-field">
<label for="email">Email</label>
<input id="email" type="email" name="email" placeholder="Email" />
</div>
<div class="form-field">
<label for="password">Password</label>
<input id="password" type="password" name="password" placeholder="Password" />
</div>
<div class="form-options">
<div class="checkbox-field">
<input id="rememberMe" type="checkbox" class="checkbox" />
<label for="rememberMe">Remember Me</label>
</div>
<a href="#">Forgot Password?</a>
</div>
<div class="form-field">
<input type="submit" class="btn btn-signin" value="Submit" />
</div>
</form>
<div class="links">
<a href="#">Privacy Policy</a>
<a href="#">Terms & Conditions</a>
</div>
</div>
</div>
</div>
</body>
</html>
<?php } ?>
