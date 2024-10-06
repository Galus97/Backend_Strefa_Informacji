<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Zmiana Email</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/changeSettings.css">
</head>
<body>

<h1 class="page-title">Zmień adres E-mail</h1>

<form:form action="/changeEmail" method="post" class="general-form">

    <label for="newEmail" class="form-label">Nowe adres E-mail:</label>
    <input type="email" id="newEmail" name="newEmail" class="form-input" required>

    <label for="newEmailAgain" class="form-label">Powtórz nowy adres E-mail:</label>
    <input type="email" id="newEmailAgain" name="newEmailAgain" class="form-input" required>

    <div class="form-actions">
        <input type="submit" value="Zmień hasło" class="button submit-button">
        <input type="reset" value="Usuń wartości" class="button reset-button">
    </div>
</form:form>

<footer class="footer">
    <a href="/panel" class="button">Powrót do panelu</a>
    <a href="/logout" class="button">Logout</a>
</footer>

</body>
</html>