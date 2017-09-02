<?php
    //$con = mysqli_connect("localhost", "id571444_mathai254", "123wambugu*", "id571444_loginregister");
    $conn = mysqli_connect("localhost","root","");
        
    if(!$conn)
    {
        die('Could not connect: '.mysql_error());
    }   
    mysqli_select_db($conn,"bebabeba2");

    $email = $_POST["email"];
    $password = $_POST["password"];
    
    $statement = mysqli_prepare($conn, "SELECT * FROM passenger WHERE email = ? AND password = ?");
    mysqli_stmt_bind_param($statement, "ss", $email, $password);
    mysqli_stmt_execute($statement);
    
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $user_id, $name, $phone_no, $email, $password);
    
    $response = array();
    $response["success"] = false;  
    
    while(mysqli_stmt_fetch($statement)){
        $response["success"] = true;
        $response["name"] = $name;
        $response["phone_no"] = $phone_no;  
        $response["email"] = $email;
        $response["password"] = $password;
    }
    
    echo json_encode($response);
    //print(json_encode($response));
?>