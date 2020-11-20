<?php


 if($_SERVER['REQUEST_METHOD']=='POST'){
 
 $nombre_producto= $_POST['nombre_producto'];
 $id_tipo_producto = $_POST['id_tipo_producto'];
 $precio= $_POST['precio'];
 $detalle_producto = $_POST['detalle_producto'];
 $imagen= $_POST['imagen'];

 
 require_once('conexion.php');
 
 $sql ="SELECT id_producto FROM producto ORDER BY id_producto ASC";
 
 $res = mysqli_query($conexion,$sql);

 
 $id = 0;
 
 while($row = mysqli_fetch_array($res)){
 $id = $row['id_producto'];
 }
 
 $path = "uploadsproductos/$id.png";
 
 $actualpath = "https://foodexpress.webcindario.com/$path";
 
 $sql = "INSERT INTO producto (nombre_producto,id_tipo_producto,precio,detalle_producto,imagen) VALUES ('$nombre_producto','$id_tipo_producto','$precio','$detalle_producto','$actualpath')";
 
 if(mysqli_query($conexion,$sql)){
 file_put_contents($path,base64_decode($imagen));
 echo "Subio imagen Correctamente";
 }
 
 mysqli_close($conexion);
 }else{
 echo "Error";
 }




  ?>