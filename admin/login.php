<!DOCTYPE html>
<html >
  <head>
    <meta charset="UTF-8">
    <title>Log-in</title>
        <link rel="stylesheet" href="css/loginstyle.css">
  </head>

  <body>
	<div class="welcome-card">
    <p class="welcomeNote">	Welcome to Bebabeba Adminstrator Website<br>Enter your credentials below<br></p>
    </div>
    <div class="login-card">
    <h1>Admin Log-in</h1><br>
  <form method="post" action="login.php">
    <input type="text" name="username" placeholder="Username">
    <input type="password" name="password" placeholder="Password">
    <div id="wrong-password">
  		<?php
			echo "Username or password is incorrect!";
		?>
  	</div>
    <input type="submit" name="login" class="login login-submit" value="login">
  </form> 
  <div class="login-help">
    <a href="#">Forgot Password</a>
  </div>

</div>   
  </body>
</html>
<?php

	if(isset($_POST['login']))
	{
		$user=$_POST['username'];
		$pass=$_POST['password'];
		
		$conn = mysqli_connect("localhost","root","");
		
		if(!$conn)
		{
			die('Could not connect: '.mysql_error());
		}	
		mysqli_select_db($conn,"bebabeba2");
		
		$result = mysqli_query($conn,"SELECT * FROM administrator WHERE username='$user' AND password='$pass'");
		
		if (mysqli_num_rows($result))
		{
			//correct info
			while($row = mysqli_fetch_array($result))
			{
				$expire = time()+60*60*24;//one day
				setcookie("uid",$row['uid'],$expire);
				echo "Logged in as ".$row['username'];
				header("location:homepage.html");
			}
		}
		else
		{
			//wrong info
			?>
			<style type="text/css">#wrong-password{
				display:block;
			}</style>
			<?php
		}
		mysqli_close($conn);
		//$userid = $row['uid'];
	}
	else
	{
		if(isset ($_COOKIE['uid']))
		{
			$userid = $_COOKIE['uid'];
		}
	}
?>