package unitn.introsde.storage_service.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import unitn.introsde.storage_service.DAO.DBHelper;

import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the measurehistory database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Measurehistory.findAll", query="SELECT m FROM Measurehistory m")
	})
@XmlRootElement
@Table(name="measurehistory")
public class Measurehistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int meaHis_id;

	private String meaHis_calories;

	private String meaHis_updated_time;

	private BigDecimal meaHis_value;

	//bi-directional many-to-one association to Measuredefinition
	@ManyToOne
	@JoinColumn(name="MeaDef_id")
	private Measuredefinition measuredefinition;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	public Measurehistory() {
	}

	public int getMeaHis_id() {
		return this.meaHis_id;
	}

	public void setMeaHis_id(int meaHis_id) {
		this.meaHis_id = meaHis_id;
	}

	public String getMeaHis_calories() {
		return this.meaHis_calories;
	}

	public void setMeaHis_calories(String meaHis_calories) {
		this.meaHis_calories = meaHis_calories;
	}

	public String getMeaHis_updated_time() {
		return this.meaHis_updated_time;
	}

	public void setMeaHis_updated_time(String meaHis_updated_time) {
		this.meaHis_updated_time = meaHis_updated_time;
	}

	public BigDecimal getMeaHis_value() {
		return this.meaHis_value;
	}

	public void setMeaHis_value(BigDecimal meaHis_value) {
		this.meaHis_value = meaHis_value;
	}

	public Measuredefinition getMeasuredefinition() {
		return this.measuredefinition;
	}

	public void setMeasuredefinition(Measuredefinition measuredefinition) {
		this.measuredefinition = measuredefinition;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
    ////////////////////////////////// 
    // CRUD operation for measure history Model 
      /////////////////////////////////

public static Measurehistory getmeasurehistoryById(Integer id) {
     EntityManager em = DBHelper.instance.createEntityManager();
     Measurehistory mh = em.find(Measurehistory.class, id);
     DBHelper.instance.closeConnections(em);
     return mh;
}

public static Measurehistory addmeasurehistory(Measurehistory mh){
     EntityManager em = DBHelper.instance.createEntityManager();
     EntityTransaction tx = em.getTransaction();

     tx.begin();
     em.persist(mh);
     tx.commit();


    DBHelper.instance.closeConnections(em);
    return mh;
}
public static boolean removemeasurehistory(int id)
{

   EntityManager em = DBHelper.instance.createEntityManager();

    Measurehistory mh = em.find(Measurehistory.class, id);

    if (mh == null){
	return false;
       }
      
   EntityTransaction tx = em.getTransaction();

   tx.begin();
   mh = em.merge(mh);
   em.remove(mh);
   tx.commit();

   em.close();

   return true;
}

public static Measurehistory updatemeasurehistory(Measurehistory mh){
	
	Measurehistory measurehistory =Measurehistory.getmeasurehistoryById(mh.getMeaHis_id());
	
	measurehistory.setMeaHis_id(mh.getMeaHis_id());
	measurehistory.setMeaHis_calories(mh.getMeaHis_calories());
	measurehistory.setMeaHis_updated_time(mh.getMeaHis_updated_time());
	measurehistory.setMeaHis_value(mh.getMeaHis_value());
		  
	
	  EntityManager em =DBHelper.instance.createEntityManager();
	  EntityTransaction tx = em.getTransaction();

	   tx.begin();
	   measurehistory = em.merge(measurehistory);
	   tx.commit();

	   em.close();
	
	   return measurehistory;
}


public static List<Measurehistory> getAll() {
   EntityManager em = DBHelper.instance.createEntityManager();
    List<Measurehistory> list = em.createNamedQuery("Measurehistory.findAll")
            .getResultList();

    em.close();
    return list;
}
}