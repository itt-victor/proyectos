package modelo.dao;

import java.util.List;

import modelo.beans.Cuenta;

public class CuentaDaoImpl extends AbstractCuentaDao implements IntCuentaDao{

	public CuentaDaoImpl() {
		super();
	}

	@Override
	public Cuenta findById(int numeroCuenta) {
		return em.find(Cuenta.class, numeroCuenta);
	}
	
	@Override
	public int updateSaldo(Cuenta cuenta) {
		int filas = 0;
		try {
			tx.begin();
			em.merge(cuenta);
			tx.commit();
			filas= 1;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return filas;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Cuenta> findAll() {
		sql = "select c from Cuenta c";
		query = em.createQuery(sql);
		
		return query.getResultList();
	}

}
