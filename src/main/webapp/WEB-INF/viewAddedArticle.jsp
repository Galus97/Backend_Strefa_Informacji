<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Added Article</title>
</head>
<body>
<h1>
    Article has been added.
</h1>

<h3>General Information about article</h3>
<div>
    <c:out value="${articleInformation.articleId}"/><br>
    <c:out value="${articleInformation.title}"/><br>
    <c:out value="${articleInformation.shortDescription}"/><br>
    <c:out value="${articleInformation.importance}"/><br>
    <c:out value="${articleInformation.imgSrc}"/><br>
    <c:out value="${articleInformation.altImg}"/>
</div>

<h3>Specific Infromation about article</h3>
<div>
    <c:out value="${specificArticle.specificArticleId}"/><br>
    <c:out value="${specificArticle.title}"/><br>
    <c:out value="${specificArticle.description}"/><br>
</div>

<h3>Images in article</h3>
<div>
    <c:forEach items="${articleImages}" var="image">
        <c:out value="${image.articleImagesId}"/><br>
        <c:out value="${image.imgSrc}"/><br>
        <c:out value="${image.altImg}"/><br>
    </c:forEach>
</div>

<label>
    <a href="/panel">Powróć do panelu głownego</a>
</label>

</body>
</html>