<?php
    //$con = mysqli_connect("localhost", "id571444_mathai254", "123wambugu*", "id571444_loginregister");

    $conn = mysqli_connect("localhost","root","");
        
    if(!$conn)
    {
        die('Could not connect: '.mysql_error());
    }   
    mysqli_select_db($conn,"bebabeba2");
    
    $name = $_POST["name"];
    $v_type = $_POST["v_type"];
    $l_plate = $_POST["l_plate"];
    $phone_no = $_POST["phone_no"];
    $email = $_POST["email"];
    $password = $_POST["password"];
    $statement = mysqli_prepare($conn, "INSERT INTO driver (name, v_type, l_plate, phone_no, email, password) VALUES (?, ?, ?, ?, ?, ?)");
    mysqli_stmt_bind_param($statement, "ssssss", $name, $v_type, $l_plate, $phone_no, $email, $password); 
    //remember to return s and v_type on db
    mysqli_stmt_execute($statement);
    
    $response = array();
    $response["success"] = true;  
    
    echo json_encode($response);
    //print(json_encode($response));
?>