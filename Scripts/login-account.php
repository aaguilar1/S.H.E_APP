<?php

  $link = mysqli_connect("localhost", "ru2018", "SHEforlife11@", "Login_info");
  $email= mysqli_real_escape_string($link, $_POST["email"]);
  $password= mysqli_real_escape_string($link, $_POST["password"]);
  $sql = "SELECT * FROM User WHERE `Email`='$email' AND `Password` = '$password'";
  $rs = mysqli_query($link, $sql);
  if (mysqli_num_rows($rs) == 0) {
    echo "FALSE";
 } else {
    $row = mysqli_fetch_array($rs);
    echo $row['username'] . "/" . $row['name'] . "/" . $row['password'] . "/" . $row['email'];
}
?>