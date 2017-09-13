<?php

  $link = mysqli_connect("localhost", "ru2018", "SHEforlife11@", "Login_info");
  $email= mysqli_real_escape_string($link, $_POST["email"]);
  $username= mysqli_real_escape_string($link, $_POST["username"]);
  $password= mysqli_real_escape_string($link, $_POST["password"]);
  $sql = "INSERT INTO `User`(`Email`, `Username`, `Password`) VALUES ('$email', '$username', '$password')";
  if (mysqli_query($link, $sql) === TRUE) {
    echo "TRUE";
} else {
    echo "FALSE";
}
?>