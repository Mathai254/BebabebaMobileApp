<!DOCTYPE html>
<html >
  <head>
    <meta charset="UTF-8">
    <title>Bebabeba Mobile</title>
    
    <link rel="stylesheet" href="css/normalize.css">
    <link rel="stylesheet" href="css/style.css">
    
  </head>

  <body>

    <div class="form">
       <div id="login">   
          <h1>DRIVER LOG IN</h1>
          
          <form action="driverloginpage.php" method="post">
          
            <div class="field-wrap">
            <label>
              Email Address
            </label>
            <input type="email"required autocomplete="off" name="email"/>
          	</div>
          
          	<div class="field-wrap">
            <label>
              Password
            </label>
            <input type="password"required autocomplete="off" name="password"/>
          	</div>
            
            <div id="wrong-password">
              <?php
                echo "Username or password is incorrect!";
              ?>
            </div>

          	<!--<p class="forgot"><a href="#">Forgot Password?</a></p>-->
            <p class="message">Not registered? <a href="driverregister.html">Create an account</a></p>
          
          	<!--<button class="button button-block"/>Log In</button>-->
            <input type="submit" name="login" class="button button-block" value="Log In">
          
          </form>
        </div><!--login--> 
	</div> <!-- /form -->
    <!--<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

        <script src="js/index.js"></script>-->

    
    
  </body>
</html>
<?php

  if(isset($_POST['login']))
  {
    $email=$_POST['email'];
    $pass=$_POST['password'];
    
    $conn = mysqli_connect("localhost","root","");
    
    if(!$conn)
    {
      die('Could not connect: '.mysql_error());
    } 
    mysqli_select_db($conn,"bebabeba");
    
    $result = mysqli_query($conn,"SELECT * FROM driver WHERE email='$email' AND Password='$pass'");
    
    if (mysqli_num_rows($result))
    {
      //correct info
      while($row = mysqli_fetch_array($result))
      {
        $expire = time()+60*60*24;//one day
        setcookie("uid",$row['uid'],$expire);
        echo "Logged in as ".$row['email'];
        header("location:driverhomepage.php");
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