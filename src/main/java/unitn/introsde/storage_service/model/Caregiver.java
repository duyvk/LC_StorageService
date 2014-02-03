package unitn.introsde.storage_service.model;

import java.awt.CardLayout;
import java.io.Serializable;

import javax.persistence.*;
import javax.swing.text.Caret;
import javax.xml.bind.annotation.XmlRootElement;

import unitn.introsde.storage_service.DAO.DBHelper;

import java.util.List;


/**
 * The persistent class for the caregiver database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Caregiver.findAll", query="SELECT c FROM Caregiver c")
	})
@XmlRootElement
@Table(name="caregiver")
public class Caregiver implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="cg_id")
	private int cgId;

	@Lob
	@Column(name="cg_address")
	private String cgAddress;

	@Lob
	@Column(name="cg_email")
	private String cgEmail;

	@Lob
	@Column(name="cg_first_name")
	private String cgFirstName;

	@Lob
	@Column(name="cg_last_name")
	private String cgLastName;

	@Lob
	@Column(name="cg_phone_number")
	private String cgPhoneNumber;
/*
	//bi-directional many-to-one association to Goal
	@OneToMany(mappedBy="caregiver")
	private List<Goal> goals;

	//bi-directional many-to-one association to Scheduledtask
	@OneToMany(mappedBy="caregiver")
	private List<Scheduledtask> scheduledtasks;
*/
	public Caregiver() {
	}

	public int getCgId() {
		return this.cgId;
	}

	public void setCgId(int cgId) {
		this.cgId = cgId;
	}

	public String getCgAddress() {
		return this.cgAddress;
	}

	public void setCgAddress(String cgAddress) {
		this.cgAddress = cgAddress;
	}

	public String getCgEmail() {
		return this.cgEmail;
	}

	public void setCgEmail(String cgEmail) {
		this.cgEmail = cgEmail;
	}

	public String getCgFirstName() {
		return this.cgFirstName;
	}

	public void setCgFirstName(String cgFirstName) {
		this.cgFirstName = cgFirstName;
	}

	public String getCgLastName() {
		return this.cgLastName;
	}

	public void setCgLastName(String cgLastName) {
		this.cgLastName = cgLastName;
	}

	public String getCgPhoneNumber() {
		return this.cgPhoneNumber;
	}

	public void setCgPhoneNumber(String cgPhoneNumber) {
		this.cgPhoneNumber = cgPhoneNumber;
	}

/*	public List<Goal> getGoals() {
		return this.goals;
	}

	public void setGoals(List<Goal> goals) {
		this.goals = goals;
	}

	public Goal addGoal(Goal goal) {
		getGoals().add(goal);
		goal.setCaregiver(this);

		return goal;
	}

	public Goal removeGoal(Goal goal) {
		getGoals().remove(goal);
		goal.setCaregiver(null);

		return goal;
	}

	public List<Scheduledtask> getScheduledtasks() {
		return this.scheduledtasks;
	}

	public void setScheduledtasks(List<Scheduledtask> scheduledtasks) {
		this.scheduledtasks = scheduledtasks;
	}

	public Scheduledtask addScheduledtask(Scheduledtask scheduledtask) {
		getScheduledtasks().add(scheduledtask);
		scheduledtask.setCaregiver(this);

		return scheduledtask;
	}

	public Scheduledtask removeScheduledtask(Scheduledtask scheduledtask) {
		getScheduledtasks().remove(scheduledtask);
		scheduledtask.setCaregiver(null);

		return scheduledtask;
	}
*/
    ////////////////////////////////// 
    // CRUD operation for Caregiver Model 
      /////////////////////////////////


public static Caregiver getCaregiverById(Integer id) {
     EntityManager em = DBHelper.instance.createEntityManager();
     Caregiver cg = em.find(Caregiver.class, id);
     DBHelper.instance.closeConnections(em);
     return cg;
}

public static Caregiver addCaregiver(Caregiver cg){
	
	// to-do: check input data of cg
     EntityManager em = DBHelper.instance.createEntityManager();
     EntityTransaction tx = em.getTransaction();

     tx.begin();
     em.persist(cg);
     tx.commit();


    DBHelper.instance.closeConnections(em);
    return cg;
}
public static boolean removeCaregiver(int id)
{

   EntityManager em = DBHelper.instance.createEntityManager();

    Caregiver cg = em.find(Caregiver.class, id);

    if (cg == null){
    	return false;
    }
      
   EntityTransaction tx = em.getTransaction();

   tx.begin();
   cg = em.merge(cg);
   em.remove(cg);
   tx.commit();

   em.close();

   return true;
}

public static Caregiver updatecaregiver(Caregiver cg){
	
	Caregiver cgiver =Caregiver.getCaregiverById(cg.getCgId());
	if(cgiver == null)
		return null;
	
	// to-do: add if
	if(cg.getCgFirstName() != null)cgiver.setCgFirstName(cg.getCgFirstName());
	cgiver.setCgLastName(cg.getCgLastName());
	cgiver.setCgEmail(cg.getCgEmail());
	cgiver.setCgAddress(cg.getCgAddress());
	cgiver.setCgPhoneNumber(cg.getCgPhoneNumber());
	  
	
	EntityManager em =DBHelper.instance.createEntityManager();
	EntityTransaction tx = em.getTransaction();

	tx.begin();
	cgiver = em.merge(cgiver);
	tx.commit();

	em.close();
	
	return cgiver;
}


public static List<Caregiver> getAll() {
   EntityManager em = DBHelper.instance.createEntityManager();
    List<Caregiver> list = em.createNamedQuery("Caregiver.findAll")
            .getResultList();

    em.close();
    return list;
}
}