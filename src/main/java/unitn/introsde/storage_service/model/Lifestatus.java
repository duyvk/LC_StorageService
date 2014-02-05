package unitn.introsde.storage_service.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import unitn.introsde.storage_service.DAO.DBHelper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the lifestatus database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Lifestatus.findAll", query="SELECT l FROM Lifestatus l")
	})
@XmlRootElement
@Table(name="lifestatus")
public class Lifestatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int lifeStatus_id;

	private double lifeStatus_value;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date lifeStatus_updated_time; 

	//bi-directional many-to-one association to Measuredefinition
	@ManyToOne
	@JoinColumn(name="MeaDef_id")
	private Measuredefinition measuredefinition;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	public Lifestatus() {
	}

	public int getLifeStatus_id() {
		return this.lifeStatus_id;
	}

	public void setLifeStatus_id(int lifeStatus_id) {
		this.lifeStatus_id = lifeStatus_id;
	}

	public double getLifeStatus_value() {
		return this.lifeStatus_value;
	}

	public void setLifeStatus_value(double lifeStatus_value) {
		this.lifeStatus_value = lifeStatus_value;
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

	public Date getLifeStatus_update_time() {
		return lifeStatus_updated_time;
	}

	public void setLifeStatus_update_time(Date lifeStatus_updated_time) {
		this.lifeStatus_updated_time = lifeStatus_updated_time;
	}
	
////////////////////////////////////////
// CRUD operation for LifeStatus Model 
//////////////////////////////////////

	public static Lifestatus getLifeStatusById(int id){
		
		EntityManager em = DBHelper.instance.createEntityManager();
		
		Lifestatus lf=em.find(Lifestatus.class, id);
		DBHelper.instance.closeConnections(em);
		
		return lf;
	}
	
	public static Lifestatus addLifestatus(Lifestatus lifestatus){
	    
		
		System.out.println("type: lifestatus");

	     
			if(lifestatus == null)
				return null;
			
			if(lifestatus.getMeasuredefinition()==null || lifestatus.getUser() ==null 
					||lifestatus.getLifeStatus_value()==0|| lifestatus.getLifeStatus_update_time()==null)
				
				return null;
			
			 EntityManager em = DBHelper.instance.createEntityManager();
		     EntityTransaction tx = em.getTransaction();
	     
	     tx.begin();
	     em.persist(lifestatus);
	     
	     tx.commit();


	    DBHelper.instance.closeConnections(em);
	    return lifestatus;
	}
	

	public static Lifestatus updateLifestatus(Lifestatus l){
		
		Lifestatus lifestatus =Lifestatus.getLifeStatusById(l.getLifeStatus_id());
		
		if (lifestatus== null)
			return null;
		lifestatus.setUser(l.getUser());
		lifestatus.setMeasuredefinition(l.getMeasuredefinition());
		lifestatus.setLifeStatus_value(l.getLifeStatus_value());
		
		  
		
		  EntityManager em =DBHelper.instance.createEntityManager();
		  EntityTransaction tx = em.getTransaction();

		   tx.begin();
		   lifestatus = em.merge(lifestatus);
		   tx.commit();

		   em.close();
		
		   return lifestatus;
	}

	
	
public static boolean removeLifeStatus(int id)
{

   EntityManager em = DBHelper.instance.createEntityManager();
   Lifestatus l = em.find(Lifestatus.class, id);
   EntityTransaction tx = em.getTransaction();

   
   tx.begin();
   
   l=em.merge(l);
   em.remove(l);
   tx.commit();
   

   DBHelper.instance.closeConnections(em);
   return true;

}
}