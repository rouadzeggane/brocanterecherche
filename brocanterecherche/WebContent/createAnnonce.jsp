<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>                  
<head>
		<title>Create Annonce</title>
	
	<link rel="stylesheet" type="text/css" href="style.css" />

</head>

<body>

<div align="center">
	<!--header-->
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
			<h1><img src="./wallet_1.png" style="width:58px;height:58px;"/> Déposer une annonce</h1>
		</li>
	</ul>
</div>

<div class="addPage">
	<ul>
		<li>
			
					
			<form enctype="multipart/form-data" name="myform" id="myform" method="post" action="/brocanterecherche/create">
						<c:choose>
				<c:when test="${not empty requestScope.erreurs['compte'] }">
				  <p class="erreur">${requestScope.erreurs['compte']}</p>
				</c:when>
				
			</c:choose>
			
			
			
			<div style="float:left"><b>Catégorie</b>*:<span id="subcatid_e" class="valid"></span>&nbsp;</div>
			<select class='requierd' name="categorie_id" id="subcatid">
					<c:forEach items="${requestScope.categories}" var="cat">
	
						<option style="background-color:#dcdcc3;" value="${cat.getIdCat()}">${cat.getNomCategory()}</option>
					
					</c:forEach>
			</select>
			<br />
			
			
			<b>TITRE</b>*:<span id="adtitle_e" class="valid"></span><br />
			<input class='requierd' name="titre" id="adtitle" size="60" maxlength="100" type="text" value="" />
			<span class="erreur">${requestScope.erreurs['titre']}</span>
			<br/>
			
			<b>DESCRIPTION</b>*:<span id="addesc_e" class="valid"></span><br />
			<textarea class='requierd' onKeyDown="limitText(this.form.addesc,this.form.countdown,1000);" 
				onKeyUp="limitText(this.form.addesc,this.form.countdown,1000);" name="description" id="addesc" class="formbox" cols="70" rows="6" maxlength="1000" /></textarea><br />
			<input class="add" readonly type="text" name="countdown" id="countdown" size="3" value="1000">  </font>
			<span class="erreur">${requestScope.erreurs['description']}</span>
			<br/>
			
			<b>Prix</b>:<br/> <input class="add" style="display:inline" name="prix" size="10" maxlength="15" type="text" value="0" /> <b>Euro</b>
			<span class="erreur">${requestScope.erreurs['prix']}</span>
							
			<br/><br/>
						
			<b>PHOTO</b>:<br/>
			
			<input class="add" type="file" name="photo" id="pic1" size="40"><br>
			

			<br/>
			
			<input name="do" id="do" type="hidden" value="post">
			<button class="searchBox" id="test" type="submit">Ajouter</button>
			</form>
			
			<br/>		</li>
	</ul>
</div>
		</li>
		
	</ul>
</div>	
	<!--footer-->
<div class="footer">
<p>Copyright 2018 / 2019 © Tous droits réservés</p>

<script>
$(function(){
	$("#test").click(function(event) {
	  //event.preventDefault();
	   check_input(event);
	});
	});



function removeA(arr){
var what, a= arguments, L= a.length, ax;
while(L> 1 && arr.length){
	what= a[--L];
	while((ax= arr.indexOf(what))!= -1){
		arr.splice(ax, 1);
	}
}
return arr;
}
input_error=[];
var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
function check_input(event){
   $('.requierd').each(function(){
	  input='#'+this.id;
	  input_attr=$(input).attr('type');
	  value=$(input).val();
	  switch (input_attr) {
		 case 'text' : if (value==''){
						add_error(this.id,event);
					 }
					 else {remove_error(this.id,event);}
					 break;
		 case 'checkbox' : if (!$(input).is(':checked')) {add_error(this.id,event)} else {remove_error(this.id,event);} break;
		 default : if (value=='#' || value==''){add_error(this.id,event)} else {remove_error(this.id,event);} break;
	  }
   });
}

function add_error (id,event) {
   event.preventDefault();
   if ((jQuery.inArray(id, input_error))) {
   input_error.push(id);
   }
   value=$(id).val();
   input='#'+id;
   span_error='#'+id+'_e';
   $(span_error).html('Required!');
   $(span_error).removeClass('valid');
   $(span_error).addClass('error');
   $(input).addClass('error_input');
   $(input).effect("shake", { times:3 }, 50);
   scrollToElement("#"+input_error[0],50);
}
function remove_error (id,event) {
   if (id=='email') {
	  if (!emailPattern.test(value)) {
		 add_error(id,event);
	  }
	  else {
		 removeA(input_error,id);
		 input='#'+id;
		 span_error='#'+id+'_e';
		 $(span_error).removeClass('error');
		 $(span_error).addClass('valid');
		 $(input).removeClass('error_input');
	  }
   }
   else {
   removeA(input_error,id);
   input='#'+id;
   span_error='#'+id+'_e';
   $(span_error).removeClass('error');
   $(span_error).addClass('valid');
   $(input).removeClass('error_input');
   }
}

function scrollToElement(selector, time, verticalOffset) {
   time = typeof(time) != 'undefined' ? time : 1000;
   verticalOffset = typeof(verticalOffset) != 'undefined' ? verticalOffset : 0;
   element = $(selector);
   offset = element.offset();
   offsetTop = offset.top + verticalOffset - 60;
   $('html, body').animate({
       scrollTop: offsetTop
   }, time);
   $(selector).focus();
}



function limitText(limitField, limitCount, limitNum) {
	if (limitField.value.length > limitNum) {
		limitField.value = limitField.value.substring(0, limitNum);
	} else {
		limitCount.value = limitNum - limitField.value.length;
	}
}
</script>

</div>

</div>


</body>
</html>
