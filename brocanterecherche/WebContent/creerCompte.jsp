<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>                  
<head>
		<title>Inscription</title>
	
		<link rel="stylesheet" type="text/css" href="style.css" />

</head>

<body>

<div align="center">
<%@ include file="header.jspf"%>

	
	<!--content-->
		<div class="gadsl"></div>

<div class="content">
		<ul>
		<li class="left">
			<script type="text/javascript">
	var RecaptchaOptions = {
		lang : 'en',
		theme : 'clean'
	};
</script>
<div class="article">
	<ul>
		<li class="title">
			<h1>Inscription </h1>
		</li>
	</ul>
</div>

<div class="addPage">
	<ul>
		<li>
			
					
			<form action="createAccount"  method="post"  name="myForm">
			<c:choose>
				<c:when test="${not empty requestScope.erreurs['compte'] }">
				  <p class="erreur">${requestScope.erreurs['compte']}</p>
				</c:when>
				
			</c:choose>
			
			
		
			
		
			<input  name="nom" id="nom" placeholder="nom*"size="60" maxlength="30" type="text" value="<c:out value="${param.nom}"/>" />
			<span class="erreur">${requestScope.erreurs['nom']}</span>
			
			<br />
			
			
			<input  name="prenom" id="prenom" placeholder="prenom*"size="60" maxlength="30" type="text" value="<c:out value="${param.prenom}"/>" />
			<span class="erreur">${requestScope.erreurs['prenom']}</span>
			<br />
			<input  name="email" id="email" placeholder="Adresse email*" size="60" maxlength="30" type="text" value="<c:out value="${param.email}"/>" />
			<span class="erreur">${requestScope.erreurs['email']}</span>
			<br />
			
			<input  name="numeroPhone" id="numeroPhone" placeholder="téléphone*"size="60" maxlength="100" type="text" value="<c:out value="${param.numeroPhone}"/>" />
			<span class="erreur">${requestScope.erreurs['numeroPhone']}</span>
			<br />
				
			<input  name="password" id="password" placeholder="mot de passe*"size="60" maxlength="30" type="password" value="" />
			<span class="erreur">${requestScope.erreurs['password']}</span>
			
			<br />
			
			<input  name="confirmation" id="confirmation" placeholder="Confirmation mot de passe*"size="60" maxlength="30" type="password" value="" />
			<span class="erreur">${requestScope.erreurs['confirmation']}</span>
			
			<br />
			
			
			
			<input name="login" id="login" type="hidden" value="post">
			<button class="searchBox" id="login" type="submit">Inscription</button>
			
			</form>
			
			<br /><br />		</li>
	</ul>
</div>
		</li>
		
	</ul>
</div>	
	<!--footer-->
<div class="footer">
<p>Copyright 2018 / 2019 © Tous droits réservés</p>


</div>

</div>


</body>
</html>
