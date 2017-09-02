<!doctype html>
<!-- Website Template by freewebsitetemplates.com -->
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Drivers - Registered users</title>
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<link rel="stylesheet" type="text/css" href="css/mobile.css" media="screen and (max-width : 568px)">
	<script type="text/javascript" src="../js/mobile.js"></script>
</head>
<body>
	<div id="header">
		<a href="homepage.html" class="logo">
			<img src="images/logo.png" alt="">
		</a>
		<form action="" method="post">
			<input type="search" name="search" class="searchfield" size="50" />
			<input type="submit" value="Search" class="searchbutton" />
		</form>
		<br>
		<ul id="navigation">
			<li>
				<a href="homepage.html">home</a>
			</li>
			<li class="selected">
				<a href="drivers.html">drivers</a>
			</li>
			<li>
				<a href="passengers.php">passengers</a>
			</li>
			<li>
				<a href="payments.html">payments</a>
			</li>
			<li>
				<a href="bookings.html">bookings</a>
			</li>
			<li>
				<a href="reports.html">reports</a>
			</li>
			<li>
				<a href="feedback.html">feedback</a>
			</li>
			<li>
				<a href="help.html">help</a>
			</li>
		</ul>
	</div>
	<div id="body">
		<h1><span>REGISTERED DRIVERS</span></h1>
		<div>
			<img src="images/driverpage.jpg" alt="">

			<?php
	
				// Create connection
				$conn = new mysqli("localhost","root","","bebabeba2");

				// Check connection
				if ($conn->connect_error) {
			    	die("Connection failed: " . $conn->connect_error);
				}
				else
				{
					$sql = "SELECT driver_id, name, v_type, l_plate, phone_no, email, status FROM driver";

					$result = $conn->query($sql);



					/*if (mysql_num_rows($result)!=0false){*/
					if($result->num_rows > 0) {
						echo "<table width='700px' cellpadding='10px' cellspacing='10px'><tr><th>Name</th><th>Vehicle Type</th><th>License Plate</th><th>Phone Number</th><th>Email</th><th>Status</th></tr>";
						// output data of each row
						while($row = $result->fetch_assoc()) {
							$id = $row["driver_id"];
							echo "<tr><td>" . $row["name"]. "</td><td>" . $row["v_type"]. "</td><td>" . $row["l_plate"]. "</td><td>" . $row["phone_no"]. "</td><td>" . $row["email"]. "</td><td>".$row["status"]."</td><td><a href='approve.php?id=$id'><button>Approve</button><a/></td></tr>";
							}	
						echo "</table>";
					} 
					else{
						echo "0 results";
					}


				}
				$conn->close();
			?>


			<!--<div class="article">
				<h3>We Have Free Templates for Everyone</h3>
				<p>
					Our website templates are created with inspiration, checked for quality and originality and meticulously sliced and coded. What’s more, they’re absolutely free! You can do a lot with them. You can modify them. You can use them to design websites for clients, so long as you agree with the <a href="http://www.freewebsitetemplates.com/about/terms/">Terms of Use</a>. You can even remove all our links if you want to.
				</p>
				<h3>We Have More Templates for You</h3>
				<p>
					Looking for more templates? Just browse through all our <a href="http://www.freewebsitetemplates.com/">Free Website Templates</a> and find what you’re looking for. But if you don’t find any website template you can use, you can try our <a href="http://www.freewebsitetemplates.com/freewebdesign/">Free Web Design</a> service and tell us all about it. Maybe you’re looking for something different, something special. And we love the challenge of doing something different and something special.
				</p>
				<h3>Be Part of Our Community</h3>
				<p>
					If you’re experiencing issues and concerns about this website template, join the discussion on <a href="http://www.freewebsitetemplates.com/forums/">on our forum</a> and meet other people in the community who share the same interests with you.
				</p>
				<h3>Template details</h3>
				<p>
					Design version 2. Code version 1. Website Template details, discussion and updates for this <a href="http://www.freewebsitetemplates.com/discuss/mustacheenthusiast/">Mustache Enthusiast Website Template</a>. Website Template design by <a href="http://www.freewebsitetemplates.com/">Free Website Templates</a>. Please feel free to remove some or all the text and links of this page and replace it with your own About content.
				</p>
			</div>-->
		</div>
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
					<a href="">googleplus</a>
				</li>
				<li>
					<a href="" id="pinterest">pinterest</a>
				</li>
			</ul>
		</div>
	</div>
</body>
</html>