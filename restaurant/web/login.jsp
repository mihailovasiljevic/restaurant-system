<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
	<head>
		<title>Prijava korisnika</title>
		<meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
		<meta HTTP-EQUIV="Expires" CONTENT="-1">
		<link href="./theme.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<form action="./login" method="post" class="prijavaForma" accept-charset="ISO-8859-1">
			<table class="prijavaTabela">
				<tr>
					<td>Korisnicko ime:</td>
					<td><input type="text" name="userEmail">				
				</tr>
				<tr>
					<td>Lozinka:</td>
					<td><input type="text" name="userPassword"></td>				
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td><input type="submit" name="submit" value="Prijavi se"></td>				
				</tr>							
		</form>
	<body>	
</html>