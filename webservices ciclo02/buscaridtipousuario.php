<?php
include 'conexion.php';
$tipo_usuario=$_GET['tipo_usuario'];

$consulta="select id_tipo_usuario from tipo_usuarios where tipo_usuario= '$tipo_usuario' ";
$resultado=$conexion -> query($consulta);

while ($fila=$resultado -> fetch_array()) {
	$producto[]=array_map('utf8_encode', $fila);
}

echo json_encode($producto);
$resultado -> close();




  ?>