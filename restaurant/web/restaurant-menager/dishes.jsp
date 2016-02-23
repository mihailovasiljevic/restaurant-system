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
	return /^\d{0,10}(\.\d{0,4}){0,1}$/.test(field[0].value);
}
function daLiJeRealanBroj(field){
	return /^(0\.[1-9]|[1-9][0-9]{0,2}(\.[1-9])?)$/.test(field[0].value);
}
        $(document).ready(function(){
                
            if("${sessionScope.infoMessage}" != "" && "${sessionScope.infoMessage}" != "null"){
                alert("${sessionScope.infoMessage}");
                <c:set var="infoMessage" scope="session" value=""/>
            }            
                $('#btn-updateType').hide();
                 $("#btn-type").click(
                    function(){
                        var dishName = $("#dishName").val();
                        var dishDescrp = $("#dishDescrp").val();
                        var dishPrice = $("#dishPrice").val();
                        
                            if( dishName == "" || dishName == undefined || dishName == null ) {
                                $("#dishName-error").text("Polje za naziv jela ne sme biti prazno.");

                                allGood = false;
                            } else {
                                $("#dishName-error").text("");

                                allGood = true;
                            }  
                        
                        
                           if( dishPrice == "" || dishPrice == undefined || dishPrice == null ) {
                                $("#dishPrice-error").text("Polje za cenu ne sme biti rpazno.");

                                allGood = false;
                            } else {
                                    if(!daLiJeCeoBroj($('#dishPrice'))){
                                       $("#dishPrice-error").text("Uneti broj nije pozitivan i realan.");
                                        allGood = false;                                
                                    }else{
                                         $("#dishPrice-error").text("");

                                        if(allGood != false)
                                            allGood = true;                               
                                    } 
    
                            }
                                               

                            if(allGood == true){
                                $.ajaxSetup({async:false});
                                $.ajax({
                                      url: "../api/dish/createDish",
                                      type: 'post',
                                      contentType: "application/x-www-form-urlencoded",
                                      data: {
                                       dishData:JSON.stringify({
                                           dishName:dishName,
                                           dishDescrp:dishDescrp,
                                           dishPrice:dishPrice,
                                       }),    
                                       cache: false,
                                       dataType:'json'
                                    },
                                      success: function (data, status) {
                                        if(data == "USPEH"){
                                             window.location.href = "/restaurant/api/dish/dishes";
                                             $('#btn-updateType').hide();
                                             $('#btn-type').show();
                                             return;
                                        }else{
                                            alert(data);
                                            window.location.href = "/restaurant/api/dish/dishes";
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
            
                $("#btn-updateType").click(
                    function(){
                        var dishName = $("#dishName").val();
                        var dishDescrp = $("#dishDescrp").val();
                        var dishPrice = $("#dishPrice").val();
                        
                            if( dishName == "" || dishName == undefined || dishName == null ) {
                                $("#dishName-error").text("Polje za naziv jela ne sme biti prazno.");

                                allGood = false;
                            } else {
                                $("#dishName-error").text("");

                                allGood = true;
                            }  
                        
                        
                           if( dishPrice == "" || dishPrice == undefined || dishPrice == null ) {
                                $("#dishPrice-error").text("Polje za cenu ne sme biti rpazno.");

                                allGood = false;
                            } else {
                               if(!daLiJeCeoBroj($('#dishPrice'))){
                                       $("#dishPrice-error").text("Uneti broj nije pozitivan i realan.");
                                        allGood = false;                                
                                    }else{
                                         $("#dishPrice-error").text("");

                                        if(allGood != false)
                                            allGood = true;                               
                                    } 
                            }                

                            if(allGood == true){
                                $.ajaxSetup({async:false});
                                $.ajax({
                                      url: "../api/dish/updateDish",
                                      type: 'post',
                                      contentType: "application/x-www-form-urlencoded",
                                      data: {
                                       dishData:JSON.stringify({
                                           dishName:dishName,
                                           dishDescrp:dishDescrp,
                                           dishPrice:dishPrice,
                                       }),    
                                       cache: false,
                                       dataType:'json'
                                    },
                                      success: function (data, status) {
                                        if(data == "USPEH"){
                                             window.location.href = "/restaurant/api/dish/dishes";
                                             $('#btn-updateType').hide();
                                             $('#btn-type').show();
                                             return;
                                        }else{
                                           alert(data);
                                            window.location.href = "/restaurant/api/dish/dishes";
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
            
                        $( "#restaurantTable" ).on( "click", "i", function( event ) {
                            var dishId = $(this).children().last().val();
                            var mess1 = "Neko je verovanto obrisao jelo koji pokusavate da izmenite. Osvezite stranicu.";
                            var mess2 = "Greska servera. Molimo pokusajte ponovo.";                        
                                $.ajaxSetup({async:false});
                                $.ajax({
                                      url: "../api/dish/prepareUpdateDish",
                                      type: 'post',
                                      contentType: "application/x-www-form-urlencoded",
                                      data: {
                                       dishId:JSON.stringify({
                                           dishId:dishId
                                       }),    
                                       cache: false,
                                       dataType:'json'
                                    },
                                      success: function (data, status) {
                                        if(data != mess1 && data != mess2){
                                             $('#dishName').val(data.name);
                                            $('#dishDescrp').val(data.description);
                                            $('#dishPrice').val(data.price);
                                             $('#btn-updateType').show();
                                             $('#btn-type').hide();
                                             $("#typeName-error").text("");
                                             return;
                                        }else{
                                           alert(data);
                                            window.location.href = "/restaurant/api/dish/dishes";
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
                            
                    });
            
                    $('#confirm-delete').on('show.bs.modal', function(e) {
                        $(this).find('.btn-ok').attr('href', $(e.relatedTarget).data('href'));

                        $('.debug-url').html('Delete URL: <strong>' + $(this).find('.btn-ok').attr('href') + '</strong>');
                    });

            
        });
        
        var pageMe = function(opts){
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
		<c:redirect url="../index.jsp" />
	</c:if>

	<c:if test="${sessionScope.user.userType.name ne 'RESTAURANT_MENAGER'}">
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
                    <h2>Menadzer restorana: ${sessionScope.user.name} ${sessionScope.user.surname}</h2>
                    <p class="lead"><div id="map_canvas" style="width:100%; height:500px">
                    
                                        <div class="row profile">
		<div class="col-md-3">
			<div class="profile-sidebar">
				<!-- SIDEBAR USERPIC -->
				<div class="profile-userpic">
                    	<c:if test="${sessionScope.user.image == null}">
                            <img src="../img/noPicture.png" class="img-responsive" alt="">
                        </c:if>
                        <c:if test="${sessionScope.user.image != null}">
                            <img src="${sessionScope.user.image.path}" class="img-responsive" alt="{sessionScope.user.image.realName}">
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
							<a href="../api/tables-configuration/tablesConfigurations" id="aTablesConfigurations">
							<i class="glyphicon glyphicon-align-center"></i>
							Konfiguracije stolova </a>
						</li>
						<li >
							<a href="../api/restaurant/restaurants">
							<i class="glyphicon glyphicon-registration-mark"></i>
							Restorani </a>
						</li>
						<li  >
						<li >
							<a href="../api/restaurant-type/restaurantTypes">
							<i class="glyphicon glyphicon-link"></i>
							Tipovi restorana </a>
						</li>
            <li >	
							<a href="../api/menu/menus">
							<i class="glyphicon glyphicon-book"></i>
							Jelovnici </a>
						</li>
                        <li class="active">
							<a href="#">
							<i class="glyphicon glyphicon-list"></i>
							Jela</a>
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
                        <button type="submit" class="btn btn-info" data-toggle="modal" data-target="#myModal" onclick="$('#updatebox').show();">Dodaj jelo</button>
                        <c:if test="${fn:length(sessionScope.dishes) > 0}">
                          <div class="table-responsive">
                            <table class="table table-hover">
                              <thead>
                                <tr>
                                  <th>Identifikator</th>
                                  <th>Naziv</th>
                                  <th>Opis</th>
                                  <th>Cena</th>
                                  <th>Pripada restoranu</th>
                                  <th>&nbsp;</th>
                                  <th>&nbsp;</th>
                                </tr>
                              </thead>
                              <tbody id="restaurantTable">
                                <c:forEach var="i" items="${sessionScope.dishes}">
                                    <tr>
                                        <td>${i.id}</td>
                                        <td>${i.name}</td>                              
                                        <td>${i.description}</td>
                                        <td>${i.price}</td>
                                        <td>${i.menu.restaurant.name}</td>
                                        <td><i><button type="button" class="btn btn-success" data-toggle="modal" data-target="#myModal" onclick="$('#updatebox').show();" id="updateButton" value="${i.id}">Izmeni
                                            jelo</button></i></td>
                                            
                                        <td><a href="#" data-href="../api/dish/deleteDish?dishId=${i.id}" data-toggle="modal" data-target="#confirm-delete">Obrisi jelo</a></td>
                                    </tr>
                                </c:forEach> 
                                </tbody>
                              </table>
                            </div>    
                            <div class="col-md-12 text-center">
                                <ul class="pagination pagination-lg pager" id="myPager"></ul>
                            </div>
                        </c:if>
                        <c:if test="${fn:length(sessionScope.dishes) == 0}">
                            <span class="label label-info">Nema jela. Dodajte jedno.</span>
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
    
 <!-- Modal create/update-->
  <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
          
        <div id="updatebox" style="display:none; margin-top:50px" class="mainbox col-md-10 col-md-offset-3 col-sm-8 col-sm-offset-2">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <div class="panel-title">Azuriranje jela</div>
                            <div style="float:right; font-size: 85%; position: relative; top:-20px"><button type="button" class="close" data-dismiss="modal" onclick="$('#updatebox').hide">&times;</button></div>
                        </div>  
                        <div class="panel-body" >
                            <form id="signupform" class="form-horizontal" role="form">
                                
                                <div id="signupalert" style="display:none" class="alert alert-danger">
                                    <p>Error:</p>
                                    <span></span>
                                </div>
                                    
                                
                                  
                                <div class="form-group">
                                    <label for="dishName" class="col-md-3 control-label">Naziv*</label>
                                    <div class="col-md-9">
                                        <input type="text" class="form-control" name="dishName" placeholder="Naziv jela" id="dishName" required>
                                    </div>
                                    <div style="margin-top:10px" class="form-group">
                                        <!-- ERROR PROVIDER -->
                                        <span id = "dishName-error" class="label label-danger"></span>
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <label for="dishDescrp" class="col-md-3 control-label">Opis jela</label>
                                    <div class="col-md-9">
                                        <textarea class="form-control" rows="5" name="dishDescrp" id="dishDescrp"></textarea>

                                    </div>
                                    <div style="margin-top:10px" class="form-group">
                                        <!-- ERROR PROVIDER -->
                                        <span id = "dishDescrp-error" class="label label-danger"></span>
                                    </div>
                                </div>
                                
                                  
                                <div class="form-group">
                                    <label for="dishPrice" class="col-md-3 control-label">Cena*</label>
                                    <div class="col-md-9">
                                        <input type="text" class="form-control" name="dishName" placeholder="Cena jela" id="dishPrice" required>
                                    </div>
                                    <div style="margin-top:10px" class="form-group">
                                        <!-- ERROR PROVIDER -->
                                        <span id = "dishPrice-error" class="label label-danger"></span>
                                    </div>
                                </div>                                
                                
                                
                                <div class="form-group">
                                    <!-- Button -->                                        
                                    <div class="col-md-offset-3 col-md-9">
                                        	
                                            <button id="btn-type" type="button" class="btn btn-info"><i class="icon-hand-right"></i>Dodajte jelo</button>
                                            <button id="btn-updateType" type="button" class="btn btn-info"><i class="icon-hand-right"></i>Izmenite jelo</button>                                      

                                    </div>
                                </div>              
                            </form>
                         </div>
                    </div>

               
               
                

        </div>	    	    
            
            
        </div>
      
    </div>
    </div>
    <div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
            
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">Potvrdite brisanje
            
                <div class="modal-body">
                    <p>Pokusavate da obrisete jelo.</p>
                    <p>Da li zelite da nastavite?</p>
                    <p class="debug-url"></p>
                </div>
                
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Odustani</button>
                    <a class="btn btn-danger btn-ok">Obrisi</a>
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