package unitn.introsde.storage_service.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import unitn.introsde.storage_service.DAO.DBHelper;

import java.util.List;


/**
 * The persistent class for the externalsource database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Externalsource.findAll", query="SELECT e FROM Externalsource e")
	})
@XmlRootElement
@Table(name="externalsource")
public class Externalsource implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int exSource_id;

	@Lob
	@Column(name="exsource_desc")
	private String exsourceDesc;

	@Lob
	@Column(name="exsource_name")
	private String exsourceName;

	@Lob
	@Column(name="exsource_url")
	private String exsourceUrl;

	//bi-directional many-to-one association to Foodtrack
	@OneToMany(mappedBy="externalsource")
	private List<Foodtrack> foodtracks;

	public Externalsource() {
	}

	public int getExSource_id() {
		return this.exSource_id;
	}

	public void setExSource_id(int exSource_id) {
		this.exSource_id = exSource_id;
	}

	public String getExsourceDesc() {
		return this.exsourceDesc;
	}

	public void setExsourceDesc(String exsourceDesc) {
		this.exsourceDesc = exsourceDesc;
	}

	public String getExsourceName() {
		return this.exsourceName;
	}

	public void setExsourceName(String exsourceName) {
		this.exsourceName = exsourceName;
	}

	public String getExsourceUrl() {
		return this.exsourceUrl;
	}

	public void setExsourceUrl(String exsourceUrl) {
		this.exsourceUrl = exsourceUrl;
	}


	public void setFoodtracks(List<Foodtrack> foodtracks) {
		this.foodtracks = foodtracks;
	}
	
	////////////////////////////// CRUD ///////////////////////////////////////////

public static Externalsource getFoodSourceById(int id) {
     EntityManager em = DBHelper.instance.createEntityManager();
     Externalsource p = em.find(Externalsource.class, id);
     DBHelper.instance.closeConnections(em);
     return p;
}

public static Externalsource addFoodSource(Externalsource exSource){
		
	if (exSource == null)
		return null;
	if (exSource.getExsourceName() == null)
		return null;
	
	EntityManager em = DBHelper.instance.createEntityManager();
	
	EntityTransaction tx = em.getTransaction();
	tx.begin();
	em.persist(exSource);
	tx.commit();
	
    DBHelper.instance.closeConnections(em);
    return exSource;
}

public static boolean removeFoodSource(int id)
{
   EntityManager em = DBHelper.instance.createEntityManager();

    Externalsource u = em.find(Externalsource.class, id);
    if (u == null){
    	return false;
    }
      
   EntityTransaction tx = em.getTransaction();

   tx.begin();
   em.remove(u);
   tx.commit();
   em.close();

   return true;
}

public static Externalsource updateFoodSource(Externalsource e){
	
	// check input data of exSource u
		
	Externalsource exSource = Externalsource.getFoodSourceById(e.getExSource_id());
	if(exSource == null)
		return null;
	
	if(e.getExsourceName()!=null)exSource.setExsourceName(e.getExsourceName());
	exSource.setExsourceUrl(e.getExsourceUrl());
	exSource.setExsourceDesc(e.getExsourceDesc());
	
	 // update 
	  	
	 EntityManager em =DBHelper.instance.createEntityManager();
	 EntityTransaction tx = em.getTransaction();

	 tx.begin();
	 exSource = em.merge(exSource);
	 tx.commit();

	 em.close();
	
	 return exSource;
}


}