<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
  <head>
		<title>Menadzer sistema</title>
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
  
    <c:if test="${sessionScope.user == null}" >
      <c:redirect url="../login.jsp" />
    </c:if>
    
    <c:if test="${sessionScope.user.userType.name ne 'SYSTEM_MENAGER'}">
		<c:redirect url="../../insufficient_privileges.jsp" />
	</c:if>

    <p>
      <a href="../api/restaurant-type/restaurantTypes">Tipovi restorana</a>
      <a href="../api/restaurant/restaurants">Restorani</a>
      <a href="../api/restaurant-menager/restaurantMenagers">Menadzeri</a>
    </p>

    <p>
      Ime: <b><c:out value="${sessionScope.user.name}"/></b> Prezime: <b><c:out value="${sessionScope.user.surname}"/></b>
    </p>
    <p>
      Email: <b><c:out value="${sessionScope.user.email}"/></b>
    </p>
    <p>
      <a href="./change-user.jsp">Izmeni podatke</a><a href="../../logout">Odjavi se</a>
    </p>

  </body>

</html>
