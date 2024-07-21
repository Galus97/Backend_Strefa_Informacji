<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Panel</title>
</head>
<body>
<h1>
    Witaj <c:out value="${employee.firstName}"/>
</h1>

<label>
    <a href="/add/articleInformation">Dodaj nowy artykuł</a>
</label>
<br>
<label>
    <a href="/yourarticles">Artykuły dodane przez Ciebie</a>
</label>
<br>
<label>
    <a href="/logout">Logout</a>
</label>
<br>

<label id="weather">
    Aktualna pogoda w Warszawie <br>
    <b>Temperatura:</b> <c:out value="${weather.temperatue}"/> <sup>o</sup>C
    <b>Ciśnienie;</b> <c:out value="${weather.pressure}"/> hPa
    <b>Wilgotność:</b> <c:out value="${weather.humidity}"/> %
    <b>Prędkość wiatru:</b> <c:out value="${weather.speed}"/> <sup>m</sup>/<sub>s</sub>
</label>

</body>
</html>