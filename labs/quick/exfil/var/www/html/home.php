<?php
include("db.php");
session_start();
if($_SESSION["loggedin"])
{
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

input {
  font-size: 1.3rem;
  padding: .8rem .6rem;
  width: 40rem;
  margin-right: 1rem;
  border-radius: .6rem;
  line-height: 1.8;
  border: none;
  outline: none;
  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.15), inset 0 1px 2px 0 rgba(255, 255, 255, 0.35);
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
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
        <script type="text/javascript">
            function getData(divid){
                var search = document.getElementById("search");
                $.ajax({
                    url: 'http://quick.htb:9001/search.php?search='+search.value,
                    success: function(html) {
                        var ajaxDisplay = document.getElementById(divid);
                        ajaxDisplay.innerHTML = html;
                    }
                });
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
<a href="" class="nav__list--link">Home</a>
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
<div class="wrapper">
<header class="header">
<div class="header__heading">
Track your Tickets
</div>
<div class="header__content">
Search with assigned ticket id
</div>
<div class="header__search">
<input type="text" placeholder="search..." name="search" id="search">
<button class="btn white" onclick="getData('displaydata')">Search</button>
</div>
<div id="displaydata">
</div>
</header>
<section class="presentaion">
<div class="card">
<div class="card__body">
<div class="card__logo">
<svg width="24" height="24" fill="#262626" ariaLabel="Find People" class="_8-yf5" viewBox="0 0 48 48"><path fill-rule="evenodd" d="M24 .5C37 .5 47.5 11 47.5 24S37 47.5 24 47.5.5 37 .5 24 11 .5 24 .5zm0 44c11.3 0 20.5-9.2 20.5-20.5S35.3 3.5 24 3.5 3.5 12.7 3.5 24 12.7 44.5 24 44.5zm-4.4-23.7c.3-.5.7-.9 1.2-1.2l14.8-8.1c.3-.1.6-.1.8.1.2.2.3.5.1.8l-8.1 14.8c-.3.5-.7.9-1.2 1.2l-14.8 8.1c-.3.1-.6.1-.8-.1-.2-.2-.3-.5-.1-.8l8.1-14.8zm6.2 5l4.3-7.8-7.8 4.3 3.5 3.5z" clip-rule="evenodd"></path></svg>
</div>
<div class="card__header">Our Services</div>
<div class="card__content">
We operate around the Globe. You can contact us to know more about our services.
</div>
</div>
</div>
<div class="card title">
<div class="card__title">
Quick | Resistant | PowerFul
</div>
<div class="card__body">
<div class="card__logo">
<svg width="24" height="24" fill="#262626" ariaLabel="Activity Feed" class="_8-yf5" viewBox="0 0 48 48"><path fill-rule="evenodd" d="M34.3 3.5C27.2 3.5 24 8.8 24 8.8s-3.2-5.3-10.3-5.3C6.4 3.5.5 9.9.5 17.8s6.1 12.4 12.2 17.8c9.2 8.2 9.8 8.9 11.3 8.9s2.1-.7 11.3-8.9c6.2-5.5 12.2-10 12.2-17.8 0-7.9-5.9-14.3-13.2-14.3zm-1 29.8c-5.4 4.8-8.3 7.5-9.3 8.1-1-.7-4.6-3.9-9.3-8.1-5.5-4.9-11.2-9-11.2-15.6 0-6.2 4.6-11.3 10.2-11.3 4.1 0 6.3 2 7.9 4.2 3.6 5.1 1.2 5.1 4.8 0 1.6-2.2 3.8-4.2 7.9-4.2 5.6 0 10.2 5.1 10.2 11.3 0 6.7-5.7 10.8-11.2 15.6z" clip-rule="evenodd"></path></svg>
</div>
<div class="card__header">Love to Help</div>
<div class="card__content">
As customer is utmost care to us, we don't hesitate to resolve ur issues.
</div>
</div>
</div>
<div class="card">
<div class="card__body">
<div class="card__logo">
<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 34.108 30.55"><g data-name="Group 14924" transform="translate(-200.233 -559.29)"><ellipse cx="6.552" cy="7.332" data-name="Ellipse 686" rx="6.552" ry="7.332" transform="translate(205.215 559.29)"></ellipse><ellipse cx="4.624" cy="5.174" data-name="Ellipse 687" rx="4.624" ry="5.174" transform="translate(221.946 563.919)"></ellipse><path d="M223.157 576.496c-2.673.081 3.1 2.629 1.725 9.979 0 0 7.077.711 8.781-2.317s.193-7.662-4.446-7.662c-4.827 0-3.387-.081-6.06 0z" data-name="Path 29499"></path><path d="M221.9 580.47c-.616-1.844-2.388-2.884-4.008-3.464a12.834 12.834 0 00-4.323-.7h-4.058a12.824 12.824 0 00-4.321.7c-1.62.58-3.394 1.62-4.01 3.464-1.228 3.69-2.648 9.556 5.582 9.366h9.556c8.227.189 6.809-5.677 5.582-9.366z" data-name="Path 29500"></path></g></svg>
</div>
<div class="card__header">Chat</div>
<div class="card__content">
Oh yea! we are working on design at the moment. 
</div>
</div>
</div>
</section>
</div>
</body>
</html>


<?php
}
else
{
	echo '<script>alert("Invalid Username/Password");window.location.href="/login.php";</script>';
}
?>
