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
            <a href="/add/articleInformation">Dodaj nowy artykuł</a>
        </label>
        <label class="label">
            <a href="https://be.contentful.com/login/" target="_blank">Dodaj artykuł przez Contentful</a>
        </label>
        <label class="label">
            <a href="/yourarticles">Twoje artykuły</a>
        </label>
        <label class="label">
            <a href="/articles">Wszystkie artykuły</a>
        </label>
        <label class="label">
            <a href="/statistics">Statystyki</a>
        </label>
        <label class="label">
            <a href="/settings">Ustawienia</a>
        </label>

    </div>


    <div class="content">

        <div class="header">
            <span class="welcome">Witaj, <c:out value="${employee.firstName}"/></span>
            <a href="/logout" class="logout">Logout</a>
        </div>

        <div class="table-container">
            <label class="label">Ostatnio dodane przez Ciebie artykuły:</label>
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Tytuł</th>
                    <th>Krótki opis</th>
                    <th>Ważność</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="article" items="${lastArticles}">
                    <tr>
                        <td><c:out value="${article.articleId}"/></td>
                        <td><c:out value="${article.title}"/></td>
                        <td><c:out value="${article.shortDescription}"/></td>
                        <td><c:out value="${article.importance}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>


<div class="footer">
    <span>Aktualna pogoda w Warszawie &nbsp;
        <b>Temperatura:</b> <c:out value="${weather.temperatue}"/> <sup>o</sup>C &nbsp;
        <b>Ciśnienie:</b> <c:out value="${weather.pressure}"/> hPa &nbsp;
        <b>Wilgotność:</b> <c:out value="${weather.humidity}"/> % &nbsp;
        <b>Prędkość wiatru:</b> <c:out value="${weather.speed}"/> <sup>m</sup>/<sub>s</sub>
    </span>
</div>

</body>
</html>