package servicios;

import java.math.BigDecimal;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import modelo.beans.*;
import modelo.dao.CuentaDaoImpl;
import modelo.dao.MovimientoDaoImpl;

@Path("/cuentas")
public class GestionCajeroService {
	
	private CuentaDaoImpl cuentaDao;
	private MovimientoDaoImpl movimientoDao;

	public GestionCajeroService() {
		cuentaDao = new CuentaDaoImpl();
		movimientoDao = new MovimientoDaoImpl();
	}
	
	@PUT
	@Path("/actualiza_saldo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateSaldo(Cuenta cuenta) {
		int filas = cuentaDao.updateSaldo(cuenta);
		if (filas == 1)
			return "saldo actualizado correctamente en la cuenta";
		else
			return "Se ha producido un error";
		/*JSON PARA PRUEBA EN POSTMAN
		{
            "numeroCuenta":1001,
            "saldo":1200,
            "tipoCuenta":"ahorro"
        } */
	}
	
	@GET
	@Path("/muestra_cuenta/{numcuenta}")
	@Produces(MediaType.APPLICATION_JSON)
	public Cuenta muestraCuenta(@PathParam("numcuenta") int numeroCuenta) {
		Cuenta cuenta = cuentaDao.findById(numeroCuenta);
		return cuenta;
	}
	
	@GET
	@Path("/todas_las_cuentas")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Cuenta> muestraTodas() {
		return cuentaDao.findAll();
	}
	
	@POST
	@Path("/ingreso/{cuenta}/{cantidad}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String ingreso(@PathParam("cuenta") int numeroCuenta, @PathParam("cantidad") BigDecimal cantidad) {
		Cuenta cuenta = cuentaDao.findById(numeroCuenta);
		BigDecimal saldo = cuenta.getSaldo().add(cantidad);
		cuenta.setSaldo(saldo);
		cuentaDao.updateSaldo(cuenta);
		Movimiento movimiento = new Movimiento(cantidad, "ingreso", cuenta);
		int filas = movimientoDao.operacion(movimiento);
		if (filas == 1)
			return "Ingreso realizado correctamente";
		else
			return "Se ha producido un error";
	}
	
	@POST
	@Path("/extraccion/{cuenta}/{cantidad}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String extraccion(@PathParam("cuenta") int numeroCuenta, @PathParam("cantidad") BigDecimal cantidad) {
		Cuenta cuenta = cuentaDao.findById(numeroCuenta);
		BigDecimal saldo = cuenta.getSaldo().subtract(cantidad);
		cuenta.setSaldo(saldo);
		cuentaDao.updateSaldo(cuenta);
		Movimiento movimiento = new Movimiento(cantidad, "ingreso", cuenta);
		int filas = movimientoDao.operacion(movimiento);
		if (filas == 1)
			return "Extracción realizada correctamente";
		else
			return "Se ha producido un error";
	}
	
	@GET
	@Path("/muestra_movimiento/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Movimiento muestraMovimiento(@PathParam("id") int idMovimiento) {
		Movimiento movimiento = movimientoDao.findById(idMovimiento);
		return movimiento;
	}

	@GET
	@Path("/historico/{cuenta}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Movimiento> movimientosPorCuenta(@PathParam("cuenta") int numeroCuenta){
		return movimientoDao.movimientosPorCuenta(numeroCuenta);
	}
}

