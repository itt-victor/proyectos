package controlador;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.List;
import modelo.dao.MovimientoDaoImpl;
import modelo.dao.CuentaDaoImpl;
import modelo.beans.Cuenta;
import modelo.beans.Movimiento;

/**
 * Servlet implementation class CajeroVirtual
 */
@WebServlet("/CajeroVirtual")
public class CajeroVirtual extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CajeroVirtual() {
		super();
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String opcion = request.getParameter("opcion");
		switch(opcion) {

		case "login":
			login(request, response);
			break;

		case "ingresar":
			ingresar(request, response);
			break;

		case "extraer":
			extraer(request, response);
			break;	

		case "verMovimientos":
			verMovimientos(request, response);
			break;

		case "transferencia":
			transferencia(request, response);
			break;

		default:
			System.out.println("opcion erronea : " + opcion);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Variables
		HttpSession misesion = request.getSession();
		CuentaDaoImpl cuentaDao = new CuentaDaoImpl();
		String error = "El número de cuenta no existe";
		Cuenta cuenta = cuentaDao.findById(Integer.parseInt(request.getParameter("cuenta")));
		//Si la cuenta no existe
		if (cuenta == null) {
			request.setAttribute("error", error);
			request.getRequestDispatcher("Index.jsp").forward(request, response);
		}
		//Si sí
		else {
		    misesion.setAttribute("cuenta", cuenta);
		    request.getRequestDispatcher("Sesion.jsp").forward(request, response);
		}
		
	}
	
	protected void ingresar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Variables
		HttpSession misesion = request.getSession();
		MovimientoDaoImpl movimientoDao = new MovimientoDaoImpl();
		CuentaDaoImpl cuentaDao = new CuentaDaoImpl();
		Cuenta cuenta = (Cuenta)misesion.getAttribute("cuenta");
		BigDecimal cantidad = BigDecimal.valueOf(Integer.valueOf(request.getParameter("cantidad")));
		
		//Se actualiza el saldo en el objeto, el atributo de sesión, y la bd
		BigDecimal saldo = cuenta.getSaldo().add(cantidad);
		cuenta.setSaldo(saldo);
		misesion.setAttribute("cuenta", cuenta);
		cuentaDao.updateSaldo(cuenta);
		//Se genera el objeto de movimiento y se refleja en la bd
		Movimiento ingreso = new Movimiento(cantidad, "ingreso", cuenta);
		movimientoDao.operacion(ingreso);
		
		request.getRequestDispatcher("Sesion.jsp").forward(request, response);
	}
	
	protected void extraer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Variables
		HttpSession misesion = request.getSession();
		MovimientoDaoImpl movimientoDao = new MovimientoDaoImpl();
		CuentaDaoImpl cuentaDao = new CuentaDaoImpl();
		Cuenta cuenta = (Cuenta)misesion.getAttribute("cuenta");
		BigDecimal cantidad = BigDecimal.valueOf(Integer.valueOf(request.getParameter("cantidad")));
		
		//Se actualiza el saldo en el objeto, el atributo de sesión, y la bd
		BigDecimal saldo = cuenta.getSaldo().subtract(cantidad);
		cuenta.setSaldo(saldo);
		misesion.setAttribute("cuenta", cuenta);
		cuentaDao.updateSaldo(cuenta);
		//Se genera el objeto de movimiento y se refleja en la bd
		Movimiento extraccion = new Movimiento(cantidad, "extracción", cuenta);
		movimientoDao.operacion(extraccion);
		
		request.getRequestDispatcher("Sesion.jsp").forward(request, response);
	}
	
	protected void verMovimientos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession misesion = request.getSession();
		MovimientoDaoImpl movimientoDao = new MovimientoDaoImpl();
		Cuenta cuenta = (Cuenta)misesion.getAttribute("cuenta");
		List<Movimiento> movimientos = movimientoDao.movimientosPorCuenta(cuenta.getNumeroCuenta());
		misesion.setAttribute("movimientos", movimientos);
		request.getRequestDispatcher("Movimientos.jsp").forward(request, response);
	}
	
	protected void transferencia(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession misesion = request.getSession();
		CuentaDaoImpl cuentaDao = new CuentaDaoImpl();
		MovimientoDaoImpl movimientoDao = new MovimientoDaoImpl();
		Cuenta cuentaOrigen = (Cuenta)misesion.getAttribute("cuenta");
		BigDecimal cantidad = BigDecimal.valueOf(Integer.valueOf(request.getParameter("cantidad")));
		Cuenta cuentaDestino = cuentaDao.findById(Integer.parseInt(request.getParameter("destino")));
		
		//Movimiento de sustracción de la cuenta origen
		BigDecimal saldoOrigen = cuentaOrigen.getSaldo().subtract(cantidad);
		cuentaOrigen.setSaldo(saldoOrigen);
		misesion.setAttribute("cuenta", cuentaOrigen);
		cuentaDao.updateSaldo(cuentaOrigen);

		Movimiento extraccion = new Movimiento(cantidad, "extracción", cuentaOrigen);
		movimientoDao.operacion(extraccion);
		
		//Movimiento de adición a la cuenta destino
		BigDecimal saldoDestino = cuentaDestino.getSaldo().add(cantidad);
		cuentaDestino.setSaldo(saldoDestino);
		cuentaDao.updateSaldo(cuentaDestino);
		
		Movimiento ingreso = new Movimiento(cantidad, "ingreso", cuentaDestino);
		movimientoDao.operacion(ingreso);
		
		request.getRequestDispatcher("Sesion.jsp").forward(request, response);
	}

}
