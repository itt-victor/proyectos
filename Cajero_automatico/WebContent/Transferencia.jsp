<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Transferencia</title>
</head>
<body>
<form action="CajeroVirtual?opcion=transferencia" method="post">
        <div>
            <label for="cantidad">Introduce cantidad: </label>
            <input type="text" name="cantidad" id="cantidad" class="cantidad" />
        </div>
        <div>
            <label for="destino">Introduce destino: </label>
            <input type="text" name="destino" id="destino" class="destino" />
        </div>
        <button type="submit">Aceptar</button>
    </form>
</body>
</html>