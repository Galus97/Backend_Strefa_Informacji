<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Your Articles</title>
</head>
<body>
<h1>
    <c:out value="${employee.firstName}"/>, to dodane przez Ciebie artykułu
</h1>

<c:forEach items="${allArticlesByEmployee}" var="article">
<br>
    <h3><c:out value="${article.title}"/></h3>
    <br>
    <c:out value="${article.articleId}"/>
    <br>
    <c:out value="${article.shortDescription}"/>
    <br>
    <c:out value="${article.importance}"/>
    <br>
    <c:out value="${article.imgSrc}"/>
    <br>
    <c:out value="${article.altImg}"/>
    <br>

</c:forEach>
<label>
    <a href="/panel">Powrót do panelu</a>
</label>
<br>
<label>
    <a href="/logout">Logout</a>
</label>

</body>
</html>