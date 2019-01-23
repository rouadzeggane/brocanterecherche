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
			<form class="searchBox"  method="post" action="search">
						<input class="searchBox" placeholder="Annonces" name="searchInput" id="searchPage" onblur="if (this.value == '') {this.value = 'Go';}" onfocus="if (this.value == 'Go') {this.value = '';}" type="text" value="" />

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
		<li class="left">
		

<div class="latestBox">
	<ul class="cat">
		<li class="ftitle">
			 <img src="./Annonces.png" style="width:264px;height:48px;"/>
		</li>
	</ul>
</div>
	
	<div class="latestBox">
	      <c:forEach items="${requestScope.annonces}" var="annonce" >
			<ul class="art"  itemscope itemtype="http://schema.org/Product">
				
			<li class="title" itemprop="name">
				<a href="/brocanterecherche/details?id=${annonce.getIdAnnonce()}" title="PETITES ANNONCES - 4FreeAd.com - Free Advertising">${annonce.getTitre()}</a>			</li>
			<li class="img">
					<img itemprop="image"  src="/brocanterecherche/image?id=${annonce.getIdAnnonce()}"  alt="PETITES ANNONCES" />
			</li>
			

			
			<li class="price" itemprop="prix">
				<p> Prix: ${annonce.getPrix()} </p>
			</li>
			
		</ul>
		
		</c:forEach>
	
	</div>
	
	</ul>
</div>
	

	
<div class="footer">
<p>Copyright 2018 / 2019  © Tous droits réservés</p>
	
</div>


</div>

</body>
</html>
