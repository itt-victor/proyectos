package modelo.dao;

import java.util.List;
import modelo.beans.Cuenta;

public interface IntCuentaDao {
	int updateSaldo(Cuenta cuenta);
	Cuenta findById(int numeroCuenta);
	List<Cuenta> findAll();
}
