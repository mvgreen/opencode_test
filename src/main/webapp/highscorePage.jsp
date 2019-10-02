<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Таблица рекордов</title>
</head>
<body>
<ul>
    <c:forEach items="${userList}"  var="item">
        <li>${item.username}: ${item.score}</li>
    </c:forEach>
</ul>
</body>
</html>
