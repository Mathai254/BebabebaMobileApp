<?php
    $con = mysqli_connect("localhost", "id571444_mathai254", "123wambugu*", "id571444_loginregister");
    
    $name = $_POST["name"];
    $username = $_POST["username"];
    $age = $_POST["age"];
    $password = $_POST["password"];
    $statement = mysqli_prepare($con, "INSERT INTO user (name, username, age, password) VALUES (?, ?, ?, ?)");
    mysqli_stmt_bind_param($statement, "ssis", $name, $username, $age, $password);
    mysqli_stmt_execute($statement);
    
    $response = array();
    $response["success"] = true;  
    
    echo json_encode($response);
?>