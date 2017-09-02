<!doctype html>
<!-- Website Template by freewebsitetemplates.com -->
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Bebabeba login</title>
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<link rel="stylesheet" type="text/css" href="css/mobile.css" media="screen and (max-width : 568px)">
	<script type="text/javascript" src="js/mobile.js"></script>
</head>
<body>
	<div id="header">
		<a href="index.html" class="logo">
			<img src="images/logo.jpg" alt="">
		</a>
		<ul id="navigation">
			<li class="selected">
				<a href="index.html">home</a>
			</li>
			<li>
				<a href="drivers.html">register</a>
			</li>
			<li>
				<a href="passengers.html">login</a>
			</li>
			<li>
				<a href="payments.html">about</a>
			</li>
			<li>
				<a href="bookings.html">contacts</a>
			</li>

		</ul>
	</div>
	<div id="body">
		<div id="featured">
			<img src="images/background.jpg" alt="">
			<div>
				<form action="registration.php" method="post" name="myForm" onload="ClearForm()" onsubmit="return checkMe()" id="form">
					<fieldset>
                        <legend>Contact details</legend>
                        <label>Your name:</label><br>
                        <input type="text" name="name" maxlength="20" size="25"><br>
                        <label>Email:</label><br>
                        <input type="email" name="email" size="25"><br>
                        <label>Password</label><br>
                        <input type="password" name="password1" size="25" value=""><br>
                        <label>Password again</label><br>
                        <input type="password" name="password2" size="25"><br>
                        <br>
                        <button id="submitbutton">Submit</button>
                        <button type="reset">Reset</button>
					</fieldset>
				</form>
			</div>
		</div>
		<ul>
			<li>
				<a href="drivers.html">
					<img src="images/drivers.jpg" alt="">
					<span>DRIVER</span>
				</a>
			</li>
			<li>
				<a href="passengers.html">
					<img src="images/passengers.jpg" alt="">
					<span>PASSENGER</span>
				</a>
			</li>
			<li>
				<a href="payments.html">
					<img src="images/payments.jpg" alt="">
					<span>PAYMENT</span>
				</a>
			</li>
		</ul>
	</div>
	<div id="footer">
		<div>
			<p>&copy; 2016 by Wambugu Mathai. All rights reserved.</p>
			<ul>
				<li>
					<a href="" id="twitter">twitter</a>
				</li>
				<li>
					<a href="" id="facebook">facebook</a>
				</li>
				<li>
					<a href="" id="googleplus">googleplus</a>
				</li>
				<li>
					<a href="" id="pinterest">pinterest</a>
				</li>
			</ul>
		</div>
	</div>
</body>
</html>