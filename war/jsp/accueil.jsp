<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<script src="/_ah/channel/jsapi"></script>
		<script src="javascript/jquery-1.7.1.js"	type="text/javascript"></script>
		<script src="javascript/mowItNow.js"		type="text/javascript"></script>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>MowItNow</title>
	</head>
	<body>
		<div class="token">{{ token }}</div>
		
		<textarea id="zoneInstruction" rows="20" cols="20"></textarea>
  		<button name="envoyer" >Envoyer</button>
  		
  		<div class="grilleContainer" style="border: solid 1px red;position: relative;" />
	</body>
</html>