<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Your Articles</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/yourArticle.css">
</head>
<body>

<main>
    <c:forEach items="${allArticles}" var="article">
        <a href="/article/${article.articleId}" target="_self">
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