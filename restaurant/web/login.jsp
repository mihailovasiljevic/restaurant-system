<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
	<head>
		<title>Prijava korisnika</title>
		<meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
		<meta HTTP-EQUIV="Expires" CONTENT="-1">
		<link href="./theme.css" rel="stylesheet" type="text/css" />

		<!--Bootstrap -->
		<!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>

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

		<div class="container">
			<form action="./login" method="post" class="prijavaForma" accept-charset="ISO-8859-1">
			  <div class="row">
					<div class="col-sm-4">&nbsp;</div>
			    <div class="col-sm-4">Email: <input type="text" name="userEmail"></div>
					<div class="col-sm-4"></div>
			  </div>
			  <div class="row">
					<div class="col-sm-4">&nbsp;</div>
					<div class="col-sm-4">Lozinka: <input type="text" name="userPassword"></div>
					<div class="col-sm-4"></div>
			  </div>
				<div class="row">
					<div class="col-sm-4">&nbsp;</div>
					<div class="col-sm-4"></div>
					<div class="col-sm-4"><input type="submit" name="submit" value="Prijavi se"></div>
				</div>
	   </form>
	 </div>
	<body>
</html>
