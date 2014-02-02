package unitn.introsde.storage_service.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import unitn.introsde.storage_service.DAO.DBHelper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the goal database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Goal.findAll", query="SELECT g FROM Goal g")
	})
@XmlRootElement
@Table(name="goal")
public class Goal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="goal_id")
	private int goalId;

	@Lob
	@Column(name="goal_desc")
	private String goalDesc;

	private double goal_expected_value;

	@Temporal(TemporalType.TIMESTAMP)
	private Date goal_from_date;

	@Temporal(TemporalType.TIMESTAMP)
	private Date goal_to_date;

	//bi-directional many-to-one association to Caregiver
	@ManyToOne
	@JoinColumn(name="cg_id")
	private Caregiver caregiver;

	//bi-directional many-to-one association to Measuredefinition
	@ManyToOne
	@JoinColumn(name="MeaDef_id")
	private Measuredefinition measuredefinition;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	public Goal() {
	}

	public int getGoalId() {
		return this.goalId;
	}

	public void setGoalId(int goalId) {
		this.goalId = goalId;
	}

	public String getGoalDesc() {
		return this.goalDesc;
	}

	public void setGoalDesc(String goalDesc) {
		this.goalDesc = goalDesc;
	}

	public double getGoal_expected_value() {
		return this.goal_expected_value;
	}

	public void setGoal_expected_value(double goal_expected_value) {
		this.goal_expected_value = goal_expected_value;
	}

	public Date getGoal_from_date() {
		return this.goal_from_date;
	}

	public void setGoal_from_date(Date goal_from_date) {
		this.goal_from_date = goal_from_date;
	}

	public Date getGoal_to_date() {
		return this.goal_to_date;
	}

	public void setGoal_to_date(Date goal_to_date) {
		this.goal_to_date = goal_to_date;
	}

	public Caregiver getCaregiver() {
		return this.caregiver;
	}

	public void setCaregiver(Caregiver caregiver) {
		this.caregiver = caregiver;
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
    // CRUD operation for goal Model 
      /////////////////////////////////
	
	public static Goal getGoalById(Integer id) {
	     EntityManager em = DBHelper.instance.createEntityManager();
	     Goal p = em.find(Goal.class, id);
	     DBHelper.instance.closeConnections(em);
	     return p;
	}

	public static Goal addGoal(Goal goal){
	     EntityManager em = DBHelper.instance.createEntityManager();
	     EntityTransaction tx = em.getTransaction();

	     tx.begin();
	     em.persist(goal);
	     tx.commit();


	    DBHelper.instance.closeConnections(em);
	    return goal;
	}
	public static boolean removeGoal(int id)
	{

	   EntityManager em = DBHelper.instance.createEntityManager();

	   Goal g = em.find(Goal.class, id);

	    if (g== null){
		return false;
	       }
	      
	   EntityTransaction tx = em.getTransaction();

	   tx.begin();
	   g = em.merge(g);
	   em.remove(g);
	   tx.commit();

	   em.close();

	   return true;
	}

	public static Goal updateGoal(Goal g){
		
		Goal goal =Goal.getGoalById(g.getGoalId());
		
		  goal.setUser(g.getUser());
		  goal.setCaregiver(g.getCaregiver());
		  goal.setGoal_expected_value(g.getGoal_expected_value());
		  goal.setGoal_from_date(g.getGoal_from_date());
		  goal.setGoal_to_date(g.getGoal_to_date());
		  goal.setGoalDesc(g.getGoalDesc());
		  
		
		  EntityManager em =DBHelper.instance.createEntityManager();
		  EntityTransaction tx = em.getTransaction();

		   tx.begin();
		   goal = em.merge(goal);
		   tx.commit();

		   em.close();
		
		   return goal;
	}

	/*public static List<Goal> getAllUserGoals(int user_id){
	
    EntityManager em = DBHelper.instance.createEntityManager();
		
		List<Goal> userGoals = (List<Goal>) em.createQuery("select gl from Goal gl where gl.user.userId = :user_id ")
				         .setParameter("user_id", user_id)
				         .getResultList();
		
		DBHelper.instance.closeConnections(em);
		return userGoals;
		
	}
	
	public static List<Goal> getGoalByTimeRange(int user_id,Date from_date ,Date to_date){
		
		EntityManager em = DBHelper.instance.createEntityManager();

		List<Goal> goal = (List<Goal>) em.createQuery("select gl from Goal gl where gl.user.userId = :user_id and gl.goal_from_date >= :from_date and gl.goal_to_date <= :to_date")
						         .setParameter("user_id", user_id)
						         .setParameter("from_date", from_date)
						         .setParameter("to_date", to_date)
						         .getResultList();
				
				DBHelper.instance.closeConnections(em);
				return goal;
				
			}
	
public static Goal addUserGoal(Goal g){

		 EntityManager em = DBHelper.instance.createEntityManager();
	     EntityTransaction tx = em.getTransaction();

	     tx.begin();
	     em.persist(g);
	     tx.commit();


	    DBHelper.instance.closeConnections(em);
	    return g;
		
	}
*/
	

}