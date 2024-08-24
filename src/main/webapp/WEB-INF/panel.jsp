<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Panel</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/panel.css">
</head>

<body>
<div class="container">
    <div class="menu">
        <label class="label">
            <a href="/add/articleInformation" class="button-addArticle">Dodaj nowy artykuł</a>
        </label>
        <label class="label">
            <a href="https://be.contentful.com/login/" class="button-addArticle">Dodaj nowy artykuł przez Contentful</a>
        </label>
        <label class="label">
            <a href="/yourarticles" class="button-yourarticles">Wszystkie artykuły dodane przez Ciebie</a>
        </label>
        <label class="label">
            <a href="/articles" class="button-allarticles">Wszystkie artykuły na portalu</a>
        </label>
    </div>

    <label class="page-title">
        Witaj <c:out value="${employee.firstName}"/>
    </label>

    <label class="label">
        <a href="/logout" class="button-logout">Logout</a>
    </label>
</div>

<label class="weather-info">
    Aktualna pogoda w Warszawie &nbsp;
    <b>Temperatura:</b> <c:out value="${weather.temperatue}"/> <sup>o</sup>C
    <b>Ciśnienie:</b> <c:out value="${weather.pressure}"/> hPa
    <b>Wilgotność:</b> <c:out value="${weather.humidity}"/> %
    <b>Prędkość wiatru:</b> <c:out value="${weather.speed}"/> <sup>m</sup>/<sub>s</sub>
</label>

</body>
</html>