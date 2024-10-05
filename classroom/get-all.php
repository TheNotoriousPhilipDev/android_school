<?php

/* Conexión a la base de datos. */
$host = "localhost";
$db_name = "classroom_bd";
$username = "root";
$pass = "";
$conn = mysqli_connect($host, $username, $pass)
or trigger_error(mysqli_error($conn), E_USER_ERROR);
mysqli_select_db($conn, $db_name);

/* Consulta para obtener todos los usuarios de la tabla 'usuarios'. */
$querry = "SELECT id, cedula, username, email, role FROM usuarios";

/* Ejecuta la consulta. */
$querry_execute = mysqli_query($conn, $querry) or die (mysqli_error($conn));

/* Crea un array y agrega los datos obtenidos de la base de datos al array. */
$usuarios = array();
while ($row = mysqli_fetch_array($querry_execute)) {
    $usuario = array(
        'id' => $row['id'],
        'cedula' => $row['cedula'],
        'username' => $row['username'],
        'email' => $row['email'],
        'role' => $row['role']
    );
    array_push($usuarios, $usuario);
}

/* Devuelve los resultados en formato JSON. */
echo json_encode(array('usuarios' => $usuarios));

/* Cierra la conexión a la base de datos. */
mysqli_close($conn);

?>
