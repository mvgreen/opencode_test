<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Регистрация</title>
</head>
<body>
<form:form method="post" modelAttribute="userForm">
    <fieldset>

        <legend>Придумайте логин и пароль</legend>
        <spring:bind path="username">
            <p>
                <label for="username">Логин</label>
                <form:input type="text" path="username"/>
                <form:errors path="username"/>
            </p>
        </spring:bind>
        <spring:bind path="password">
            <p>
                <label for="password">Пароль</label>
                <form:input type="password" id="password" path="password"/>
                <form:errors path="password"/>
            </p>
        </spring:bind>
        <spring:bind path="confirmPassword">
            <p>
                <label for="confirmPassword">Повторите пароль</label>
                <form:input type="password" id="confirmPassword" path="confirmPassword"/>
                <form:errors path="confirmPassword"/>
            </p>
        </spring:bind>
        <div>
            <button type="submit" class="btn">Зарегистрироваться</button>
        </div>
    </fieldset>
</form:form>
</body>
</html>
