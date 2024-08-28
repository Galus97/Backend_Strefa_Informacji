<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Registration new employee</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/register.css">
    <script type="text/javascript">
        function clearError(field) {
            document.getElementById(field + '-error').innerHTML = '';
        }
    </script>
</head>
<body>

<div class="login-container">
    <img src="${pageContext.request.contextPath}/images/logo-no-background.png" alt="Logo" class="logo">

    <form:form action="register" method="post" modelAttribute="employee" class="form">

        <div class="personalData">
            <label class="fields">
                Imię:
                <form:input type="text" path="firstName" class="input-text" oninput="clearError('firstName')" />
                <form:errors path="firstName" cssClass="error-message"/>
            </label><br>

            <label class="fields">
                Nazwisko:
                <form:input type="text" path="lastName" class="input-text" oninput="clearError('lastName')" />
                <form:errors path="lastName" cssClass="error-message"/>
            </label><br>

            <label class="fields">
                Email:
                <form:input type="text" path="email" class="input-text" oninput="clearError('email')" />
                <form:errors path="email" cssClass="error-message"/>
            </label><br>

            <label class="fields">
                Nazwa użytkownika:
                <form:input type="text" path="username" class="input-text" oninput="clearError('username')" />
                <form:errors path="username" cssClass="error-message"/>
            </label><br>

            <label class="fields">
                Hasło:
                <form:input type="password" path="password" class="input-password" oninput="clearError('password')" />
                <form:errors path="password" cssClass="error-message" element="div" id="password-error"/>
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

</div>

</body>
</html>
