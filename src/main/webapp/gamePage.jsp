<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <meta name="contextPath" content="${contextPath}"/>
    <title>Быки и коровы</title>
</head>
<body>

<form action="${contextPath}/logout" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <button type="submit">Выйти</button>
</form>

История попыток:
<br/>
<textarea id="results" readonly="readonly" style="resize: none; height: 200px; width: 500px"></textarea>
<br/>
Введите четырехзначное число, каждая цифра должна быть уникальной:
<br/>
<input type="text" id="guessedNumber" style="resize: none"></input>
<br/>

<table id="numTable">
    <tr>
        <td>
            <button id="num1">1</button>
        </td>
        <td>
            <button id="num2">2</button>
        </td>
        <td>
            <button id="num3">3</button>
        </td>
    </tr>
    <tr>
        <td>
            <button id="num4">4</button>
        </td>
        <td>
            <button id="num5">5</button>
        </td>
        <td>
            <button id="num6">6</button>
        </td>
    </tr>
    <tr>
        <td>
            <button id="num7">7</button>
        </td>
        <td>
            <button id="num8">8</button>
        </td>
        <td>
            <button id="num9">9</button>
        </td>
    </tr>
    <tr>
        <td>
        </td>
        <td>
            <button id="num0">0</button>
        </td>
        <td>
            <button id="del"><</button>
        </td>
    </tr>

</table>


<button id="confirmGuess">Подтвердить</button>
<br/><br/>
<button id="highscores">Таблица рекордов</button>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script>
    var contextPath = $("meta[name='contextPath']").attr("content");
    var inputField = document.getElementById("guessedNumber");

    function sendGuess() {
        if (inputField.value.length !== 4)
            return;
        var data = {};
        data.guessedNumber = inputField.value.split('');
        data.cows = 0;
        data.bulls = 0;
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        $.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8",
            url: contextPath + "/game",
            data: JSON.stringify(data),
            beforeSend: function (request) {
                request.setRequestHeader(header, token);
            },
            success: function (data) {
                var log = document.getElementById("results");
                log.append("" + data.guessedNumber[0] + data.guessedNumber[1] + data.guessedNumber[2] + data.guessedNumber[3]
                    + " : " + data.bulls + "Б" + data.cows + "К\n");
                if (data.bulls === 4) {
                    log.append("Победа! Среднее количество попыток: " + data.score + "\n\n\n");
                    newGamePrompt();
                }
                log.scrollTop = log.scrollHeight;
            }
        });
        inputField.value = "";
    }

    function newGamePrompt() {
        document.getElementById("results").append("Я загадал четырехзначное число, все цифры в нем различны...\n");
    }

    inputField.onkeypress = function (ev) {
        var isDigit = ev.key.match("[0-9]");
        if ((isDigit && inputField.value.length === 4) || (!isDigit && ev.key !== "Enter" && ev.key !== "Backspace" && ev.key !== "Delete")) {
            ev.preventDefault();
            return;
        }
        if (ev.key === "Enter")
            sendGuess();
    };
    document.getElementById("confirmGuess").onclick = function () {
        sendGuess();
    };
    document.getElementById("highscores").onclick = function () {
        window.open(contextPath + "/highscores", '_blank');
    };
    document.getElementById("numTable").onclick = function (ev) {
        var btn = $(ev.target).closest('button');
        var key = btn.text();
        var inputField = document.getElementById("guessedNumber");
        if (key === "<")
            inputField.value = inputField.value.substring(0, inputField.value.length - 1);
        else if (inputField.value.length < 4)
            inputField.value += key;
    };
    newGamePrompt();
</script>
</body>
</html>
