/**
 * 
 */
package leilaoDeCentavos.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author Tiago Ferezin ( Data: 11/08/2015 );
 * Funcionalidade da Classe:
 *
 */
public class JPAUtil {
	
	public static EntityManager criaEntityManager() {

		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("default");
		return factory.createEntityManager();

	}

}
