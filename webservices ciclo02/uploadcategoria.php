<?php


 if($_SERVER['REQUEST_METHOD']=='POST'){
 
 $imagen= $_POST['imagen'];
 $categoria_producto = $_POST['categoria_producto'];
 
 require_once('conexion.php');
 
 $sql ="SELECT id_tipo_producto FROM categoria_producto ORDER BY id_tipo_producto ASC";
 
 $res = mysqli_query($conexion,$sql);

 
 $id = 0;
 
 while($row = mysqli_fetch_array($res)){
 $id = $row['id_tipo_producto'];
 }
 
 $path = "uploads/$id.png";
 
 $actualpath = "https://foodexpress.webcindario.com/$path";
 
 $sql = "INSERT INTO categoria_producto (categoria_producto,imagen) VALUES ('$categoria_producto','$actualpath')";
 
 if(mysqli_query($conexion,$sql)){
 file_put_contents($path,base64_decode($imagen));
 echo "Subio imagen Correctamente";
 }
 
 mysqli_close($conexion);
 }else{
 echo "Error";
 }




  ?>