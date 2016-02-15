<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<title>Tipovi restorana</title>
<meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
<meta HTTP-EQUIV="Expires" CONTENT="-1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
		<c:redirect url="../insufficient_privileges.jsp" />
	</c:if>

	<p>
		<a href="../restaurant-type/restaurantTypes.jsp">Tipovi restorana</a> <b><u>Restorani</u></b>
		<a href="../restaurant-menager/menagers.jsp">Menadzeri</a>
	</p>

	<p>
	<table>
		<tr>
			<td>&nbsp;</td>
			<td><a href="../../api/restaurant/prepareInsertRestaurant">Dodaj restoran</a></td>
		</tr>
	</table>
	<c:if test="${fn:length(sessionScope.restaurants) > 0}">
		<table>
			<tr>
				<th>Identifikator</th>
				<th>Naziv</th>
				<th>Adresa</th>
				<th>Ocena</th>
				<th>Tip</th>
			</tr>

			<c:forEach var="i" items="${sessionScope.restaurants}">
				<tr>
					<td>${i.id}</td>
					<td>${i.name}</td>
					<td>${i.address.brojUUlici} ${i.address.street.name} ${i.address.street.city.name} ${i.address.street.city.country.name}</td>
					<td>${i.grade}</td>
					<td>${i.restaurantType.name}</td>
					<form action="../../api/restaurant/prepareInsertRestaurant"
						method="get" class="prijavaForma" accept-charset="UTF-8">
					<td><button type='submit' value="${i.id}" name="restaurantId">Izmeni
							tip</button></td>
					</form>
					<form action="../../api/restaurant/deleteRestaurant"
						method="post" class="prijavaForma" accept-charset="UTF-8">
						<td><button type='submit' value="${i.id}" id="${i.id}"
								name="restaurantId">Obrisi tip</button></td>
					</form>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	</p>

	<c:if test="${fn:length(sessionScope.restaurants) == 0}">
		<p>Nema elemenata. Dodajte jedan.</p>
		<c:out value="${sessionScope.user.restaurants}" />
	</c:if>

	<p>
		<a href="./system-menager.jsp"><< Nazad</a> <a href="../../logout">Odjavi
			se</a>
	</p>

</body>

</html>
