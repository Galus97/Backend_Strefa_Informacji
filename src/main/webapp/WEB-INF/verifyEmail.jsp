<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<html>
<head>
    <meta charset="UTF-8">
    <title>Panel</title>
</head>
<body>
<h1>
    Podaj swój kod aktywacyjny otrzymany na maila
</h1>
<form:form method="post" action="/verifyEmail">
<label>
    Kod aktywacyjny: <input type="text" name="verifyEmailCode">
    <input type="submit" name="action" value="Wyślij">
</label>
<br>
<label>
    Mail z kodem nie dotarł? <br>
    <button type="submit" name="action" value="ResendCode">Wyslij kod jeszcze raz</button>
</label>
</form:form>


</body>
</html>