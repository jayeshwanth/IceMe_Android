<?php
/*****************************************************************************
			Valid				InValid
input:			
1. Mobile
2. Device-id or email-id

output:
1. user-id.		Existed id			Null
2. status message.	some status message		Invalid status message.
*******************************************************************************/

//include database implementation class
include_once("UConnectDB.php");
if($_SERVER['REQUEST_METHOD'] == "GET")
{
//Get Data
	$mobile = isset($_GET['mobile'])?mysql_real_escape_string($_GET['mobile']):"";
	$device_id = isset($_GET['unique_id'])?mysql_real_escape_string($_GET['unique_id']):"";
	$json = array();
	$ucdb = new UConnectDB("root","***********","uConnect_db_schema","localhost");
	if($ucdb->connect())
	{
		if($ucdb->selectDatabase())
		{
			$sql = "select t_user_id from t_user where t_user_mobile_no='$mobile' and t_user_unique_id='$device_id'";
			$data = $ucdb->select($sql);
			$size = count($data);
			if($size <= 1)
			{
				$json = array("user_id" => 0, "message" => "Success.");
			}
			else
			{
			foreach($data as $user)
			{
				$json = array("user_id" => $user, "message" => "Data retrieved successfully.");
			}
			}
		}
		else
		{
			$json = array("user_id" => 0, "message" => "Not able to select database.");
		}
	}
	else
	{
		$json = array("user_id" => 0, "message" => "DB Connection failed.");
	}
}
else
{
	$json = array("user_id" => 0, "message" => "Request method not accepted."); 
}
header('Content-type: application/json');
echo json_encode($json);
?>
