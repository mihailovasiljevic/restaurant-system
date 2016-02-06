<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<jsp:useBean id="vozilo" type="restaurant.server.entity.Vozilo" scope="request"/>
<jsp:useBean id="menjaci" type="java.util.List" scope="request"/>
<jsp:useBean id="goriva" type="java.util.List" scope="request"/>

<fmt:setBundle basename="messages.messages"/>

<html>
	<head>
		<title><fmt:message key="promenaVozila"/></title>
		<meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
		<meta HTTP-EQUIV="Expires" CONTENT="-1">
		<link href="./theme.css" rel="stylesheet" type="text/css" />
	</head>
	<c:if test="${sessionScope.admin==null}">
		<c:redirect url="./login.jsp" />
	</c:if>
	<body>
		<form action="./UpdateController" method="post" class="promenaVozilaForma" accept-charset="ISO-8859-1">
			<table class="promenaVozilaTabela">
				<tr>
					<td><fmt:message key="naziv"/>:</td>
					<td><input type="text" name="naziv" value="${vozilo.nazivVozila}"></td>			
				</tr>
				<tr>
					<td><fmt:message key="godinaProizvodnje"/>:</td>
					<td><input type="text" name="godinaProizvodnje" value="${vozilo.godinaProizvodnjeVozila}"></td>				
				</tr>
				<tr>
					<td><fmt:message key="snaga"/>:</td>
					<td><input type="text" name="snaga" value="${vozilo.snagaVozila}"></td>				
				</tr>
				<tr>
					<td><fmt:message key="predjeniPut"/>:</td>
					<td><input type="text" name="predjeniPut" value="${vozilo.predjeniPutVozila}"></td>				
				</tr>
				<tr>
					<td><fmt:message key="cena"/>:</td>
					<td><input type="text" name="cena" value="${vozilo.cenaVozila}"></td>				
				</tr>
				<tr>
					<td><fmt:message key="radnaZapremina"/>:</td>
					<td><input type="text" name="radnaZapremina" value="${vozilo.radnaZapreminaVozila}"></td>				
				</tr>
				<tr>
					<td><fmt:message key="brojVrata"/>:</td>
					<td><input type="text" name="brojVrata" value="${vozilo.brojVrataVozila}"></td>				
				</tr>
				<tr>
					<td><fmt:message key="brojSedista"/>:</td>
					<td><input type="text" name="brojSedista" value="${vozilo.brojSedistaVozila}"></td>				
				</tr>
				<tr>
					<td><fmt:message key="brojBrzina"/>:</td>
					<td><input type="text" name="brojBrzina" value="${vozilo.brojBrzinaVozila}"></td>				
				</tr>
				<tr>
					<td><fmt:message key="masa"/>:</td>
					<td><input type="text" name="masa" value="${vozilo.masaVozila}"></td>				
				</tr>	
				<tr>
					<td><fmt:message key="postojiKlimaUredjaj"/>:</td>
					<td>
						<c:if test="${vozilo.voziloImaKlimaUredjaj}" ><input type="checkbox" name="klimaUredjaj" checked="checked" value="true"></c:if>
						<c:if test="${!vozilo.voziloImaKlimaUredjaj}" ><input type="checkbox" name="klimaUredjaj" value="false"></c:if>
					</td>				
				</tr>
				<tr>
					<td><fmt:message key="gorivo"/>:</td>
					<td>
						<select name="gorivo">
						<c:forEach items="${goriva}" var="gorivo">
							<c:if test="${vozilo.gorivo.id == gorivo.id}">
								<option value="${gorivo.id}" selected="selected">${gorivo.tipGoriva}</option>
							</c:if>
							<c:if test="${vozilo.gorivo.id != gorivo.id}">
								<option value="${gorivo.id}" >${gorivo.tipGoriva}</option>
							</c:if>
						</c:forEach>
						</select>
					</td>				
				</tr>	
				<tr>
					<td><fmt:message key="menjac"/>:</td>
					<td>
						<select name="menjac">
						<c:forEach items="${menjaci}" var="menjac">
							<c:if test="${vozilo.menjac.id == menjac.id}">
								<option value="${menjac.id}" selected="selected">${menjac.tipMenjaca}</option>
							</c:if>
							<c:if test="${vozilo.menjac.id != menjac.id}">
								<option value="${menjac.id}">${menjac.tipMenjaca}</option>
							</c:if>
						</c:forEach>
						</select>
					</td>				
				</tr>
				<tr>
					<td><fmt:message key="datumPostavljanja"/>:</td>
					<td><input type="text" name="datumPostavljanja" value="${vozilo.datumPostavljanjaVozilaString}"></td>				
				</tr>					
				<tr>
					<td><input type="hidden" name="id" value="${vozilo.id}"></td>
					<td><input type="submit" name="submit" value="<fmt:message key="promeni"/>"></td>				
				</tr>
			</table>											
		</form>
		[<a href="./ReadController"><fmt:message key="listaVozila"/></a>]<br/>
		[<a href="./LogoutController"><fmt:message key="odjava"/></a>]<br/>
	</body>	
</html>