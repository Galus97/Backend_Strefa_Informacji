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
    <input type="submit" value="Wyslij">
</label>
</form:form>

</body>
</html>