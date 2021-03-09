<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Ingresar o extraer</title>
</head>
<body>
    <h1>${param.operacion}</h1>
    <!-- Mediante el parámetro insertado se escoge cuál será la opción que el servlet llamará -->
    <c:choose>
      <c:when test="${param.operacion=='ingresar'}">
        <c:set var="eleccion" value="CajeroVirtual?opcion=ingresar" />
      </c:when>   
      <c:otherwise>
        <c:set var="eleccion" value="CajeroVirtual?opcion=extraer" />
      </c:otherwise>
    </c:choose>

     <form action="${eleccion}" method="post">
        <div>
            <label for="cantidad">Introduce cantidad: </label>
            <input type="text" name="cantidad" id="cantidad" class="cantidad" />
        </div>
        <button type="submit">Aceptar</button>
    </form>
</body>
</html>