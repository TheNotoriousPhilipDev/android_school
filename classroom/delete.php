<?php

/* Conexión a la base de datos. */
$host = "localhost";
$db_name = "classroom_bd";
$username = "root";
$pass = "";
$conn = mysqli_connect($host, $username, $pass) 
or die("Error al conectar: " . mysqli_connect_error());

mysqli_select_db($conn, $db_name);

/* Obtener la cédula desde la solicitud POST. */
$cedula = $_POST['cedula'];
$querry = "DELETE FROM usuarios WHERE cedula = '".$cedula."'";

/* Ejecuta la consulta. */
$querry_execute = mysqli_query($conn, $querry) or die (mysqli_error($conn));

if (mysqli_affected_rows($conn) > 0) {
    echo json_encode(array('status' => 'success', 'message' => 'Usuario eliminado con éxito.'));
} else {
    echo json_encode(array('status' => 'error', 'message' => 'No se encontró ningún usuario con esa cédula.'));
}

/* Cierra la conexión a la base de datos. */
mysqli_close($conn);

?>
