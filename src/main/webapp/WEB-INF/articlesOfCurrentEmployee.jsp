<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Your Articles</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/yourArticle.css">
</head>
<body>
<header>
    <h1>
        <c:out value="${employee.firstName}"/>, to dodane przez Ciebie artykuły
    </h1>
</header>

<main>
    <c:forEach items="${allArticlesByEmployee}" var="article">
        <a href="/article/${article.articleId}" target="_blank">
        <article class="article-item">
            <h3><c:out value="${article.title}"/></h3>
            <p><strong>ID:</strong> <c:out value="${article.articleId}"/></p>
            <p><strong>Opis:</strong> <c:out value="${article.shortDescription}"/></p>
            <p><strong>Ważność:</strong> <c:out value="${article.importance}"/></p>
        </article>
        </a>
    </c:forEach>
</main>

<footer>
    <a href="/panel" class="button">Powrót do panelu</a>
    <a href="/logout" class="button">Logout</a>
</footer>
</body>
</html>