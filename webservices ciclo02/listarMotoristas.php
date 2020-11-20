<?php 
include("conexion.php");
$query="Select nombre_completo,telefono,correo from usuarios where id_tipo_usuario=3;";
$consulta=$conexion->query($query);


while ($row=$consulta->fetch_array()) {
       $array_datos[]=array_map('utf8_encode',$row);

}

echo json_encode($array_datos);
$consulta->close();


 ?>