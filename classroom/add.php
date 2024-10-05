<?php

/* Connecting to the database. */
$host = "localhost";
$db_name = "classroom_bd"; // Cambiado a "classrom_bd"
$username = "root";
$pass = "";
$conn = mysqli_connect($host, $username, $pass) 
or trigger_error(mysqli_error($conn), E_USER_ERROR);
mysqli_select_db($conn, $db_name);

/* Getting the values from the form. */
$cedula = $_POST['cedula'];
$username = $_POST['username'];
$password = $_POST['password'];
$email = $_POST['email'];
$role = $_POST['role'];

/* Inserting the values from the form into the database. */
$query = "INSERT INTO usuarios (cedula, username, password, email, role) 
VALUES ('$cedula', '$username', '$password', '$email', '$role')";

/* Executing the query. */
$query_execute = mysqli_query($conn, $query) or die (mysqli_error($conn));

/* Closing the connection. */
mysqli_close($conn);

?>
