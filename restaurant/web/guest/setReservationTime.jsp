<%@page import="java.util.HashSet"%>
<%@page import="restaurant.server.entity.User"%>
<%@page import="restaurant.server.entity.RestaurantTable"%>
<%@page import="java.util.List"%>
<%@page import="restaurant.server.entity.TablesConfiguration"%>
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
//niz funkcija za proveru onoga sta je uneseno
        function parseDatumOd(field){
            if (/^(\d{2})-(\d{2})-(\d{4})$/.test(field[0].value) == true){
                var dan = field[0].value.substr(0,2);
                var mesec = field[0].value.substr(3,2);
                var godina = field[0].value.substr(6,4);
                var brDana=-1;
                if(godina<2015 || godina>2018){
                    if($("#reservationDate-error").css("visibility") == "hidden"){
                        $("#reservationDate-error").css("visibility","visible");
                        $("#reservationDate-error").text("Godina je broj izmedju 2015 i 2018");
                    }else{
                        $("#reservationDate-error").text("Godina je broj izmedju 2015 i 2018");
                    }
                    return false;
                }
                else if(mesec >12 || mesec<1){
                    if($("#reservationDate-error").css("visibility") == "hidden"){
                        $("#reservationDate-error").css("visibility","visible");
                        $("#reservationDate-error").text("Mesec mora biti broj izmedju 1 i 12");
                    }else{
                        $("#reservationDate-error").text("Mesec mora biti broj izmedju 1 i 12");
                    }
                    return false;
                }
                else{
                     switch (mesec)
                        {
                            case 1: brDana = 31; break;
                            case 3: brDana = 31; break;
                            case 5: brDana = 31; break;
                            case 7: brDana = 31; break;
                            case 8: brDana = 31; break;
                            case 10: brDana = 31; break;
                            case 12: brDana = 31; break;

                            case 4: brDana = 30; break;
                            case 6: brDana = 30; break;
                            case 9: brDana = 30; break;
                            case 11: brDana = 30; break;

                            case 2:
                                var prestupna = false;
                                if ((godina % 4 == 0 && godina % 100 != 0 || godina % 400 == 0)==true)
                                    var prestupna = true;
                                if(prestupna)
                                    brDana = 29;
                                else
                                    var brDana = 28; break;
                        }
                        if ((mesec == 2 && (godina % 4 == 0 && godina % 100 != 0 || godina % 400 == 0) && dan > 29) == true)
                        {
                            if($("#reservationDate-error").css("visibility") == "hidden"){
                                $("#reservationDate-error").css("visibility","visible");
                                $("#reservationDate-error").text("U prestupnoj godini februar ima najviše 29 dana!");
                            }else{
                                $("#reservationDate-error").text("U prestupnoj godini februar ima najviše 29 dana!");
                            }
                            return true;
                        }
                        else if ((mesec == 2 && !(godina % 4 == 0 && godina % 100 != 0 || godina % 400 == 0) && dan > 28) == true)
                        {
                            if($("#reservationDate-error").css("visibility") == "hidden"){
                                $("#reservationDate-error").css("visibility","visible");
                                $("#reservationDate-error").text("U godini koja nije prestupna februar ima najviše 28 dana!");
                            }else{
                                $("#reservationDate-error").text("U godini koja nije prestupna februar ima najviše 28 dana!");
                            }
                            return true;
                        }
                        else if (((mesec == 1 || mesec == 3 || mesec == 5 || mesec == 7 || mesec == 8 || mesec == 10) && dan > 31) == true)
                        {
                            if($("#reservationDate-error").css("visibility") == "hidden"){
                                $("#reservationDate-error").css("visibility","visible");
                                $("#reservationDate-error").text("Mesec '" + mesec + "' može imati najviše 31 dan!");
                            }else{
                                $("#reservationDate-error").text("Mesec '" + mesec + "' može imati najviše 31 dan!");
                            }
                            return true;
                        }
                        else if (((mesec == 4 || mesec == 6 || mesec == 9 || mesec == 11) && dan > 30)==true)
                        {
                            if($("#reservationDate-error").css("visibility") == "hidden"){
                                $("#reservationDate-error").css("visibility","visible");
                                $("#reservationDate-error").text("Mesec '" + mesec + "' može imati najviše 30 dana!");
                            }else{
                                $("#reservationDate-error").text("Mesec '" + mesec + "' može imati najviše 30 dana!");
                            }
                            return true;
                        }
                        else if (dan < 1)
                        {
                            if($("#reservationDate-error").css("visibility") == "hidden"){
                                $("#reservationDate-error").css("visibility","visible");
                                $("#reservationDate-error").text("Svaki mesec mora imati najmanje 1 dan!");
                            }else{
                                $("#reservationDate-error").text("Svaki mesec mora imati najmanje 1 dan!");
                            }
                            return true;
                        }
                        if($("#reservationDate-error").css("visibility") == "visible")
                            $("#reservationDate-error").css("visibility","hidden");

                        return false;
                }
            }else{
                if($("#reservationDate-error").css("visibility") == "hidden"){
                    $("#reservationDate-error").css("visibility","visible");
                    $("#reservationDate-error").text("Format dd-mm-gggg");
                }else{
                    $("#reservationDate-error").text("Format dd-mm-gggg");
                }
            }
        }
        function daLiJeCeoBroj(field){
            return /^\+?(0|[1-9]\d*)$/.test(field[0].value);
        }
        function datum(field){
            return /^(0?[1-9]|[12][0-9]|3[01])[\-](0?[1-9]|1[012])[\-]\d{2}$/.test(field[0].value);
        }
        function vreme(field){
            return /^([01]?[0-9]|2[0-3]):[0-5][0-9]$/.test(field[0].value);
        }       
        $(document).ready(function(){
                
                   $("#btn-next").click(
                    function(){
                        var reservationDate = $("#reservationDate").val();
                        var reservationTime = $("#reservationTime").val();
                        var reservationForHowLong = $("#reservationForHowLong").val();
                        var allGood = true;
                        
                        if(reservationDate == undefined || reservationDate == null || reservationDate== ""){
                                $("#reservationDate-error").text("Polje za datum je prazno!");

                                allGood = false;                                
                        }else{
                                 $("#reservationDate-error").text("");

                                if(allGood != false)
                                    allGood = true;                               
                        }
                               
                        if(reservationTime == undefined || reservationTime == null || reservationTime== ""){
                                $("#reservationTime-error").text("Polje za vreme je prazno!");

                                allGood = false;                                
                        }else{
                                 $("#reservationTime-error").text("");

                                if(allGood != false)
                                    allGood = true;                               
                        }
                        
                        if(reservationForHowLong == undefined || reservationForHowLong == null || reservationForHowLong== ""){
                                $("#reservationForHowLong-error").text("Polje za duzinu trajanja rezervacije je prazno!");

                                allGood = false;                                
                        }else{
                                 $("#reservationForHowLong-error").text("");

                                if(allGood != false)
                                    allGood = true;                               
                        }                          
                        
                        if(!daLiJeCeoBroj($('#reservationForHowLong'))){
                                $("#reservationForHowLong-error").text("Niste uneli ceo broj");

                                allGood = false;                                
                        }else{
                            if(reservationForHowLong > 3){
                                $("#reservationForHowLong-error").text("Duzina ne moze da prelazi 3 sata.");

                                allGood = false;  
                            }else{
                                 $("#reservationForHowLong-error").text("");

                                if(allGood != false)
                                    allGood = true;    
                            }
                        }
                        
                        if(parseDatumOd($('#reservationDate')) != false){
                            allGood = false;                             
                        }else{
                                 $("#reservationDate-error").text("");

                                if(allGood != false)
                                    allGood = true;                               
                        }
                        
                        if(!vreme($('#reservationTime'))){
                                $("#reservationTime-error").text("Vreme mora biti u formatu HH:MM(12:40 npr.)");

                                allGood = false;                                
                        }else{

                                var sat = reservationTime.substr(0,2);

                                var minut = reservationTime.substr(3,2);
                            
                               if (sat.substring(sat.length-1) == ":")
                                {
                                    sat = sat.substring(0, sat.length-1);
                                }
                            if(sat <= 9 && sat >= 1){
                                   $("#reservationTime-error").text("Restoran pocinje da radi od 9 sati ujutru.");

                                allGood = false;                               
                            }else if(sat > 21){
                                $("#reservationTime-error").text("Posle 21h ne primamo rezervacije. Hvala na razumevanju.");

                                allGood = false;                              
                            }
                            else{
                            
                            $("#reservationTime-error").text("");

                                if(allGood != false)
                                    allGood = true;   
                            }
                            
                        } 
                        

                        
                        if(allGood == true){
                                $.ajaxSetup({async:false});
                                $.ajax({
                                      url: "../api/guest/checkReservation",
                                      type: 'post',
                                      contentType: "application/x-www-form-urlencoded",
                                      data: {
                                       reservationData:JSON.stringify({
                                           reservationDate:reservationDate,
                                           reservationTime: reservationTime,
                                           reservationForHowLong: reservationForHowLong
                                       }),    
                                       cache: false,
                                       dataType:'json'
                                    },
                                      success: function (data, status) {
                                        if(data == "USPEH"){
                                             window.location.href = "/restaurant/guest/setTables.jsp";
                                             $('#btn-updateType').hide();
                                             $('#btn-type').show();
                                             return;
                                        }else{
                                            alert(data);
                                            return;
                                        }
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
    
        });


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

	<c:if test="${sessionScope.user.userType.name ne 'GUEST'}">
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
                    <h2>Dobro dosli: ${sessionScope.user.name} ${sessionScope.user.surname}</h2>
                    <p class="lead"><div id="map_canvas" style="width:100%; height:500px">
                    
                                        <div class="row profile">
		<div class="col-md-3">
			<div class="profile-sidebar">
				<!-- SIDEBAR USERPIC -->
				<div class="profile-userpic">
                    	<c:if test="${sessionScope.image == null}">
                            <a herf="#" id="changePicture"><img src="../img/noPicture.png" class="img-responsive" alt=""></a>
                        </c:if>
                        <c:if test="${sessionScope.image != null}">
                            <a herf="#" id="changePicture"><img src="${sessionScope.image.path}" class="img-responsive" alt="{sessionScope.image.realName}"></a>
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
                        <li id="liTablesConfigurations">
                            <a href="./guest.jsp" id="aTablesConfigurations">
                            <i class="glyphicon glyphicon-align-center"></i>
                            Moj nalog </a>
                        </li>
                        <li >
                            <a href="#">
                            <i class="glyphicon glyphicon-registration-mark"></i>
                            Restorani </a>
                        </li>
                        <li  >
                        <li  class="active">
                            <a href="./friends.html">
                            <i class="glyphicon glyphicon-link"></i>
                            Prijatelji </a>
                        </li>   
                        <li >
                            <a href="./myReservations.jsp">
                            <i class="glyphicon glyphicon-link"></i>
                            Moje posete </a>
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
                        
                        <p><h2>Restoran: <b>${sessionScope.restaurantForReservation.name}</b></h2></p>
                         <div class="form-group">
                            <label for="reservationDate" class="col-md-3 control-label">Datum rezervacije: </label>
                            <div class="col-md-9">
                                <input type="text" class="form-control" name="reservationDateAndTime" placeholder="Datum rezervacije" id="reservationDate" required>

                            </div>
                            <div style="margin-top:10px" class="form-group">
                                <!-- ERROR PROVIDER -->
                                <span id = "reservationDate-error" class="label label-danger">Format: dd-mm-gggg</span>
                            </div>
                          </div>
                          <div class="form-group">
                            <label for="reservationTime" class="col-md-3 control-label">Vreme rezervacije</label>
                            <div class="col-md-9">
                                <input type="text" class="form-control" name="reservationTime" placeholder="U koliko sati..." id="reservationTime" required>

                            </div>
                            <div style="margin-top:10px" class="form-group">
                                <!-- ERROR PROVIDER -->
                                <span id = "reservationTime-error" class="label label-danger">Format: HH:MM</span>
                            </div>
                          </div>
                     
                         <div class="form-group">
                            <label for="reservationForHowLong" class="col-md-3 control-label">Duzina trajanja rezervacije</label>
                            <div class="col-md-9">
                                <input type="text" class="form-control" name="reservationForHowLong" placeholder="Na koliko sati(ceo broj sati)..." id="reservationForHowLong" required>

                            </div>
                            <div style="margin-top:10px" class="form-group">
                                <!-- ERROR PROVIDER -->
                                <span id = "reservationForHowLong-error" class="label label-danger"></span>
                            </div>
                          </div>
                     
                          <div class="form-group">
                                    <!-- Button -->                                        
                                    <div class="col-md-offset-3 col-md-9">                                        	
                                        <button id="btn-next" type="button" class="btn btn-info"><i class="icon-hand-right"></i>Dalje >></button>
                                    </div>
                        </div> 
                     
                     
                     
                    </div>
                </div>
 
                    
                
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
    
      
    </div>
    </div>

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
