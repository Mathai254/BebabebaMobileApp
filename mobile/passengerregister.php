<!DOCTYPE html>
<html >
  <head>
    <meta charset="UTF-8">
    <title>Bebabeba Mobile</title>
    <link href='http://fonts.googleapis.com/css?family=Titillium+Web:400,300,600' rel='stylesheet' type='text/css'>
    
  </head>

  <body>

    <?php

	
		$u_id = $_POST['IdNumber'];
		$f_name = $_POST['FirstName'];
		$l_name = $_POST['LastName'];
		$photo = $_POST['Photo'];
		$phone_N = $_POST['PhoneNumber'];
		$email = $_POST['Email'];
		$password = $_POST['password1'];
	
	
		// Create connection
		$conn = new mysqli("localhost","root","","bebabeba");
	
		// Check connection
		if ($conn->connect_error) 
		{
			die("Connection failed: " . $conn->connect_error);
		}
		else
		{
			echo "Connected successfully. ";
		}
	
	
		$sql = "INSERT INTO passenger (PassengerIDNO, FirstName, LastName, PhoneNumber, Photo, email, Password)
		VALUES ('$u_id', '$f_name', '$l_name', '$phone_N', '$photo', '$email', '$password')";
	
		if ($conn->query($sql) === TRUE) 
		{
			echo "New record created successfully.";
			header ("location: homepage.php");
		} 
		else 
		{
			echo "Error: " . $sql . "<br>" . $conn->error;
		}
	
		//session_start();
		//$_SESSION['cus_name'] = $c_name;
	
		mysqli_close($conn);

?>
    
  </body>
</html>

