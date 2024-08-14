<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Registration new employee</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/register.css">
</head>
<body>

<div class="login-container">
    <img src="${pageContext.request.contextPath}/images/logo-no-background.png" alt="Logo" class="logo">

<form:form action="register" method="post" modelAttribute="employee" class="form">

    <div class="personalData">
        <label class="fields">
            First Name:
            <form:input type="text" path="firstName" class="input-text"/>
        </label><br>
        <label class="fields">
            Last Name:
            <form:input type="text" path="lastName" class="input-text"/>
        </label><br>
        <label class="fields">
            Email:
            <form:input type="text" path="email" class="input-text"/>
        </label><br>
        <label class="fields">
            Username:
            <form:input type="text" path="username" class="input-text"/>
        </label><br>
        <label class="fields">
            Password:
            <form:input type="password" path="password" class="input-password"/>
        </label>
    </div>
    <br>

    <div class="form-buttons">
        <label>
            <input type="submit" value="Przejdź dalej" class="button-submit"/>
        </label>
        <label>
            <input type="reset" value="Usuń wartości" class="button-reset"/>
        </label>
    </div>

</form:form>

<%@ include file="/WEB-INF/includes/footer.html" %>

</body>
</html>
