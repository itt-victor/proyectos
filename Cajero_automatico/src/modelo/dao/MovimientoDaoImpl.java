package modelo.dao;

import java.util.List;

import modelo.beans.Movimiento;

public class MovimientoDaoImpl extends AbstractCuentaDao implements IntMovimientoDao{

	public MovimientoDaoImpl() {
		super();
	}

	@Override
	public int operacion(Movimiento movimiento) {
		int filas = 0;
		try {
			tx.begin();
			em.persist(movimiento);
			tx.commit();
			filas = 1;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return filas;
	}


	@Override
	public Movimiento findById(int idMovimiento) {
		
		return em.find(Movimiento.class, idMovimiento);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Movimiento> movimientosPorCuenta(int id_cuenta) {
		sql = "select m from Movimiento m where m.cuenta.numeroCuenta = :cod";
		query = em.createQuery(sql);
		query.setParameter("cod", id_cuenta);
		return query.getResultList();
	}
	

}
