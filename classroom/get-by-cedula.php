<?php

/* Connecting to the database. */
$host = "localhost";
$db_name = "classroom_bd";
$username = "root";
$pass = "";
$conn = mysqli_connect($host, $username, $pass);

if (!$conn) {
    error_log("Connection failed: " . mysqli_connect_error());
    echo "Connection failed: " . mysqli_connect_error();
    http_response_code(500);
    exit();
}

mysqli_select_db($conn, $db_name);

/* Getting the values from the form. */
$cedula = $_POST['cedula'];

/* Querying the database. */
$query = "SELECT * FROM usuarios WHERE cedula='$cedula'";
$result = mysqli_query($conn, $query);

if (!$result) {
    // Log the error
    error_log("MySQL Error: " . mysqli_error($conn));
    // Display the error for debugging
    echo "MySQL Error: " . mysqli_error($conn);
    http_response_code(500);
    exit();
}

$usuarios = array();
while ($row = mysqli_fetch_assoc($result)) {
    $usuarios[] = $row;
}

$response = array("usuarios" => $usuarios);
echo json_encode($response);

/* Closing the connection. */
mysqli_close($conn);

?>
