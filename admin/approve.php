<!DOCTYPE html>
<html>
<head>
	<title></title>
</head>
<body>
<p>Success</p>
<?php
	// Create connection
	$conn = new mysqli("localhost","root","","bebabeba2");

	// Check connection
	if ($conn->connect_error) {
		die("Connection failed: " . $conn->connect_error);
	}
	else
	{
		echo "$_GET[id]";
		$sql = "UPDATE driver SET status = 'approved' WHERE driver_id = $_GET[id]";

		if ($conn->query($sql) === TRUE) 
		{
			echo "New record created successfully.";
			header ("location: drivers.php");
		} 
		else 
		{
			echo "Error: " . $sql . "<br>" . $conn->error;
		}
	}
	$conn->close();
?>

</body>
</html>
