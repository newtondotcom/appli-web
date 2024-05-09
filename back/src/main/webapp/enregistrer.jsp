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
Prénom : <input type="text" name="prenom"><br>
INE : <input type="text" name="ine"><br>
Mot de passe administrateur : <input type="text" name="mdp_admin"><br>
Email : <input type="text" name="email"><br>
Téléphone : <input type="text" name="telephone"><br>
Nom de l'établissement : <input type="text" name="nom_etablissement"><br>
<input type="submit" value="OK">
<input type="hidden" name="op" value="enregistrer">
</form>

</body>
</html>