<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Dodajte novi tip restorana</title>

<meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
<meta HTTP-EQUIV="Expires" CONTENT="-1">
<link href="./theme.css" rel="stylesheet" type="text/css" />

<!--Bootstrap -->
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
	integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7"
	crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css"
	integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r"
	crossorigin="anonymous">

<!-- Latest compiled and minified JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"
	integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS"
	crossorigin="anonymous"></script>
</head>
<body>
	<c:if test="${sessionScope.user == null}">
		<c:redirect url="../login.jsp" />
	</c:if>

	<c:if test="${sessionScope.user.userType.name ne 'SYSTEM_MENAGER'}">
		<c:redirect url="../../insufficient_privileges.jsp" />
	</c:if>

	<p>
		<b><u>Tipovi restorana</u></b> <a href="../restaurant/restaurants.jsp">Restorani</a>
		<a href="../restaurant-menager/menagers.jsp">Menadzeri</a>
	</p>

	<div class="container">
		<form action="../../api/restaurant-type/createRestaurantType" method="post"
			class="prijavaForma" accept-charset="UTF-8">
			<div class="row">
				<div class="col-sm-4">&nbsp;</div>
				<div class="col-sm-4">
					Naziv tipa: <input type="text" name="typeName">
				</div>
				<div class="col-sm-4"></div>
			</div>
			<div class="row">
				<div class="col-sm-4">&nbsp;</div>
				<div class="col-sm-4"></div>
				<div class="col-sm-4">
					<input type="submit" name="submit" value="Dodaj tip">
				</div>
			</div>
		</form>
	</div>

</body>
</html>