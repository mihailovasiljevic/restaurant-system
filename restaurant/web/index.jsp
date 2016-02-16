<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Sistem za rezervaciju restorana</title>

    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="css/stylish-portfolio.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,700,300italic,400italic,700italic" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <!-- Google maps code -->
    <script type="text/javascript"
    src="http://maps.google.com/maps/api/js?sensor=false">
    </script>
    <script>

        $(document).ready(function(){
	

            
                $("#btn-login").click(
                    function(){
                        var userEmail = $("#userEmail").val();
                        var userPassword = $("#userPassword").val();
                        var allGood = false;
                        var rememberMe = $("#login-remember").is(':checked') ? '1' : '0';
                            if( userEmail == "" || userEmail == undefined || userEmail == null ) {
                                $("#email-error").text("Polje za email adresu ne sme biti prazno!");

                                allGood = false;
                            } else {
                                $("#email-error").text("");

                                allGood = true;
                            }  

                            if( userPassword == "" || userPassword == undefined || userPassword == null ) {
                                $("#password-error").text("Polje za lozinku ne sme biti prazno!");

                                allGood = false;
                            } else {
                               $("#password-error").text("");
                                
                                if(allGood != false)
                                    allGood = true;
                            }  
                            var answer="";
                            if(allGood == true){
                                $.ajaxSetup({async:false});
                                $.ajax({
                                      url: "./login",
                                      type: 'post',
                                      contentType: "application/x-www-form-urlencoded",
                                      data: {
                                       loginData:JSON.stringify({
                                           userEmail:userEmail,
                                           userPassword:userPassword,
                                           rememberMe: rememberMe
                                       }),    
                                       cache: false,
                                       dataType:'json'
                                    },
                                      success: function (data, status) {
                                        if(data == "USPEH_GOST"){
                                             window.location.href = "/restaurant/guest/guest.jsp";
                                            return;
                                        }else if(data == "USPEH__SISTEM_MENADZER"){
                                             window.location.href = "/restaurant/system-menager/system-menager.jsp";
                                            return;
                                        }else if(data == "USPEH__RESTORAN_MENADZER"){
                                             window.location.href = "/restaurant/restaurant-menager/restaurant-menager.jsp";
                                            return;
                                        }
                                        $( "#email-error" ).text(data);
                                        //alert("Data: "+ data);
                                        console.log(data);
                                        console.log(status);
                                      },
                                      error: function (xhr, desc, err) {
                                        console.log(xhr);
                                      },
                                    });
                                $.ajaxSetup({async:true});
                            }
                    });
                
            
            function validateEmail(email) {
                var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
                return re.test(email);
            }
                    $("#btn-signup").click(
                    function(){
                        var userEmailsu = $("#userEmailsu").val();
                        var userName = $("#userName").val();
                        var userSurname = $("#userSurname").val();
                        var userPasswordsu = $("#userPasswordsu").val();
                        var userPasswordsuRepeat = $("#userPasswordsuRepeat").val();
                        var uploadFile = $("#uploadFile");
                        var allGood = false;
                            if( userEmailsu == "" || userEmailsu == undefined || userEmailsu == null ) {
                                $("#emailsu-error").text("Polje za email adresu ne sme biti prazno!");

                                allGood = false;
                            } else {
                                if(!validateEmail(userEmailsu)){
                                    $("#emailsu-error").text("Neispravna email adresa!");
                                    allGood = false 
                                }
                                else{
                                    $("#emailsu-error").text("");

                                    allGood = true;
                                }
                            }  
                        
                            if( userName == "" || userName == undefined || userName == null ) {
                                $("#name-error").text("Polje za ime ne sme biti prazno!");

                                allGood = false;
                            } else {
                               $("#name-error").text("");
                                
                                if(allGood != false)
                                    allGood = true;
                            } 
                        
                            if( userSurname == "" || userSurname == undefined || userSurname == null ) {
                                $("#surname-error").text("Polje za prezime ne sme biti prazno!");

                                allGood = false;
                            } else {
                               $("#surname-error").text("");
                                
                                if(allGood != false)
                                    allGood = true;
                            } 
                        
                                                
                            if( userPasswordsu == "" || userPasswordsu == undefined || userPasswordsu == null ) {
                                $("#passwordsu-error").text("Polje za lozinku ne sme biti prazno!");

                                allGood = false;
                            } else {
                               $("#passwordsu-error").text("");
                                
                                if(allGood != false)
                                    allGood = true;
                            } 

                            if( userPasswordsuRepeat == "" || userPasswordsuRepeat == undefined || userPasswordsuRepeat == null ) {
                                $("#repeatPassword-error").text("Polje za ponovljenu lozinku ne sme biti prazno!");

                                allGood = false;
                            } else {
                               $("#repeatPassword-error").text("");
                                
                                if(allGood != false)
                                    allGood = true;
                            }  
                            if(userPasswordsu != userPasswordsuRepeat || userPasswordsu == "" || userPasswordsu == undefined || userPasswordsu == null || userPasswordsuRepeat == "" || userPasswordsuRepeat == undefined || userPasswordsuRepeat == null ){
                                 $("#repeatPassword-error").text("Lozinke se ne poklapaju!!");

                                allGood = false;
                            }else{
                               $("#repeatPassword-error").text("");
                                
                                if(allGood != false)
                                    allGood = true;
                            }
                            var answer="";
                            if(allGood == true){
                                if(document.getElementById("uploadFile").files[0] != null){
                                    performAjaxSubmitSlika(userEmailsu, userName, userSurname, userPasswordsu);
                                }

                            }
                    });
            
            
            
            
            });
        
        function performAjaxSubmitSlika(userEmailsu, userName, userSurname, userPasswordsu) {
            var uploadItem = document.getElementById("uploadFile").files[0];
            var formdata = new FormData();
            formdata.append("image-upload", uploadItem);	  

            var xhr = new XMLHttpRequest();

            xhr.open("POST","/restaurant/fileUpload", true);
            xhr.send(formdata);

            xhr.onload = function(e) {
                    if (this.status == 200) {
                       
                              $.ajaxSetup({async:false});
                                $.ajax({
                                      url: "./login",
                                      type: 'post',
                                      contentType: "application/x-www-form-urlencoded",
                                      data: {
                                       registrationData:JSON.stringify({
                                           userEmail:userEmailsu,
                                           userPassword:userPasswordsu,
                                           userName: userName,
                                           userSurname: userSurname
                                       }),    
                                       cache: false,
                                       dataType:'json'
                                    },
                                      success: function (data, status) {
                                        if(data != "GRESKA"){
                                             $("#myModal").modal('hide');
                                             $("#registrationModal #message").text("Dogodila se greska i nismo uspeli da vas registrujemo. Molimo pokusajte ponovo.");
                                             $("#registrationModal").modal('show');
                                            return;
                                        }else 
                                             $("#registrationModal #message").text("Da biste koristili nalog morate ga aktivirati. Aktivacioni mejl je poslat na adresu: " + data);
                                             $("#registrationModal").modal('show');
                                        }
                                        $( "#email-error" ).text(data);
                                        //alert("Data: "+ data);
                                        console.log(data);
                                        console.log(status);
                                      },
                                      error: function (xhr, desc, err) {
                                        console.log(xhr);
                                      },
                                    });
                                $.ajaxSetup({async:true});
                        
                        
                        
                    }else if(this.status == 500){
                        alert("Dogodila se greska prilikom postavljanja slike. Molimo ponovite proces registracije.");
                        window.location.href="/restaurant/index.jsp"
                    }
            };	        		
        }
        
        $(document).on('click', '#close-preview', function(){ 
            $('.image-preview').popover('hide');
            // Hover befor close the preview
            $('.image-preview').hover(
                function () {
                   $('.image-preview').popover('show');
                }, 
                 function () {
                   $('.image-preview').popover('hide');
                }
            );    
        });

        $(function() {
            // Create the close button
            var closebtn = $('<button/>', {
                type:"button",
                text: 'x',
                id: 'close-preview',
                style: 'font-size: initial;',
            });
            closebtn.attr("class","close pull-right");
            // Set the popover default content
            $('.image-preview').popover({
                trigger:'manual',
                html:true,
                title: "<strong>Preview</strong>"+$(closebtn)[0].outerHTML,
                content: "There's no image",
                placement:'bottom'
            });
            // Clear event
            $('.image-preview-clear').click(function(){
                $('.image-preview').attr("data-content","").popover('hide');
                $('.image-preview-filename').val("");
                $('.image-preview-clear').hide();
                $('.image-preview-input input:file').val("");
                $(".image-preview-input-title").text("Browse"); 
            }); 
            // Create the preview image
            $(".image-preview-input input:file").change(function (){     
                var img = $('<img/>', {
                    id: 'dynamic',
                    width:250,
                    height:200
                });      
                var file = this.files[0];
                var reader = new FileReader();
                // Set preview image into the popover data-content
                reader.onload = function (e) {
                    $(".image-preview-input-title").text("Change");
                    $(".image-preview-clear").show();
                    $(".image-preview-filename").val(file.name);            
                    img.attr('src', e.target.result);
                    $(".image-preview").attr("data-content",$(img)[0].outerHTML).popover("show");
                }        
                reader.readAsDataURL(file);
            });  
        });

    </script>
    
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
  <script src="scripts/login.js"></script>
