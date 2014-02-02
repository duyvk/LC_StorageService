package unitn.introsde.storage_service.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import unitn.introsde.storage_service.DAO.DBHelper;


/**
 * The persistent class for the allergy database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Allergy.findAll", query="SELECT a FROM Allergy a")
	})
@XmlRootElement
@Table (name="allergy")
public class Allergy implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int allergy_id;

	private int allergy_name;

	@Lob
	private String allergy_reaction;

	@Lob
	private String allergy_treatment;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	public Allergy() {
	}

	public int getAllergy_id() {
		return this.allergy_id;
	}

	public void setAllergy_id(int allergy_id) {
		this.allergy_id = allergy_id;
	}

	public int getAllergy_name() {
		return this.allergy_name;
	}

	public void setAllergy_name(int allergy_name) {
		this.allergy_name = allergy_name;
	}

	public String getAllergy_reaction() {
		return this.allergy_reaction;
	}

	public void setAllergy_reaction(String allergy_reaction) {
		this.allergy_reaction = allergy_reaction;
	}

	public String getAllergy_treatment() {
		return this.allergy_treatment;
	}

	public void setAllergy_treatment(String allergy_treatment) {
		this.allergy_treatment = allergy_treatment;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	   ////////////////////////////////// 
    // CRUD operation for allergy Model 
      /////////////////////////////////

public static Allergy getAllergyById(Integer id) {
     EntityManager em = DBHelper.instance.createEntityManager();
     Allergy a = em.find(Allergy.class, id);
     DBHelper.instance.closeConnections(em);
     return a;
}

public static Allergy addAllergy(Allergy allergy){
     EntityManager em = DBHelper.instance.createEntityManager();
     EntityTransaction tx = em.getTransaction();

     tx.begin();
     em.persist(allergy);
     tx.commit();


    DBHelper.instance.closeConnections(em);
    return allergy;
}
public static boolean removeAllergy(int id)
{

   EntityManager em = DBHelper.instance.createEntityManager();

   Allergy a = em.find(Allergy.class, id);

    if (a == null){
	return false;
       }
      
   EntityTransaction tx = em.getTransaction();

   tx.begin();
   a = em.merge(a);
   em.remove(a);
   tx.commit();

   em.close();

   return true;
}

public static Allergy updateAllergy(Allergy a){
	
	Allergy allergy =Allergy.getAllergyById(a.getAllergy_id());
	
	allergy.setAllergy_name(a.getAllergy_name());
	allergy.setAllergy_reaction(a.getAllergy_reaction());
	allergy.setAllergy_reaction(a.getAllergy_reaction());
	allergy.setAllergy_treatment(a.getAllergy_treatment());
	allergy.setUser(a.getUser());
	
	
	  
	 
	  
	
	  EntityManager em =DBHelper.instance.createEntityManager();
	  EntityTransaction tx = em.getTransaction();

	   tx.begin();
	   allergy = em.merge(allergy);
	   tx.commit();

	   em.close();
	
	   return allergy;
}


public static List<Allergy> getAll() {
   EntityManager em = DBHelper.instance.createEntityManager();
    List<Allergy> list = em.createNamedQuery("Food.findAll")
            .getResultList();

    em.close();
    return list;
}
	
}