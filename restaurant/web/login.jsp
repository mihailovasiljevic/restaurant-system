<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<fmt:setBundle basename="messages.messages"/>

<html>
	<head>
		<title><fmt:message key="prijava"/></title>
		<meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
		<meta HTTP-EQUIV="Expires" CONTENT="-1">
		<link href="./theme.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<form action="./LoginController" method="post" class="prijavaForma" accept-charset="ISO-8859-1">
			<table class="prijavaTabela">
				<tr>
					<td><fmt:message key="korisnickoIme"/>:</td>
					<td><input type="text" name="korisnickoIme">				
				</tr>
				<tr>
					<td><fmt:message key="lozinka"/>:</td>
					<td><input type="text" name="lozinka"></td>				
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td><input type="submit" name="submit" value="<fmt:message key="prijaviSe"/>"></td>				
				</tr>							
		</form>
	<body>	
</html>