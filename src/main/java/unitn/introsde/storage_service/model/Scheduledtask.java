package unitn.introsde.storage_service.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import unitn.introsde.storage_service.DAO.DBHelper;

import java.util.Date;


/**
 * The persistent class for the scheduledtask database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Scheduledtask.findAll", query="SELECT s FROM Scheduledtask s")
	})
@XmlRootElement
@Table(name="scheduletask")
public class Scheduledtask implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int scheTask_id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date scheTask_from_time;

	@Temporal(TemporalType.TIMESTAMP)
	private Date scheTask_to_time;

	@Lob
	private String schTask_address;

	private int schTask_cg_checked;

	private int schTask_note;

	private int schTask_status;

	private int schTask_user_checked;

	//bi-directional many-to-one association to Caregiver
	@ManyToOne
	@JoinColumn(name="cg_id")
	private Caregiver caregiver;

	//bi-directional many-to-one association to Tasktype
	@ManyToOne
	@JoinColumn(name="TaskType_id")
	private Tasktype tasktype;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	public Scheduledtask() {
	}

	public int getScheTask_id() {
		return this.scheTask_id;
	}

	public void setScheTask_id(int scheTask_id) {
		this.scheTask_id = scheTask_id;
	}

	public Date getScheTask_from_time() {
		return this.scheTask_from_time;
	}

	public void setScheTask_from_time(Date scheTask_from_time) {
		this.scheTask_from_time = scheTask_from_time;
	}

	public Date getScheTask_to_time() {
		return this.scheTask_to_time;
	}

	public void setScheTask_to_time(Date scheTask_to_time) {
		this.scheTask_to_time = scheTask_to_time;
	}

	public String getSchTask_address() {
		return this.schTask_address;
	}

	public void setSchTask_address(String schTask_address) {
		this.schTask_address = schTask_address;
	}

	public int getSchTask_cg_checked() {
		return this.schTask_cg_checked;
	}

	public void setSchTask_cg_checked(int schTask_cg_checked) {
		this.schTask_cg_checked = schTask_cg_checked;
	}

	public int getSchTask_note() {
		return this.schTask_note;
	}

	public void setSchTask_note(int schTask_note) {
		this.schTask_note = schTask_note;
	}

	public int getSchTask_status() {
		return this.schTask_status;
	}

	public void setSchTask_status(int schTask_status) {
		this.schTask_status = schTask_status;
	}

	public int getSchTask_user_checked() {
		return this.schTask_user_checked;
	}

	public void setSchTask_user_checked(int schTask_user_checked) {
		this.schTask_user_checked = schTask_user_checked;
	}

	public Caregiver getCaregiver() {
		return this.caregiver;
	}

	public void setCaregiver(Caregiver caregiver) {
		this.caregiver = caregiver;
	}

	public Tasktype getTasktype() {
		return this.tasktype;
	}

	public void setTasktype(Tasktype tasktype) {
		this.tasktype = tasktype;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	

    ////////////////////////////////// 
    // CRUD operation for User Model 
      /////////////////////////////////
	
	public static Scheduledtask getScheduledtaskById(Integer id) {
	     EntityManager em = DBHelper.instance.createEntityManager();
	     Scheduledtask s = em.find(Scheduledtask.class, id);
	     DBHelper.instance.closeConnections(em);
	     return s;
	}

	public static Scheduledtask addScheduledtask(Scheduledtask scheduledtask){
	     EntityManager em = DBHelper.instance.createEntityManager();
	     EntityTransaction tx = em.getTransaction();

	     tx.begin();
	     em.persist(scheduledtask);
	     tx.commit();


	    DBHelper.instance.closeConnections(em);
	    return scheduledtask;
	}
	public static boolean removeScheduledtask(int id)
	{

	   EntityManager em = DBHelper.instance.createEntityManager();

	   Scheduledtask s = em.find(Scheduledtask.class, id);

	    if (s== null){
		return false;
	       }
	      
	   EntityTransaction tx = em.getTransaction();

	   tx.begin();
	   s = em.merge(s);
	   em.remove(s);
	   tx.commit();

	   em.close();

	   return true;
	}

	public static Scheduledtask updateScheduledtask(Scheduledtask s){
		
		Scheduledtask scheduledtask =Scheduledtask.getScheduledtaskById(s.getScheTask_id());
		
		scheduledtask.setUser(s.getUser());
		scheduledtask.setCaregiver(s.getCaregiver());
		scheduledtask.setScheTask_from_time(s.getScheTask_from_time());
		scheduledtask.setScheTask_to_time(s.getScheTask_to_time());
		scheduledtask.setSchTask_address(s.getSchTask_address());
		scheduledtask.setSchTask_cg_checked(s.getSchTask_cg_checked());
		scheduledtask.setSchTask_user_checked(s.getSchTask_user_checked());
		scheduledtask.setSchTask_note(s.getSchTask_note());
		scheduledtask.setSchTask_status(s.getSchTask_status());
		scheduledtask.setTasktype(s.getTasktype());

		  EntityManager em =DBHelper.instance.createEntityManager();
		  EntityTransaction tx = em.getTransaction();

		   tx.begin();
		   scheduledtask = em.merge(scheduledtask);
		   tx.commit();

		   em.close();
		
		   return scheduledtask;
	}

}