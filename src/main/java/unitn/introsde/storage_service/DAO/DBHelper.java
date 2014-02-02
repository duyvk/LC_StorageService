package unitn.introsde.storage_service.DAO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

// TODO: Auto-generated Javadoc
/**
 * The Enum DBHelper.
 */
public enum DBHelper {
	
	/** The instance. */
	instance;
	
	/** The emf. */
	private EntityManagerFactory emf;
	
	/**
	 * Instantiates a new dB helper.
	 */
	private DBHelper() {
		if(emf != null)
			emf.close();
		emf = Persistence.createEntityManagerFactory("StorageService");
	}
	
	/**
	 * Creates the entity manager.
	 *
	 * @return the entity manager
	 */
	public EntityManager createEntityManager() {
		return emf.createEntityManager();
	}

	/**
	 * Close connections.
	 *
	 * @param em the em
	 */
	public void closeConnections(EntityManager em) {
		em.close();
	}

	/**
	 * Gets the transaction.
	 *
	 * @param em the em
	 * @return the transaction
	 */
	public EntityTransaction getTransaction(EntityManager em) {
		return em.getTransaction();
	}
	
	/**
	 * Gets the entity manager factory.
	 *
	 * @return the entity manager factory
	 */
	public EntityManagerFactory getEntityManagerFactory() {
		return emf;
	}
}
