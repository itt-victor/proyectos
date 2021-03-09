<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Inicio</title>
</head>
<body>
    <form action="CajeroVirtual?opcion=login" method="post">
        <div>
            <label for="cuenta">Introduce cuenta: </label>
            <input type="text" name="cuenta" id="cuenta" class="cuenta" />
        </div>
        <button type="submit">Entrar</button>
    </form>
    <h3>${requestScope.error}</h3>
</body>
</html>