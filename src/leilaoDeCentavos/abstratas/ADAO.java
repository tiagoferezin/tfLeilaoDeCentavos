/**
 * 
 */
package leilaoDeCentavos.abstratas;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import leilaoDeCentavos.interfaces.IDAO;
import leilaoDeCentavos.model.Auditoria;

/**
 * @author Tiago Ferezin ( Data: 11/08/2015 );
 * Funcionalidade da Classe:
 *
 */
public abstract class ADAO<T> implements IDAO, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void inserir() throws Exception {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("postgreSQL");
		EntityManager manager = factory.createEntityManager();
		try {
			manager.getTransaction().begin();
			manager.persist(this);
			manager.getTransaction().commit();
		} catch (Exception e) {
			if (manager.getTransaction().isActive()) {
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			manager.close();
		}
	}

	public void alterar() throws Exception {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("postgreSQL");
		EntityManager manager = factory.createEntityManager();
		T encontrada = (T) manager.find(this.getClass(), this.getId());
		try {
			manager.getTransaction().begin();
			manager.merge(this);
			manager.getTransaction().commit();
		} catch (Exception e) {
			if (manager.getTransaction().isActive()) {
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			manager.close();
		}
	}

	public void desativar() throws Exception {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("postgreSQL");
		EntityManager manager = factory.createEntityManager();
		T encontrada = (T) manager.find(this.getClass(), this.getId());
		try {
			manager.getTransaction().begin();
			manager.remove(this);
			manager.getTransaction().commit();
		} catch (Exception e) {
			if (manager.getTransaction().isActive()) {
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			manager.close();
		}
	}

	public List<Auditoria> getLista() {
		List<Auditoria> lista = null;
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("postgreSQL");
		EntityManager manager = factory.createEntityManager();
		try {
			Query query = manager.createQuery("from T t order by t.T desc");
			lista = query.getResultList();
		} catch (Exception e) {
			if (manager.getTransaction().isActive()) {
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			manager.close();
		}
		return lista;
	}

}
