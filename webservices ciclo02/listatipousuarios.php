<?php

include ('cn.php'); 
if ($resultset = getSQLResultSet("select tipo_usuario, id_tipo_usuario from tipo_usuarios;")) {
	$rawdata = array(); 
	$i=0;
    	while ($row = $resultset->fetch_array(MYSQLI_NUM)) {
			$rawdata[$i] = $row;
			$i++;
    	}
		echo json_encode($rawdata);
 }

?>