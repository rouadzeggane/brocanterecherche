<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>                  
<head>
		<title>Connexion</title>
	
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
			<h1>Se Connecter</h1>
		</li>
	</ul>
</div>

<div class="addPage">
	<ul>
		<li>
			
					
			<form action="login" onsubmit="return validateForm()" method="post"  name="myForm">
      			
			
			<label>Email*:</label>
			<input class='requierd' name="email" id="email" size="60" maxlength="100" type="text" value="" />
			<label>Mot de passe*:</label>
			<input class='requierd' name="password" id="password" size="60" maxlength="100" type="password" value="" />
			
			<input name="login" id="login" type="hidden" value="post">
			<button  class="searchBox" id="login" type="submit">Connexion</button>
			</form>
			
			<br /><br /><br /><br /><br /><br /><br /><br /><br />
			<br /><br /><br /><br /><br /><br />	</li>
	</ul>
</div>
		</li>
		
	</ul>
</div>	
	<!--footer-->
<div class="footer">
<p>Copyright 2018 / 2019  © Tous droits réservés</p>

<script>
function validateForm()
{
var x=document.forms["myForm"]["email"].value;
if (x==null || x=="")
  {
  alert("Username must be filled out");
  document.getElementById('un').focus();
  return false;
  }
var y=document.forms["myForm"]["password"].value;
if (y==null || y=="")
  {
  alert("password must be filled out");
  document.getElementById('pw').focus();
  return false;
  }
}
</script>

</div>

</div>


</body>
</html>
