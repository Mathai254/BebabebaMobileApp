<!DOCTYPE html>
<html >
<head>
  <meta charset="UTF-8">
  <title>Bebabeba - Feedback</title>
  
  
  
      <link rel="stylesheet" href="css/style.css">
      <link rel="stylesheet" href="../responsive.css">

  
</head>

<body>
  <nav class="menu" tabindex="0">
	<div class="smartphone-menu-trigger"></div>
  <header class="avatar">
		<img src="https://s3.amazonaws.com/uifaces/faces/twitter/kolage/128.jpg" />
    <?php session_start(); echo $_SESSION['name'] ?>
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
    <form action="" method="post">
      <p>Please send us your feedback</p>
      <textarea name="comments" cols="20" rows="4">Enter
      your comments...</textarea>
      <br>
      <input type="submit" name="send" value="Send">
    </form>

  </div>
</main>
  
      <script src="js/index.js"></script>

</body>
</html>
