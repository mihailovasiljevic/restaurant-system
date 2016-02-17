<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>    
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
                
            
        });
        
        $.fn.pageMe = function(opts){
            var $this = this,
                defaults = {
                    perPage: 7,
                    showPrevNext: false,
                    hidePageNumbers: false
                },
                settings = $.extend(defaults, opts);

            var listElement = $this;
            var perPage = settings.perPage; 
            var children = listElement.children();
            var pager = $('.pager');

            if (typeof settings.childSelector!="undefined") {
                children = listElement.find(settings.childSelector);
            }

            if (typeof settings.pagerSelector!="undefined") {
                pager = $(settings.pagerSelector);
            }

            var numItems = children.size();
            var numPages = Math.ceil(numItems/perPage);

            pager.data("curr",0);

            if (settings.showPrevNext){
                $('<li><a href="#" class="prev_link">«</a></li>').appendTo(pager);
            }

            var curr = 0;
            while(numPages > curr && (settings.hidePageNumbers==false)){
                $('<li><a href="#" class="page_link">'+(curr+1)+'</a></li>').appendTo(pager);
                curr++;
            }

            if (settings.showPrevNext){
                $('<li><a href="#" class="next_link">»</a></li>').appendTo(pager);
            }

            pager.find('.page_link:first').addClass('active');
            pager.find('.prev_link').hide();
            if (numPages<=1) {
                pager.find('.next_link').hide();
            }
            pager.children().eq(1).addClass("active");

            children.hide();
            children.slice(0, perPage).show();

            pager.find('li .page_link').click(function(){
                var clickedPage = $(this).html().valueOf()-1;
                goTo(clickedPage,perPage);
                return false;
            });
            pager.find('li .prev_link').click(function(){
                previous();
                return false;
            });
            pager.find('li .next_link').click(function(){
                next();
                return false;
            });

            function previous(){
                var goToPage = parseInt(pager.data("curr")) - 1;
                goTo(goToPage);
            }

            function next(){
                goToPage = parseInt(pager.data("curr")) + 1;
                goTo(goToPage);
            }

            function goTo(page){
                var startAt = page * perPage,
                    endOn = startAt + perPage;

                children.css('display','none').slice(startAt, endOn).show();

                if (page>=1) {
                    pager.find('.prev_link').show();
                }
                else {
                    pager.find('.prev_link').hide();
                }

                if (page<(numPages-1)) {
                    pager.find('.next_link').show();
                }
                else {
                    pager.find('.next_link').hide();
                }

                pager.data("curr",page);
                pager.children().removeClass("active");
                pager.children().eq(page+1).addClass("active");

            }
        };

    </script>
    
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
  <script src="scripts/login.js"></script>
</head>

<body>
	<c:if test="${sessionScope.user == null}">
		<c:redirect url="../login.jsp" />
	</c:if>

	<c:if test="${sessionScope.user.userType.name ne 'SYSTEM_MENAGER'}">
		<c:redirect url="../insufficient_privileges.jsp" />
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
        </ul>
    </nav>

    <!-- Header -->
    <header id="top" class="header">
        <div class="text-vertical-center">
            <h1>&nbsp;</h1>
            <h3>&nbsp;</h3>
        </div>
    </header>

    <!-- About -->
    <section id="restaurants" class="about">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h2>Menadzer sistema: ${sessionScope.user.name} ${sessionScope.user.surname}</h2>
                    <p class="lead"><div id="map_canvas" style="width:100%; height:500px">
                    
                                        <div class="row profile">
		<div class="col-md-3">
			<div class="profile-sidebar">
				<!-- SIDEBAR USERPIC -->
				<div class="profile-userpic">
                    	<c:if test="${sessionScope.image == null}">
                            <img src="../img/noPicture.png" class="img-responsive" alt="">
                        </c:if>
                        <c:if test="${sessionScope.image != null}">
                            <img src="${sessionScope.image.path}" class="img-responsive" alt="{sessionScope.image.realName}">
                        </c:if>

				</div>
				<!-- END SIDEBAR USERPIC -->
				<!-- SIDEBAR USER TITLE -->
				<div class="profile-usertitle">
					<div class="profile-usertitle-name">
						${sessionScope.user.name} ${sessionScope.user.surname}
					</div>
					<div class="profile-usertitle-job">
						${sessionScope.user.userType.name}
					</div>
				</div>
				<!-- END SIDEBAR USER TITLE -->
				<!-- SIDEBAR BUTTONS -->
				<!--<div class="profile-userbuttons">-->
				<!--	<button type="button" class="btn btn-success btn-sm">Follow</button>-->
				<!--	<button type="button" class="btn btn-danger btn-sm">Message</button>-->
				<!--</div>-->
				<!-- END SIDEBAR BUTTONS -->
				<!-- SIDEBAR MENU -->
				<div class="profile-usermenu">
					<ul class="nav">
						<li class="active" id="restaurantType">
							<a href="#" id="aRestaurantType">
							<i class="glyphicon glyphicon-link"></i>
							Tipovi restorana </a>
						</li>
						<li id="restaurant" id="aRestaurant">
							<a href="../api/restaurant/restaurants" target="_blank">
							<i class="glyphicon glyphicon-registration-mark"></i>
							Restoran </a>
						</li>
						<li id="restaurantMenager" id="aRestaurantMenager">
							<a href="../api/restaurant-menager/restaurantMenagers">
							<i class="glyphicon glyphicon-briefcase"></i>
							Menadzeri restorana </a>
						</li>
					</ul>
				</div>
				<!-- END MENU -->
			</div>
		</div>
		<div class="col-md-9">
            <div class="profile-content" id="content">
			   
                <div class="container">
                    <div class="row">
                        <c:if test="${fn:length(sessionScope.restaurantTypes) > 0}">
                          <div class="table-responsive">
                            <table class="table table-hover">
                              <thead>
                                <tr>
                                  <th>Identifikator</th>
                                  <th>Ime</th>
                                  <th>&nbsp;</th>
                                  <th>&nbsp;</th>
                                </tr>
                              </thead>
                              <tbody id="restaurantTable">
                                <c:forEach var="i" items="${sessionScope.restaurantTypes}">
                                    <tr>
                                        <td>${i.id}</td>
                                        <td>${i.name}</td>
                                        <form action="../../api/restaurant-type/updateRestaurantType"
                                            method="post" class="prijavaForma" accept-charset="UTF-8">
                                        <td><a class="btn mini blue-stripe" href="#">Izmeni
                                                tip</a></td>
                                        </form>
                                        <form action="../../api/restaurant-type/deleteRestaurantType"
                                            method="post" class="prijavaForma" accept-charset="UTF-8">
                                            <td><a href="#" class="confirm-delete btn mini red-stripe" role="button" data-title="${i.name}" data-id="${i.id}">Obrisi tip</a></td>
                                        </form>
                                    </tr>
                                </c:forEach> 
                                </tbody>
                              </table>
                            </div>    
                            <div class="col-md-12 text-center">
                                <ul class="pagination pagination-lg pager" id="myPager"></ul>
                            </div>
                        </c:if>
                        <c:if test="${fn:length(sessionScope.restaurantTypes) == 0}">
                            <span class="label label-info">Nema tipova restorana. Dodajte jedan.</span>
                        </c:if>
                    </div>
                </div>
                
            </div>
		</div>
	</div>
                    
                    
                    
                    
                    </div></p>
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
