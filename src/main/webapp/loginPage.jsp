<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:url value="/login" var="loginProcessingUrl"/>
<c:url value="/registration" var="registrationUrl"/>
<!DOCTYPE html>
<html>
<head>
    <title>Вход</title>
</head>

<body>
<form action="${loginProcessingUrl}" method="post">
    <fieldset>
        <legend>Пожалуйста, авторизуйтесь</legend>
        <c:if test="${error != null}">
            <div>
                Не удалось войти: ${error}
            </div>
        </c:if>
        <c:if test="${logout != null}">
            <div>
                ${logout}
            </div>
        </c:if>
        <p>
            <label for="username">Логин</label>
            <input type="text" id="username" name="username"/>
        </p>
        <p>
            <label for="password">Пароль</label>
            <input type="password" id="password" name="password"/>
        </p>
        <div>
            <button type="submit" class="btn">Войти</button>
        </div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        <p>
            <a href="${registrationUrl}">Зарегистрироваться</a>
        </p>
    </fieldset>
</form>
</body>
</html>