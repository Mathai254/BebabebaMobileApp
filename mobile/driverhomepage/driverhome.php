<!DOCTYPE html>
<html >
<head>
  <meta charset="UTF-8">
  <title>Bebabeba - Home</title>
  
  
  
      <link rel="stylesheet" href="css/style.css">
      <link rel="stylesheet" href="../responsive.css">

  
</head>

<body>
  <?php 
    session_start(); 
  ?>
  <nav class="menu" tabindex="0">
	<div class="smartphone-menu-trigger"></div>
  <header class="avatar">
		<img src= ".<?php $_SESSION['userphoto']?>." />
    <?php 
      echo $_SESSION['name'] 
    ?>
    <h2></h2>
  </header>
	<ul>
    <a href=""><li tabindex="0" class="icon-dashboard"><span>Home</span></li></a>
    <a href=""><li tabindex="0" class="icon-dashboard"><span>Payments</span></li></a>
    <a href=""><li tabindex="0" class="icon-users"><span>Posts</span></li></a>
    <a href=""><li tabindex="0" class="icon-settings"><span>Bookings</span></li></a>
    <a href="feedback.php"><li tabindex="0" class="icon-settings"><span>Feedback</span></li></a>
  </ul>
</nav>

<main>
  <div class="helper">
    RESIZE THE WINDOW
		<span>Breakpoints on 900px and 400px</span>
    

  </div>
</main>
  
      <script src="js/index.js"></script>

</body>
</html>
