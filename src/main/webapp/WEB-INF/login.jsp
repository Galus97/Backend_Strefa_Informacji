<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Login form</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/login.css">
</head>
<body class="body">

<div class="login-container">
    <img src="${pageContext.request.contextPath}/images/logo-no-background.png" alt="Logo" class="logo">

<form:form method="post" action="/login" class="form">

    <label class="label">
        Podaj nazwę użytkownika:
        <input name="username" class="input-text" />
    </label>
    <br>
    <label class="label">
        Podaj hasło:
        <input type="password" name="password" class="input-password"/>
    </label>
    <br>
    <label class="label">
        <input type="submit" value="Zaloguj się" class="button-submit">
    </label>
    <br>
    <label class="label">
        <input type="reset" value="Usuń wartości" class="button-reset">
    </label>
    <br>
    <br>
    <label class="label">
        <h3 class="header">Nie masz jeszcze konta</h3>
        <a href="/register" class="register-link">Załóż nowe konto</a>
    </label>

</form:form>

<%@ include file="/WEB-INF/includes/footer.html" %>

</body>
</html>