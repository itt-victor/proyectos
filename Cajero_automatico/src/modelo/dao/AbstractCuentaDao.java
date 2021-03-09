package modelo.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

public abstract class AbstractCuentaDao {

	protected EntityManagerFactory emf;
	protected EntityManager em;
	protected EntityTransaction tx;
	protected Query query;
	protected String sql;
		
	public  AbstractCuentaDao() {
		emf = Persistence.createEntityManagerFactory("Cajero_automatico");
		em = emf.createEntityManager();
		tx = em.getTransaction();
	}

}