</head>

<body>
          <c:if test="${sessionScope.user != null}" >
			<c:choose>
			    <c:when test="${sessionScope.user.userType.name eq 'GUEST'}">
			       <c:redirect url="./guest/guest.jsp" />
			    </c:when>
					<c:when test="${sessionScope.user.userType.name eq 'SYSTEM_MENAGER'}">
			       <c:redirect url="./system-menager/system-menager.jsp" />
			    </c:when>
					<c:otherwise>
						<c:redirect url="./restaurant-menager/restaurant-menager.jsp" />
      		</c:otherwise>
			</c:choose>
		</c:if>
    
    <!-- Navigation -->
    <a id="menu-toggle" href="#" class="btn btn-dark btn-lg toggle"><i class="fa fa-bars"></i></a>
    <nav id="sidebar-wrapper">
        <ul class="sidebar-nav">
            <a id="menu-close" href="#" class="btn btn-light btn-lg pull-right toggle"><i class="fa fa-times"></i></a>
            <li class="sidebar-brand">
                <a href="#top"  onclick = $("#menu-close").click(); >Rezervacije restorana</a>
            </li>
            <li>
                <a href="#top" onclick = $("#menu-close").click(); >Početna</a>
            </li>
            <li>
                <a href="#" data-toggle="modal" data-target="#myModal" >Prijavite se </a>
            </li>
            <li>
                <a href="#portfolio" onclick = $("#menu-close").click(); >Registrujte se</a>
            </li>

        </ul>
    </nav>

    <!-- Header -->
    <header id="top" class="header">
        <div class="text-vertical-center">
            <h1>Restorani na dohvat ruke</h1>
            <h3>Rezervišite vaš termin već danas!</h3>
            <br>
            <a href="#restaurants" class="btn btn-dark btn-lg">Pogledajte restorane</a>
        </div>
    </header>

    <!-- About -->
    <section id="restaurants" class="about">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h2>Upoznajte vašu sledeću destinaciju za obrok!</h2>
                    <p class="lead"><div id="map_canvas" style="width:100%; height:500px"></div></p>
                </div>
            </div>
            <!-- /.row -->
        </div>
        <!-- /.container -->
    </section>


    <!-- Footer -->
    <footer>
        <div class="container">
            <div class="row">
                <div class="col-lg-10 col-lg-offset-1 text-center">
                    <h4><strong>Reserve Now</strong>
                    </h4>
                    <p>21000 Novi Sad Srbija</p>
                    <ul class="list-unstyled">
                        <li><i class="fa fa-phone fa-fw"></i> (123) 456-7890</li>
                        <li><i class="fa fa-envelope-o fa-fw"></i>  <a href="mailto:name@example.com">mihailo93@gmail.com</a>
                        </li>
                    </ul>
                    <br>
                    <ul class="list-inline">
                        <li>
                            <a href="#"><i class="fa fa-facebook fa-fw fa-3x"></i></a>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-twitter fa-fw fa-3x"></i></a>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-dribbble fa-fw fa-3x"></i></a>
                        </li>
                    </ul>
                    <hr class="small">
                    <p class="text-muted">Copyright &copy; Reserve Now 2016</p>
                </div>
            </div>
        </div>
    </footer>
    
    
            <!-- Registration Modal -->
        <div id="registrationModal" class="modal fade" role="dialog">
          <div class="modal-dialog">

            <!-- Modal content-->
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Informacija o registraciji</h4>
              </div>
              <div class="modal-body">
                  <span id="message"></span>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Zatvori</button>
              </div>
            </div>

          </div>
        </div>
    
 <!-- Modal log in-->
  <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">

 <div id="loginbox" style="margin-top:50px;" class="mainbox col-md-10 col-md-offset-3 col-sm-6 col-sm-offset-2">                    
            <div class="panel panel-info" >
                    <div class="panel-heading">
                        <div class="panel-title">Prijava</div>
                        <div style="float:right; font-size: 80%; position: relative; top:-10px"><a href="#" data-dismiss="modal">Zaboravljena lozinka?</a></div>
                    </div>     

                    <div style="padding-top:30px" class="panel-body" >

                        <div style="display:none" id="login-alert" class="alert alert-danger col-sm-12"></div>
                            
                        <form id="loginform" class="form-horizontal" role="form">
                                    
                            <div style="margin-bottom: 25px" class="input-group">
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                                        <input id="userEmail" type="text" class="form-control" name="userEmail" value="" placeholder="email">                                        
                                    </div>
                                
                            <div style="margin-bottom: 25px" class="input-group">
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                                        <input id="userPassword" type="password" class="form-control" name="userPassword" placeholder="lozinka">
                                    </div>
                                    

                                
                            <div class="input-group">
                                      <div class="checkbox">
                                        <label>
                                          <input id="login-remember" type="checkbox" name="remember" value="1"> Zapamti me
                                        </label>
                                      </div>
                                    </div>


                                <div style="margin-top:10px" class="form-group">
                                    <!-- Button -->

                                    <div class="col-sm-12 controls">
                                      <a id="btn-login" href="#" class="btn btn-success">Prijavi se  </a>
                                      <a id="btn-fblogin" href="#" class="btn btn-primary">Facebook</a>
                                      <a id="btn-fblogin" href="http://localhost:8080/restaurant/googlePlus" class="btn btn-danger">Google+</a>          
                                    </div>
                                </div>
                            
                              <div style="margin-top:10px" class="form-group">
                                    <!-- ERROR PROVIDER -->
                                    <span id = "email-error" class="label label-danger"></span>
                                </div>
                            
                                <div style="margin-top:10px" class="form-group">
                                    <span id = "password-error" class="label label-danger"></span>
                                </div>

                                <div class="form-group">
                                    <div class="col-md-12 control">
                                        <div style="border-top: 1px solid#888; padding-top:15px; font-size:85%" >
                                            Nemate nalog?! 
                                        <a href="#" onClick="$('#loginbox').hide(); $('#signupbox').show()">
                                            Registrujte se
                                        </a>
                                        </div>
                                    </div>
                                </div>    
                            </form>     



                        </div>                     
                    </div>  
        </div>
        <div id="signupbox" style="display:none; margin-top:50px" class="mainbox col-md-10 col-md-offset-3 col-sm-8 col-sm-offset-2">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <div class="panel-title">Registracija</div>
                            <div style="float:right; font-size: 85%; position: relative; top:-10px"><a id="signinlink" href="#" onclick="$('#signupbox').hide(); $('#loginbox').show()">Prijava</a></div>
                        </div>  
                        <div class="panel-body" >
                            <form id="signupform" class="form-horizontal" role="form">
                                
                                <div id="signupalert" style="display:none" class="alert alert-danger">
                                    <p>Error:</p>
                                    <span></span>
                                </div>
                                    
                                
                                  
                                <div class="form-group">
                                    <label for="userEmailsu" class="col-md-3 control-label">Email*</label>
                                    <div class="col-md-9">
                                        <input type="text" class="form-control" name="userEmailsu" placeholder="Email" id="userEmailsu" required>
                                    </div>
                                    <div style="margin-top:10px" class="form-group">
                                        <!-- ERROR PROVIDER -->
                                        <span id = "emailsu-error" class="label label-danger"></span>
                                    </div>
                                </div>
                                    
                                <div class="form-group">
                                    <label for="userName" class="col-md-3 control-label">Ime*</label>
                                    <div class="col-md-9">
                                        <input type="text" class="form-control" name="userName" placeholder="Vaše ime" id="userName" required>
                                    </div>
                                    <div style="margin-top:10px" class="form-group">
                                        <!-- ERROR PROVIDER -->
                                        <span id = "name-error" class="label label-danger"></span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="userSurname" class="col-md-3 control-label">Prezime*</label>
                                    <div class="col-md-9">
                                        <input type="text" class="form-control" name="userSurname" placeholder="Vaše prezime" id="userSurname" required>
                                    </div>
                                     <div style="margin-top:10px" class="form-group">
                                        <!-- ERROR PROVIDER -->
                                        <span id = "surname-error" class="label label-danger"></span>
                                    </div>                                   
                                </div>
                                <div class="form-group">
                                    <label for="userPasswordsu" class="col-md-3 control-label">Lozinka*</label>
                                    <div class="col-md-9">
                                        <input type="password" class="form-control" name="userPasswordsu" placeholder="Lozinka" id="userPasswordsu" required>
                                    </div>
                                    <div style="margin-top:10px" class="form-group">
                                        <!-- ERROR PROVIDER -->
                                        <span id = "passwordsu-error" class="label label-danger"></span>
                                    </div>                                    
                                </div>
                                    
                                <div class="form-group">
                                    <label for="userPasswordsuRepeat" class="col-md-3 control-label">Ponovo unesite lozinku*</label>
                                    <div class="col-md-9">
                                        <input type="password" class="form-control" name="userPasswordsuRepeat" placeholder="Ponovite lozinku" id="userPasswordsuRepeat" required>
                                    </div>
                                    <div style="margin-top:10px" class="form-group">
                                        <!-- ERROR PROVIDER -->
                                        <span id = "repeatPassword-error" class="label label-danger"></span>
                                    </div>                                    
                                </div>
                                 <div class="form-group">
                                        <div class="row">    
                                            <div class="col-xs-12 col-md-8 col-md-offset-3 col-sm-8 col-sm-offset-2">  
                                                <!-- image-preview-filename input [CUT FROM HERE]-->
                                                <div class="input-group image-preview">
                                                    <input type="text" class="form-control image-preview-filename" disabled="disabled"> <!-- don't give a name === doesn't send on POST/GET -->
                                                    <span class="input-group-btn">
                                                        <!-- image-preview-clear button -->
                                                        <button type="button" class="btn btn-default image-preview-clear" style="display:none;">
                                                            <span class="glyphicon glyphicon-remove"></span> Clear
                                                        </button>
                                                        <!-- image-preview-input -->
                                                        <div class="btn btn-default image-preview-input">
                                                            <span class="glyphicon glyphicon-folder-open"></span>
                                                            <span class="image-preview-input-title">Browse</span>
                                                            <input type="file" accept="image/png, image/jpeg, image/gif" name="uploadFile" id="uploadFile"/> <!-- rename it -->
                                                        </div>
                                                    </span>
                                                </div><!-- /input-group image-preview [TO HERE]--> 
                                            </div>
                                        </div>
                                    <div style="margin-top:10px" class="form-group">
                                        <!-- ERROR PROVIDER -->
                                        <span id = "fileUpload-error" class="label label-danger"></span>
                                    </div>                                    
                                </div>                               

                                <div class="form-group">
                                    <!-- Button -->                                        
                                    <div class="col-md-offset-3 col-md-9">
                                        <button id="btn-signup" type="button" class="btn btn-info"><i class="icon-hand-right"></i> &nbsp Registrujte se</button>
                                        <span style="margin-left:8px;">ili koristite</span>  
                                    </div>
                                </div>
                                
                                <div style="border-top: 1px solid #999; padding-top:20px"  class="form-group">
                                    
                                    <div class="col-md-offset-3 col-md-9">
                                        <button id="btn-fbsignup" type="button" class="btn btn-primary"><i class="icon-facebook"></i>Facebook</button>
                                        <button id="btn-fbsignup" type="button" class="btn btn-danger"><i class="icon-google"></i>Google</button>
                                    </div>                                           
                                        
                                </div>
                                
                                
                                
                            </form>
                         </div>
                    </div>

               
               
                

        </div>	    	    
            
            
        </div>
      
    </div>
      
      

    <!-- jQuery -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script>
    // Closes the sidebar menu
    $("#menu-close").click(function(e) {
        e.preventDefault();
        $("#sidebar-wrapper").toggleClass("active");
    });

    // Opens the sidebar menu
    $("#menu-toggle").click(function(e) {
        e.preventDefault();
        $("#sidebar-wrapper").toggleClass("active");
    });

    // Scrolls to the selected menu item on the page
    $(function() {
        $('a[href*=#]:not([href=#])').click(function() {
            if (location.pathname.replace(/^\//, '') == this.pathname.replace(/^\//, '') || location.hostname == this.hostname) {

                var target = $(this.hash);
                target = target.length ? target : $('[name=' + this.hash.slice(1) + ']');
                if (target.length) {
                    $('html,body').animate({
                        scrollTop: target.offset().top
                    }, 1000);
                    return false;
                }
            }
        });
    });
    </script>
      
    <!-- Facebook login -->
      <div id="fb-root"></div>
        <script>(function(d, s, id) {
          var js, fjs = d.getElementsByTagName(s)[0];
          if (d.getElementById(id)) return;
          js = d.createElement(s); js.id = id;
          js.src = "//connect.facebook.net/sr_RS/sdk.js#xfbml=1&version=v2.5&appId=147298848985731";
          fjs.parentNode.insertBefore(js, fjs);
        }(document, 'script', 'facebook-jssdk'));</script>

</body>

</html>
