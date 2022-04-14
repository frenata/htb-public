<?php
include("db.php");
session_start();
if($_SESSION["loggedin"])
{
		$search=$_GET["search"];
                $id = "%{$_GET['search']}%";
		if($search)
		{
                $stmt=$conn->prepare("select id,title,description,status from tickets where id like ?");
                $stmt->bind_param("s",$id);
                $stmt->execute();
                $result = $stmt->get_result();
                $num_rows = $result->num_rows;
		if($num_rows > 0)
		{
			$row=$result->fetch_assoc();
			echo '<br />';
			echo '<br />';
			echo '<table border="2" width="100%"><tr><td style="font-size:180%;">ID</td><td style="font-size:180%;">Title</td><td style="font-size:180%;">Description</td><td style="font-size:180%;">Status</td></tr>';
			echo '<tr><td style="font-size:180%;">'.$row["id"].'</td>';
			echo '<td style="font-size:180%;">'.$row["title"].'</td>';
			echo '<td style="font-size:180%;">'.$row["description"].'</td>';
			echo '<td style="font-size:180%;">'.$row["status"].'</td></tr></table>';
		}
		else
		{
			echo '<br />';
			echo '<br />';
			echo '<p style="font-size:180%;">0 results</p>';
		}
		}
		else
		{
			echo '<br />';
			echo '<br />';
			echo '<p style="font-size:180%;">Please provide a search item</p>';
		}


}
?>

