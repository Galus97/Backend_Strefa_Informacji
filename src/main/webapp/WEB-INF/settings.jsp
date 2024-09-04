<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Zmień hasło</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/settings.css">
</head>
<body>

<h1>Zmień hasło</h1>

<form action="/settings" method="post">
    <label for="lastPassword">Stare hasło:</label>
    <input type="password" id="lastPassword" name="lastPassword" required>

    <label for="newPassword">Nowe hasło:</label>
    <input type="password" id="newPassword" name="newPassword" required>

    <label for="newPasswordAgain">Powtórz nowe hasło:</label>
    <input type="password" id="newPasswordAgain" name="newPasswordAgain" required>

    <button type="submit">Zmień hasło</button>
</form>

<footer>
    <a href="/panel" class="button">Powrót do panelu</a>
    <a href="/logout" class="button">Logout</a>
</footer>

</body>
</html>