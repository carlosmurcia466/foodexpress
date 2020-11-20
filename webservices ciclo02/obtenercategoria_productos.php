<?php 

define('DB_HOST','mysql.webcindario.com');
define('DB_USER', 'foodexpress');
define('DB_PASS','elsalvador466');
define('DB_NAME', 'foodexpress');

$conn =new mysqli(DB_HOST,DB_USER,DB_PASS,DB_NAME);

if(mysqli_connect_errno()){
  echo "fallo la conexion: ".mysqli_connect_errno();
  die();
}

$stmt=$conn->prepare("SELECT id_tipo_producto,categoria_producto,imagen FROM categoria_producto;");

$stmt->execute();
$stmt->bind_result($id_tipo_producto,$categoria_producto,$imagen);
$categoria =array();

while ($stmt->fetch()) {
	$temp=array();
	$temp['id_tipo_producto']=$id_tipo_producto;
	$temp['categoria_producto']=$categoria_producto;
	$temp['imagen']=$imagen;

	array_push($categoria, $temp);
}
echo json_encode($categoria);





 ?>