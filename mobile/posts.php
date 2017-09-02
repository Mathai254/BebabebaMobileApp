<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Bebabeba | Solution for your transport services</title>
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="content-language" content="" />
    <link href="style.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

    <div id="header">
            <h1><a href="#">Bebabeba</a></h1>
            <p id="subtitle">Solution for your transport needs</p>
    </div>
    
    <div id="menu"><div id="menu2">
            <ul>
                <li class="active"><a href="#"><span>Home</span></a></li>
                <li><a href="posts.php"><span>Posts</span></a></li>
                <li><a href="driverbookings.php"><span>Bookings</span></a></li>
                <li><a href="#"><span>Help</span></a></li>
                <li><a href="#"><span>Contact</span></a></li>
            </ul>
    </div></div>
    <div id="main">
        <div id="content">
            <div class="post">
                <h2><a href="#">Add new posts</a></h2>
                
                <div class="form">
                    <div id="signup">   
             
                      <form action="addpost.php" method="post" onsubmit="return checkMe()" name="myForm">
                      
                          <div><h3>Route details</h3></div>
                          
                          <div class="top-row">
                          <br>
                            <div class="field-wrap">
                              <label>
                                From
                              </label>
                              <input type="text" required autocomplete="off" name="LocationFrom"/>
                            </div>
                        
                            <div class="field-wrap">
                              <label>
                                To
                              </label>
                              <input type="text"required autocomplete="off" name="LocationTo"/>
                            </div>
                          </div>
                
                          <div class="field-wrap">
                            <label>
                              Price
                            </label>
                            <input type="text"required autocomplete="off" name="Price"/>
                          </div>
                          
                          <button type="submit" class="button button-block"/>Add post</button>
                          
                      </form>

                    </div><!--sigup-->
             
                </div> <!-- /form -->

                <?php
    
                    // Create connection
                    $conn = new mysqli("localhost","root","","bebabeba");

                    // Check connection
                    if ($conn->connect_error) {
                        die("Connection failed: " . $conn->connect_error);
                    }
                    else
                    {
                        $sql = "SELECT LocationFrom, LocationTo, Price FROM route";

                        $result = $conn->query($sql);



                        /*if (mysql_num_rows($result)!=0false){*/
                        if($result->num_rows > 0) {
                            echo "<table width='300px' cellpadding='20px' cellspacing='10px'><tr><th float='right'>From</th><th>To</th><th>Price</th></tr>";
                            // output data of each row
                            while($row = $result->fetch_assoc()) {
                                echo "<tr><td>" . $row["LocationFrom"]. "</td><td>" . $row["LocationTo"]. "</td><td>" . $row["Price"]. "</td></tr>";
                                }   
                            echo "</table>";
                        } 
                        else{
                            echo "0 results";
                        }
                    }
                    $conn->close();
                ?>

           
            </div><!-- post -->
            <!--<div class="post">
                <h2><a href="#">Lorem ipsum dolor sit amet</a></h2>
                <p class="postmeta">Posted in <a href="#">Class apent</a> | Sep 19, 2019</p>
                <div class="entry">
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean nec dui quis urna sollicitudin sodales. Fusce laoreet, ligula et rhoncus volutpat, felis magna varius tortor, ac molestie diam lorem in lectus. Aliquam venenatis mollis est, a porttitor ipsum interdum nec. Vestibulum sed risus ac nulla viverra pharetra.</p>
                    <p>Quisque congue lacus sed odio fermentum tincidunt. Proin vitae nulla velit. Cras consectetur commodo scelerisque. Curabitur leo nisl, blandit at tempus et, interdum at risus. Sed dui augue, pellentesque ac pulvinar id, malesuada eget diam. Integer elementum sem eget tortor faucibus id pellentesque lorem dignissim.</p>
                    <p class="postmeta2"><span class="readmore"><a href="#">read more</a></span><span class="comments"><a href="#">comments: 3</a></span></p>
                </div>
            </div><!-- post -->
        </div><!-- content -->		
        <!--<div id="sidebar">
            <div class="box">
                <h2>Quisque luctus</h2>
                <ul>
                    <li><a href="#">Ut pharetra neque eget</a></li>
                    <li><a href="#">Nisi tristique et feugiat</a></li>
                    <li><a href="#">Vulputate ac hendrerit ut</a></li>
                    <li><a href="#">Aenean sed lectus massa</a></li>
                    <li><a href="#">Quisque luctus sem suscipit</a></li>
                </ul>
            </div>
            <div class="box2">
                <h2>Integer rhoncus</h2>
                <div class="box3">
                    <p>Mauris sollicitudin tincidunt magna vitae semper. Curabitur ut pharetra quam. Integer rhoncus convallis urna vitae mattis. Sed pharetra massa ac metus fermentum et iaculis enim accumsan.</p>
                </div>
            </div>
            <div class="box">
                <h2>Mauris sagittis</h2>
                <ul>
                    <li><a href="#">Mauris sagittis tellus</a></li>
                    <li><a href="#">Lacus tincidunt adipiscing</a></li>
                    <li><a href="#">Purus sagittis feugiat</a></li>
                    <li><a href="#">Suspendisse faucibus purus</a></li>
                    <li><a href="#">Nulla erat rhoncus felis</a></li>
                </ul>
            </div>
        </div><!-- sidebar -->-->
        <div class="clearing">&nbsp;</div>
    </div><!-- main -->
    <div id="footer"><div id="footer2">
            <p>Copyright &copy; 2012, designed by <a href="http://www.webtemplateocean.com/">WebTemplateOcean.com</a> | <a href="#">Privacy Policy</a>
</p>
    </div></div>
</body>
</html>