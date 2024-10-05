<?php

/* Conexión a la base de datos. */
$host = "localhost";
$db_name = "classroom_bd";
$username = "root";
$pass = "";
$conn = mysqli_connect($host, $username, $pass)
or trigger_error(mysqli_error($conn), E_USER_ERROR);
mysqli_select_db($conn, $db_name);

/* Obtener los datos del formulario. */
$cedula = $_POST['cedula'];
$username = $_POST['username'];
$password = $_POST['password'];
$email = $_POST['email'];

/* Actualizar los datos en la tabla 'usuarios' basado en la cédula. */
$query = "UPDATE usuarios SET 
    username = '".$username."',
    password = '".$password."',
    email = '".$email."'
WHERE cedula = '".$cedula."'";

$query_execute = mysqli_query($conn, $query) or die (mysqli_error($conn));

/* Verifica si la actualización fue exitosa. */
if (mysqli_affected_rows($conn) > 0) {
    echo json_encode(array('message' => 'Usuario actualizado con éxito.'));
} else {
    echo json_encode(array('message' => 'No se encontró el usuario o no se realizaron cambios.'));
}

/* Cierra la conexión a la base de datos. */
mysqli_close($conn);

?>
