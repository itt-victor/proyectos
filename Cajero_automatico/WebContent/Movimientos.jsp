<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Movimientos</title>
<style>
    table, th, td {
    border: 1px solid;
    }
</style>
</head>
<body>
    <h1>Cuenta: ${sessionScope.cuenta.numeroCuenta}</h1>
    <h1>Saldo: ${sessionScope.cuenta.saldo}</h1>
    
    <table>
        <tr>
            <th>Cantidad</th>
            <th>Fecha</th>
            <th>Tipo</th>
        </tr>

        <c:forEach var="movimientos" items="${sessionScope.movimientos}">
            <!-- Se da formato a la fecha -->
            <fmt:formatDate value="${movimientos.fecha}" var="formato" type = "both" 
            dateStyle = "medium" timeStyle = "medium"/>
            <tr> 
                <td>${movimientos.cantidad}</td>
                <td>${formato}</td>
                <td>${movimientos.operacion}</td>
            </tr>
        </c:forEach>
    </table>
    <a href="Sesion.jsp">Volver</a>
</body>
</html>