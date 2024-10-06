<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Zmiana hasła</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/changeSettings.css">
</head>
<body>

<div class="container">
    <h1 class="page-title">Zmień hasło</h1>

    <c:if test="${not empty wrongPassword}">
        <p class="error-message">${wrongPassword}</p>
    </c:if>

    <c:if test="${not empty passwordsDoNotMatch}">
        <p class="error-message">${passwordsDoNotMatch}</p>
    </c:if>

    <form:form action="/changePassword" method="post" class="general-form">
        <label for="lastPassword" class="form-label">Stare hasło:</label>
        <input type="password" id="lastPassword" name="lastPassword" class="form-input" required>

        <label for="newPassword" class="form-label">Nowe hasło:</label>
        <input type="password" id="newPassword" name="newPassword" class="form-input" required>

        <label for="newPasswordAgain" class="form-label">Powtórz nowe hasło:</label>
        <input type="password" id="newPasswordAgain" name="newPasswordAgain" class="form-input" required>

        <div class="form-actions">
            <input type="submit" value="Zmień hasło" class="button submit-button">
            <input type="reset" value="Usuń wartości" class="button reset-button">
        </div>
    </form:form>

    <footer class="footer">
        <a href="/panel" class="button">Powrót do panelu</a>
        <a href="/logout" class="button">Logout</a>
    </footer>
</div>

</body>
</html>