<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>                  
<head>
		<title>Annonce</title>
	
	<link rel="stylesheet" type="text/css" href="style.css" />

</head>

<body>
<div align="center">
	<!--header-->
	<%@ include file="header.jspf"%>

<div class="searchBox">
	<ul>
		<li>
			<form class="searchBox" enctype="multipart/form-data" method="post" action="/Search/">
										<input class="searchBox" placeholder="Annonce" name="searchKey" id="searchKey" onblur="if (this.value == '') {this.value = 'Go';}" onfocus="if (this.value == 'Go') {this.value = '';}" type="text" value="" />
					
				    	<%@ include file="categories.jspf"%>
						<input type="hidden" name="searchPage" value="1">
						<button class="searchBox" type="submit">Search</button>


			</form>
		</li>
	</ul>
</div>
	
	<!--content-->
		<div class="gadsl"></div>

<div class="content">
		<ul>
		<li class="leftDetails">
		

	<div class="article">
	
	<c:set  var="annonce" value="${requestScope.annonce}"/>
	  <c:set  var="annonceOwner" value="${requestScope.annonceOwner}"/>
			<ul class="art"  itemscope itemtype="http://schema.org/Product">
				
			<li class="title" itemprop="name">
				<h1>${annonce.getTitre()}		</h1>	</li>
			<li class="image">
				
					<img itemprop="image"  src="/brocanterecherche/image?id=${annonce.getIdAnnonce()}"  alt="PETITES ANNONCES" />
			</li>
			
			<li class="txt" itemprop="description">
				<p>Description: ${annonce.getDescription()}</p>
			</li>
			
			<li class="price">
				prix: ${annonce.getPrix()} <br> <br>
			</li>
			
			 <c:if test="${(not empty  sessionScope.currentSessionUser) and (sessionScope.currentSessionUser.tel eq annonceOwner.tel) and (sessionScope.currentSessionUser.lastName eq annonceOwner.lastName)}">
					<li class="zone">
				<!-- <a  title="supprimer" onclick="document.getElementById('deleteAnnonce').submit();" >supprimer</a> -->
				 	<button type="button" class="searchBox" onclick="document.getElementById('deleteAnnonce').submit();">Supprimer</button><br> 
					</li>
				</c:if>
			
			<br />
			
			
			
			
		</ul>
		
	
	</div>
	</li>
	    <li class="right">
	    	<ul>
	    	   
	    	     <li> <h2>Information du client </h2></li>
	    	     <li>Prénom: ${annonceOwner.firstName} </li>
	    	     <li>téléphone: ${annonceOwner.tel}</li>
	    	     
	    	</ul>
	    	

	    	
	    </li>
	
	</ul>
</div>	
	
<div class="footer">
	<p>Copyright 2018 / 2019 © Tous droits réservés</p>
	
</div>


</div>


<form action="deleteAnnonce"  method="post"  id ="deleteAnnonce" name="deleteAnnonce">
<input  name="annonceId" id="nom" size="60" maxlength="30" type="hidden" value="${annonce.idAnnonce}" />
</form>


</body>
</html>
