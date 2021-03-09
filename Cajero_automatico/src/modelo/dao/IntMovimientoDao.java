package modelo.dao;

import java.util.List;

import modelo.beans.Movimiento;

public interface IntMovimientoDao {
	int operacion(Movimiento movimiento);
	Movimiento findById(int idMovimiento);
	List<Movimiento> movimientosPorCuenta(int id_cuenta);
}
