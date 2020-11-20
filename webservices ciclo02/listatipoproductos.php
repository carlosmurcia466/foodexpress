<?php

include ('cn.php'); 
if ($resultset = getSQLResultSet("select categoria_producto from categoria_producto;")) {
	$rawdata = array(); 
	$i=0;
    	while ($row = $resultset->fetch_array(MYSQLI_NUM)) {
			$rawdata[$i] = $row;
			$i++;
    	}
		echo json_encode($rawdata);
 }

?>