<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Success added Article</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/addedArticle.css">
</head>
<body>

<header>
    <h1>Artykuł o tytule: ${articleInformation.title}</h1>
</header>

<main>
    <section class="article-info">
        <h3>Informacje o artykule</h3>
        <p><strong>ID artykułu:</strong> <c:out value="${articleInformation.articleId}"/></p>
        <p><strong>Tytuł:</strong> <c:out value="${articleInformation.title}"/></p>
        <p><strong>Opis:</strong> <c:out value="${articleInformation.shortDescription}"/></p>
        <p><strong>Ważność:</strong> <c:out value="${articleInformation.importance}"/></p>
        <p><strong>Link do zdjęcia:</strong> <c:out value="${articleInformation.imgSrc}"/></p>
        <p><strong>Opis zdjęcia:</strong> <c:out value="${articleInformation.altImg}"/></p>
    </section>

    <section class="specific-article-info">
        <h3>Informacje szczegółowe o artykule</h3>
        <p><strong>ID artykułu:</strong> <c:out value="${specificArticle.specificArticleId}"/></p>
        <p><strong>Tytuł:</strong> <c:out value="${specificArticle.title}"/></p>
        <p><strong>Opis:</strong> <c:out value="${specificArticle.description}"/></p>
    </section>

    <section class="article-images">
        <h3>Zdjęcia w artykule</h3>
        <c:forEach items="${articleImages}" var="image">
            <p><strong>ID zdjęcia:</strong> <c:out value="${image.articleImagesId}"/></p>
            <p><strong>Link do zdjęcia:</strong> <c:out value="${image.imgSrc}"/></p>
            <p><strong>Opis zdjęcia:</strong> <c:out value="${image.altImg}"/></p>
        </c:forEach>
    </section>

    <nav class="navigation">
        <a href="/yourarticles" class="button">Powróć do dodanych artykułów</a>
    </nav>
</main>

</body>
</html>