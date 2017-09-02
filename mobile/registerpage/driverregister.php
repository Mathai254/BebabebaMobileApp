<?php
	$f_name = $_POST['FirstName'];
	$l_name = $_POST['LastName'];
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
	
	
	$sql = "INSERT INTO driver (FirstName, LastName, email, Password)
	VALUES ($f_name', '$l_name', '$email', '$password')";
	
	if ($conn->query($sql) === TRUE) 
	{
		echo "New record created successfully.";
		//header ("location: homepage.php");
	} 
	else 
	{
		echo "Error: " . $sql . "<br>" . $conn->error;
	}
	
	//session_start();
	//$_SESSION['cus_name'] = $c_name;
	
	mysqli_close($conn);
?>