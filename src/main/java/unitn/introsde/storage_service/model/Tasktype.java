package unitn.introsde.storage_service.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import unitn.introsde.storage_service.DAO.DBHelper;

import java.util.List;


/**
 * The persistent class for the tasktype database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Tasktype.findAll", query="SELECT t FROM Tasktype t")
	})
@XmlRootElement
@Table(name="tasktype")
public class Tasktype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int taskType_id;

	@Lob
	private String taskType_description;

	@Lob
	private String taskType_name;

	//bi-directional many-to-one association to Scheduledtask
	@OneToMany(mappedBy="tasktype")
	private List<Scheduledtask> scheduledtasks;

	public Tasktype() {
	}

	public int getTaskType_id() {
		return this.taskType_id;
	}

	public void setTaskType_id(int taskType_id) {
		this.taskType_id = taskType_id;
	}

	public String getTaskType_description() {
		return this.taskType_description;
	}

	public void setTaskType_description(String taskType_description) {
		this.taskType_description = taskType_description;
	}

	public String getTaskType_name() {
		return this.taskType_name;
	}

	public void setTaskType_name(String taskType_name) {
		this.taskType_name = taskType_name;
	}

	public List<Scheduledtask> getScheduledtasks() {
		return this.scheduledtasks;
	}

	public void setScheduledtasks(List<Scheduledtask> scheduledtasks) {
		this.scheduledtasks = scheduledtasks;
	}

	public Scheduledtask addScheduledtask(Scheduledtask scheduledtask) {
		getScheduledtasks().add(scheduledtask);
		scheduledtask.setTasktype(this);

		return scheduledtask;
	}

	public Scheduledtask removeScheduledtask(Scheduledtask scheduledtask) {
		getScheduledtasks().remove(scheduledtask);
		scheduledtask.setTasktype(null);

		return scheduledtask;
	}
    ////////////////////////////////// 
    // CRUD operation for task type Model 
      /////////////////////////////////

public static Tasktype gettasktypeById(Integer id) {
     EntityManager em = DBHelper.instance.createEntityManager();
     Tasktype tsktype = em.find(Tasktype.class, id);
     DBHelper.instance.closeConnections(em);
     return tsktype;
}

public static Tasktype addtasktype(Tasktype tsktype){
     EntityManager em = DBHelper.instance.createEntityManager();
     EntityTransaction tx = em.getTransaction();

     tx.begin();
     em.persist(tsktype);
     tx.commit();


    DBHelper.instance.closeConnections(em);
    return tsktype;
}
public static boolean removetasktype(int id)
{

   EntityManager em = DBHelper.instance.createEntityManager();

    Tasktype tt = em.find(Tasktype.class, id);

    if (tt == null){
	return false;
       }
      
   EntityTransaction tx = em.getTransaction();

   tx.begin();
   tt = em.merge(tt);
   em.remove(tt);
   tx.commit();

   em.close();

   return true;
}

public static Tasktype updatetasTasktype(Tasktype tt){
	
	Tasktype tasktype =Tasktype.gettasktypeById(tt.getTaskType_id());
	
	  tasktype.setTaskType_name(tt.getTaskType_name());
	  tasktype.setTaskType_description(tt.getTaskType_description());
	  
	
	  EntityManager em =DBHelper.instance.createEntityManager();
	  EntityTransaction tx = em.getTransaction();

	   tx.begin();
	   tasktype = em.merge(tasktype);
	   tx.commit();

	   em.close();
	
	   return tasktype;
}


public static List<Tasktype> getAll() {
   EntityManager em = DBHelper.instance.createEntityManager();
    List<Tasktype> list = em.createNamedQuery("Tasktype.findAll")
            .getResultList();

    em.close();
    return list;
}

}