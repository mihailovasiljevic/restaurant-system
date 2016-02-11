<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
  <head>
		<title>Tipovi restorana</title>
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

    <c:if test="${sessionScope.user.userType.name ne 'SYSTEM_MENAGER'}" >
      <c:redirect url="../insufficient_privileges.jsp" />
    </c:if>

    <p>
      <b><u>Tipovi restorana</u></b>
      <a href="../restaurant/restaurants.jsp">Restorani</a>
      <a href="../restaurant-menager/menagers.jsp">Menadzeri</a>
    </p>

    <p>
      <table>
        <tr>
            <td>&nbsp;</td>
            <td><a href="../../createRestauranType.jsp"></a>Dodaj tip</a></td>
        </tr>
      </table>

      <table>
        <tr>
            <th>Identifikator</th>
            <th>Naziv</th>
        </tr>
        <c:if test="${fn:length(sessionScope.user.restaurantTypes) > 0}" >
          <c:forEach var="i" begin="1" end="${fn:length(sessionScope.user.restaurantTypes)}">
             <tr>
               <td><c:out value="${i.id}"/></td>
               <td><c:out value="${i.name}"/></td>
             </tr>
          </c:forEach>
        </c:if>

        <c:if test="${fn:length(sessionScope.user.restaurantTypes) == 0}" >
          <p>Nema elemenata. Dodajte jedan.</p>
        </c:if>

      </table>
    </p>
    <p>
      <a href="./system-menager.jsp"><< Nazad</a> <a href="../logout">Odjavi se</a>
    </p>

  </body>

</html>