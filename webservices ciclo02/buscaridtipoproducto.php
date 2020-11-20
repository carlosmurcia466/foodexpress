<?php
include 'conexion.php';
$categoria_producto=$_GET['categoria_producto'];

$consulta="SELECT id_tipo_producto from categoria_producto where categoria_producto= '$categoria_producto' ";
$resultado=$conexion -> query($consulta);

while ($fila=$resultado -> fetch_array()) {
	$producto[]=array_map('utf8_encode', $fila);
}

echo json_encode($producto);
$resultado -> close();




  ?>