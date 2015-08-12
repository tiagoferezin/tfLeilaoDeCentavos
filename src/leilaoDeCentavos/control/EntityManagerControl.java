/**
 * 
 */
package leilaoDeCentavos.control;

import javax.persistence.EntityManager;

/**
 * @author Tiago Ferezin ( Data: 11/08/2015 );
 * Funcionalidade da Classe:
 *
 */
public class EntityManagerControl {

	public static void transactionBegin(EntityManager entityManager) {
		System.out.println("");
		System.out.println("EntityManagerControl.transactionBegin");
		System.out.println("  Verificando entityManager");
		if (entityManager!=null) {
			System.out.println("  Verificando entityManager.isOpen");
			if (entityManager.isOpen()) {
				System.out.println("  Verificando entityManager.getTransaction().isActive");
				if (!entityManager.getTransaction().isActive()) {
					System.out.println("  Iniciando transação");
					entityManager.getTransaction().begin();
				}
			}
		}
	}
	public static void transactionCommit(EntityManager entityManager) {
		System.out.println("");
		System.out.println("EntityManagerControl.transactionCommit");
		System.out.println("  Verificando entityManager");
		if (entityManager!=null) {
			System.out.println("  Verificando entityManager.isOpen");
			if (entityManager.isOpen()) {
				System.out.println("  Verificando entityManager.getTransaction().isActive");
				if (entityManager.getTransaction().isActive()) {
					System.out.println("  Comitando: entityManager.getTransaction().commit()");
					entityManager.getTransaction().commit();
				}
			}
		}
	}
	
	public static void transactionRollback(EntityManager entityManager) {
		System.out.println("");
		System.out.println("EntityManagerControl.transactionRollback");
		System.out.println("  Verificando entityManager");
		if (entityManager!=null) {
			System.out.println("  Verificando entityManager.isOpen");
			if (entityManager.isOpen()) {
				System.out.println("  Verificando entityManager.getTransaction().isActive");
				if (entityManager.getTransaction().isActive()) {
					System.out.println("  Desfazendo: entityManager.getTransaction().rollback()");
					entityManager.getTransaction().rollback();
				}
			}
		}
	}

	
}
