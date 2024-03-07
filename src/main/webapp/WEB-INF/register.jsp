<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Registration new employee</title>
</head>
<body>

<form:form action="register" method="post" modelAttribute="employee">

    <div class="personalData">
    <label class="fields">
        First Name:
        <form:input type="text" path="firstName"/>
    </label><br>
    <label>
        Last Name:
        <form:input type="text" path="lastName"/>
    </label><br>
    <label>
        Email:
        <form:input type="text" path="email"/>
    </label><br>
    <label>
        Username:
        <form:input type="text" path="username"/>
    </label><br>
    <label>
        Password:
        <form:input type="password" path="password"/>
    </label>
    </div>
    <br>

    <label>
        <input type="submit" value="Przejdź dalej"/>
    </label>
    <label>
        <input type="reset" value="Usuń wartości">
    </label>

</form:form>
</body>

</html>
