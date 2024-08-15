<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Panel</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/verifyEmail.css">
</head>
<body>
<form:form method="post" action="/verifyEmail" class="login-container">
    <img src="${pageContext.request.contextPath}/images/logo-no-background.png" alt="Logo" class="logo">
    <h1 class="title">Podaj swój kod aktywacyjny otrzymany na maila</h1>

    <label for="verifyEmailCode" class="form-label">Kod aktywacyjny:</label>
    <input type="text" name="verifyEmailCode" id="verifyEmailCode" class="form-input">

    <input type="submit" name="action" value="Wyślij" class="button-submit">

    <label class="form-label mail-resend">
        Mail z kodem nie dotarł?
        <button type="submit" name="action" value="ResendCode" class="button-resend">Wyślij kod jeszcze raz</button>
    </label>
</form:form>
</body>
</html>