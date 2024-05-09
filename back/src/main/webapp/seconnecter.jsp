<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, pack.*" %><!DOCTYPE html>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<form method="post" action="Serv">
<% 
String message = (String)request.getAttribute("msg");
    out.println(message);
%>
<br><br>
Nom : <input type="text" name="nom"><br>
Mot de passe : <input type="text" name="mdp"><br>
<input type="submit" value="OK">
<input type="hidden" name="op" value="seconnecter">
</form>

</body>
</html>