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

        $(document).ready(function(){
                
            
                $('#btn-updateType').hide();
                 $("#btn-updateUser").click(
                    function(){
                        var userName = $("#userName").val();
                        var userSurname = $("#userSurname").val();
                        var userEmail = $("#userEmail").val();
                        var userStreet = $("#userStreet :selected").val();
                        var userStreetNo = $("#userStreetNo").val();
                        
                            if( userName == "" || userName == undefined || userName == null ) {
                                $("#userName-error").text("Polje za vase ime ne sme biti prazno.");

                                allGood = false;
                            } else {
                                $("#userName-error").text("");

                                allGood = true;
                            }  
                        
                            if( userSurname == "" || userSurname == undefined ||userSurname == null ) {
                                $("#userSurname-error").text("Polje za vase prezime ne sme ostati prazno");

                                allGood = false;
                            } else {
                                $("#userSurname-error").text("");

                                if(allGood != false)
                                    allGood = true;
                            }

                           if( userStreetNo == "" || userStreetNo == undefined || userStreetNo == null ) {
                                $("#userStreetNo-error").text("Polje za broj u ulici n sme ostati prazno.");

                                allGood = false;
                            } else {
                                $("#userStreetNo-error").text("");

                                  if(allGood != false)
                                    allGood = true;
                            
                            }

                            if(allGood == true){
                                $.ajaxSetup({async:false});
                                $.ajax({
                                      url: "../api/guest/updateGuest",
                                      type: 'post',
                                      contentType: "application/x-www-form-urlencoded",
                                      data: {
                                       guestData:JSON.stringify({
                                           userName:userName,
                                           userSurname:userSurname,
                                           userEmail:userEmail,
                                           userStreet:userStreet,
                                           userStreetNo:userStreetNo
                                       }),    
                                       cache: false,
                                       dataType:'json'
                                    },
                                      success: function (data, status) {
                                        if(data == "USPEH"){
                                             window.location.href = "/restaurant/guest/guest.jsp";
                                             $('#btn-updateType').hide();
                                             $('#btn-type').show();
                                             return;
                                        }else{
                                            $("#confName-error").text(data);
                                            $("#updateBox").hide();
                                            $("#myModal").hide();
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
                 $("#btn-changePassword").click(
                    function(){
                        
                        $("#useOldPassword").prop('disabled', false);
                        $("#userNewPassword").prop('disabled', false);
                        $("#userNewPasswordRep").prop('disabled', false);
                    });
        
                $("#btn-changePasswordAppr").click(
                    function(){
                        var useOldPassword = $("#useOldPassword").val();
                        var userNewPassword = $("#userNewPassword").val();
                        var userNewPasswordRep = $("#userNewPasswordRep").val();
                        
                            if( useOldPassword == "" || useOldPassword == undefined || useOldPassword == null ) {
                                $("#useOldPassword-error").text("Polje za staru lozinku ne sme biti prazno.");

                                allGood = false;
                            } else {
                                $("#userName-error").text("");

                                allGood = true;
                            }  
                        
                            if( userNewPassword == "" || userNewPassword == undefined ||userNewPassword == null ) {
                                $("#userNewPassword-error").text("Polje za novu lozinku ne sme ostati prazno");

                                allGood = false;
                            } else {
                                $("#userNewPassword-error").text("");

                                if(allGood != false)
                                    allGood = true;
                            }
                     
                            if(userNewPassword != userNewPasswordRep){
                                
                                $("#userNewPasswordRep-error").text("Lozinke sen e poklapaju.");

                                allGood = false;
                            }    else {
                                $("#userNewPasswordRep-error").text("");

                                  if(allGood != false)
                                    allGood = true;
                            }
                        
                            if(allGood == true){
                                $.ajaxSetup({async:false});
                                $.ajax({
                                      url: "../api/guest/changePassword",
                                      type: 'post',
                                      contentType: "application/x-www-form-urlencoded",
                                      data: {
                                       guestData:JSON.stringify({
                                           userNewPassword:userNewPassword,
                                           userOldPassword: useOldPassword
                                       }),    
                                       cache: false,
                                       dataType:'json'
                                    },
                                      success: function (data, status) {
                                        if(data == "USPEH"){
                                             window.location.href = "/restaurant/guest/guest.jsp";
                                             $('#btn-updateType').hide();
                                             $('#btn-type').show();
                                             return;
                                        }else{
                                            $("#confName-error").text(data);
                                            $("#updateBox").hide();
                                            $("#myModal").hide();
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
        
                $("#btn-search").click(
                    function(){
                        var searchFriendsBy = $("#searchFriendsBy :selected").val();
                        var searchFriends = $("#searchFriends").val();
                        var allGood = false;
                          
                            if( searchFriends == "" || searchFriends == undefined || searchFriends == null ) {
                                $("#searchFriends-error").text("Unesite nesto u polje za pretragu");

                                allGood = false;
                            } else {
                                $("#searchFriends-error").text("");

                                allGood = true;
                            }  
                        
                            if(allGood == true){
                                $.ajaxSetup({async:false});
                                $.ajax({
                                      url: "../api/guest/searchFriends",
                                      type: 'post',
                                      contentType: "application/x-www-form-urlencoded",
                                      data: {
                                       searchData:JSON.stringify({
                                           searchFriendsBy:searchFriendsBy,
                                           searchFriends: searchFriends
                                       }),    
                                       cache: false,
                                       dataType:'json'
                                    },
                                      success: function (data, status) {
                                        if(data == "USPEH"){
                                             window.location.href = "/restaurant/guest/guest.jsp";
                                             $('#btn-updateType').hide();
                                             $('#btn-type').show();
                                             return;
                                        }else{
                                            window.location.href = "/restaurant/guest/guest.jsp";
                                            $("#confName-error").text(data);
                                            $("#updateBox").hide();
                                            $("#myModal").hide();
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
            
                   $('#confirm-delete').on('show.bs.modal', function(e) {
                        $(this).find('.btn-ok').attr('href', $(e.relatedTarget).data('href'));

                        $('.debug-url').html('Delete URL: <strong>' + $(this).find('.btn-ok').attr('href') + '</strong>');
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
                        <li id="liTablesConfigurations" class="active">
                            <a href="#" id="aTablesConfigurations">
                            <i class="glyphicon glyphicon-align-center"></i>
                            Moj nalog </a>
                        </li>
                        <li >
                            <a href="../api/guest/restaurants">
                            <i class="glyphicon glyphicon-registration-mark"></i>
                            Restorani </a>
                        </li>
                        <li  >
                        <li >
                            <a href="./friends.jsp">
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
                    	<form id="changeUser" class="form-horizontal" role="form">
                                  
                                <div class="form-group">
                                    <label for="userName" class="col-md-3 control-label">Ime</label>
                                    <div class="col-md-9">
                                        <input type="text" class="form-control" name="userName" value="${sessionScope.user.name}" id="userName" required>
                                    </div>
                                    <label for="userSurname" class="col-md-3 control-label">Prezime</label>
                                    <div class="col-md-9">
                                        <input type="text" class="form-control" name="userSurname" value="${sessionScope.user.surname}" id="userSurname" required>
                                    </div>
                                    <div style="margin-top:10px" class="form-group">
                                        <!-- ERROR PROVIDER -->
                                        <span id = "userName-error" class="label label-danger"></span>
                                    </div>
                                    <div style="margin-top:10px" class="form-group">
                                        <!-- ERROR PROVIDER -->
                                        <span id = "userSurname-error" class="label label-danger"></span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="userEmail" class="col-md-3 control-label">Email</label>
                                    <div class="col-md-9">
                                        <input type="text" class="form-control" name="userEmail" value="${sessionScope.user.email}" id="userEmail" disabled>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="userStreet" class="col-md-3 control-label">Adresa</label>
                                    <div class="col-md-9">
                                        <select id="userStreet" class="form-control">
                                            <option value="${sessionScope.user.address.street.id}">${sessionScope.user.address.street.name}</option>
                                            <c:forEach var="i" items="${sessionScope.streets}">
                                                <c:if test="${sessionScope.user.address.street.id != i.id}">
                                                    <option value="${i.id}">${i.name}</option>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                    </div>
                        
                                </div>
                                
                                <div class="form-group">
                                    <label for="userStreetNo" class="col-md-3 control-label">Broj u ulici</label>
                                    <div class="col-md-9">
                                        <input type="text" class="form-control" name="userStreetNo" value="${sessionScope.user.address.brojUUlici}" id="userStreetNo" required>
                                    </div>
                                    <div style="margin-top:10px" class="form-group">
                                        <!-- ERROR PROVIDER -->
                                        <span id = "userStreetNo-error" class="label label-danger"></span>
                                    </div>                                  
                               </div>
                            
                                 <div class="form-group">
                                    <!-- Button -->                                        
                                    <div class="col-md-offset-3 col-md-9">
                                        <button id="btn-updateUser" type="button" class="btn btn-info"><i class="icon-hand-right"></i>Izmenite podatke</button>                               
                                    </div>
                                </div>  

                        </form>
                         <div class="form-group">
                                    <!-- Button -->                                        
                                    <div class="col-md-offset-3 col-md-9">
                                          <span id = "userName-error" class="label label-info">Promena lozinke:</span>                   
                                    </div>
                                    <div class="col-md-offset-3 col-md-9">
                                          <span id = "nesto-error" class="label label-info"></span>                   
                                    </div>
                          </div> 
                    	<form id="changePassword" class="form-horizontal" role="form">
                                  
                                <div class="form-group">
                                    <label for="useOldPassword" class="col-md-3 control-label">Stara lozinka</label>
                                    <div class="col-md-9">
                                        <input type="password" class="form-control" name="useOldPassword" id="useOldPassword" disabled>
                                    </div>
                                    <div style="margin-top:10px" class="form-group">
                                        <!-- ERROR PROVIDER -->
                                        <span id = "useOldPassword-error" class="label label-danger"></span>
                                    </div>

                                </div>
                                <div class="form-group">
                                    <label for="userNewPassword" class="col-md-3 control-label">Nova lozinka</label>
                                    <div class="col-md-9">
                                        <input type="password" class="form-control" name="userNewPassword" id="userNewPassword" disabled>
                                    </div>
                                    <div style="margin-top:10px" class="form-group">
                                        <!-- ERROR PROVIDER -->
                                        <span id = "userNewPassword-error" class="label label-danger"></span>
                                    </div>
                                </div>
                               <div class="form-group">
                                    <label for="userNewPasswordRep" class="col-md-3 control-label">Ponovite novu lozinku</label>
                                    <div class="col-md-9">
                                        <input type="password" class="form-control" name="userNewPasswordRep" id="userNewPasswordRep" disabled>
                                    </div>
                                    <div style="margin-top:10px" class="form-group">
                                        <!-- ERROR PROVIDER -->
                                        <span id = "userNewPasswordRep-error" class="label label-danger"></span>
                                    </div>
                                </div>
                                 <div class="form-group">
                                    <!-- Button -->                                        
                                    <div class="col-md-offset-3 col-md-9">
                                        <button id="btn-changePassword" type="button" class="btn btn-info"><i class="icon-hand-right"></i>Izmenite Lozinku</button>
                                        <button id="btn-changePasswordAppr" type="button" class="btn btn-danger"><i class="icon-hand-right" disabled></i>Potvrdite izmenu</button>                               
                                    </div>
                                </div>  

                        </form>
                        <p><h2>Prijatelji</h2></p>
                   	<form id="changePassword" class="form-horizontal" role="form">
                                  
                                <div class="form-group">
                                    <label for="searchFriendsBy" class="col-md-3 control-label">Pretrazi prijatelje za dodavanje</label>
                                    <div class="col-md-9">
                                        <select class="form-control" id="searchFriendsBy">
                                            <option value="0">Pretrazi po imenu</option>
                                            <option value="1">Pretrazi po prezimenu</option>
                                        </select>
                                        
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="searchFriends" class="col-md-3 control-label">Unesite ime ili prezime: </label>
                                    <div class="col-md-9">
                                        <input type="text" class="form-control" name="searchFriends" id="searchFriends">
                                    </div>
                                    <div style="margin-top:10px" class="form-group">
                                        <!-- ERROR PROVIDER -->
                                        <span id = "searchFriends-error" class="label label-danger"></span>
                                    </div>
                                </div>
                                 <div class="form-group">
                                    <!-- Button -->                                        
                                    <div class="col-md-offset-3 col-md-9">
                                        <button id="btn-search" type="button" class="btn btn-info"><i class="icon-hand-right"></i>Pretrazi</button>                        
                                    </div>
                                </div>  

                        </form>                        
                        
                <c:if test="${sessionScope.friends != null}">  
                 <div class="container">
                    <div class="row">
                          <div class="table-responsive">
                            <table class="table table-hover">
                              <thead>
                                <tr>
                                  <th>Slika</th>
                                  <th>Ime</th>
                                  <th>Prezime</th>
                                  <th>&nbsp;</th>
                                  <th>&nbsp;</th>
                                </tr>
                              </thead>
                              <tbody id="restaurantTable">
                                <c:forEach var="i" items="${sessionScope.friends}">
                                    <tr>
                                         <c:if test="${i.image == null}">
                                            <td><img src="../img/noPicture.png" class="img-responsive" alt="" width="32px" height="32px"></td>
                                        </c:if>
                                        <c:if test="${i.image != null}">
                                            <td><img src="${i.image.path}" class="img-responsive" alt="{sessionScope.image.realName}" width="32px" height="32px"></td>
                                        </c:if>                                       
                                        <td>${i.name}</td>
                                        <td>${i.surname}</td>
                                        
                                        <c:if test="${fn:length(sessionScope.user.myFriends) > 0}">

                                           <td><a href="../api/guest/addFriend?userId=${i.id}&page=guest">Dodaj</a></td>    
     									   <td><a href="#" data-href="../api/guest/deleteFriend?userId=${i.id}&page=guest" data-toggle="modal" data-target="#confirm-delete">Ukloni iz liste prijatelja</a></td>
     									  
 
                                            
                                        </c:if>
                                        <c:if test="${fn:length(sessionScope.user.myFriends) == 0}">
                                            <td><a href="../api/guest/addFriend?userId=${i.id}&page=guest">Dodaj</a></td> 
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
    
      
    </div>
    </div>
    <div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
            
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">Potvrdite brisanje iz liste prijatelja</h4>
            
                <div class="modal-body">
                    <p>Pokusavate da uklonite prijatelja.</p>
                    <p>Da li zelite da nastavite?</p>
                    <p class="debug-url"></p>
                </div>
                
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Odustani</button>
                    <a class="btn btn-danger btn-ok">Ukloni</a>
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
