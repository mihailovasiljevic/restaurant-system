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
                               
            if("${sessionScope.infoMessage}" != "" && "${sessionScope.infoMessage}" != "null"){
                alert("${sessionScope.infoMessage}");
                <c:set var="infoMessage" scope="session" value=""/>
            }   
                   $("#btn-grade").click(
                    function(){
                        var grade = $("#grade").val();
                        var visitId = $("#visitId").val();
                        var allGood = false;
                       var allGood = true;
                        
                        if(grade == "" || grade== undefined || grade == null){
                            alert("Morate uneti ocenu!");
                            allGood = false;
                        }else{
                            allGoode = true;
                        }
                        if(allGood == true){
                                $.ajaxSetup({async:false});
                                $.ajax({
                                      url: "../api/guest/grade",
                                      type: 'post',
                                      contentType: "application/x-www-form-urlencoded",
                                      data: {
                                       gradeData:JSON.stringify({
                                           grade:grade,
                                           visitId: visitId
                                       }),    
                                       cache: false,
                                       dataType:'json'
                                    },
                                      success: function (data, status) {
                                        if(data == "USPEH"){
                                             window.location.href = "/restaurant/guest/myReservations.jsp";
                                             return;
                                        }else{
                                            alert(data);
                                            window.location.href = "/restaurant/guest/myReservations.jsp";
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
		<c:redirect url="../index.jsp" />
	</c:if>

	<c:if test="${sessionScope.user.userType.name ne 'GUEST'}">
		<c:redirect url="../index.jsp" />
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
                <a href="../index.jsp" onclick = $("#menu-close").click(); >Početna</a>
            </li>
            <li>
                <a href="../logout"> Odjavite se </a>
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
                    	<c:if test="${sessionScope.user.image == null}">
                            <a herf="#" id="changePicture"><img src="../img/noPicture.png" class="img-responsive" alt=""></a>
                        </c:if>
                        <c:if test="${sessionScope.user.image != null}">
                            <a herf="#" id="changePicture"><img src="${sessionScope.user.image.path}" class="img-responsive" alt="{sessionScope.user.image.realName}"></a>
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
                            <a href="../api/guest/restaurants">
                            <i class="glyphicon glyphicon-registration-mark"></i>
                            Restorani </a>
                        </li>
                        <li  >
                        <li>
                            <a href="./friends.jsp">
                            <i class="glyphicon glyphicon-link"></i>
                            Prijatelji </a>
                        </li>   
                         <li class="active">
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
                     	
                <c:if test="${fn:length(sessionScope.user.visits) > 0}">  
                 <div class="container">
                    <div class="row">
                          <div class="table-responsive">
                            <table class="table table-hover">
                              <thead>
                                <tr>
                                  <th>Datum rezervacije</th>
                                  <th>Restoran</th>
                                  <th>Moja ocena</th>
                                  <th>&nbsp;</th>
                                  <th>&nbsp;</th>
                                </tr>
                              </thead>
                              <tbody id="restaurantTable">
                                <c:forEach var="i" items="${sessionScope.user.visits}">
                                    <tr>                                     
                                        <td>${i.reservation.date}</td>
                                        <td>${i.restaurant.name}</td>
                                        <c:if test="${i.grade == -1}">
                                            <td>
                                                
                                                <div class="form-group">
                                                    
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control" name="grade" id="grade">
                                                    </div>
                                                                                                        
                                                    <div class="col-md-3">
                                                        <input type="hidden" class="form-control" name="grade" id="visitId" value="${i.id}">
                                                    </div>
                                                    <div class="col-md-offset-3 col-md-3">
                                                        <button id="btn-grade" type="button" class="btn btn-info"><i class="icon-hand-right"></i>Oceni</button>                        
                                                    </div>
                                                </div>
                                            
                                            </td>
                                        </c:if>
                                        <c:if test="${i.grade != -1}">
                                            <td>
                                                
                                                <div class="form-group">
                                                    
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control" name="grade" id="grade" value="${i.grade}" disabled>
                                                    </div>
                                                </div>

                                            </td>
                                        </c:if>                                        
             
                                    </tr>
                                </c:forEach> 
                                </tbody>
                              </table>
                            </div>    
                            <div class="col-md-12 text-center">
                                <ul class="pagination pagination-lg pager" id="myPager"></ul>
                            </div>
                    </div>
                </div>
                </c:if> 
                
                     
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
