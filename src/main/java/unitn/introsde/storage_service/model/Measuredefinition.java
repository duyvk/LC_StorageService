package unitn.introsde.storage_service.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import unitn.introsde.storage_service.DAO.DBHelper;

import java.util.List;


/**
 * The persistent class for the measuredefinition database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Measuredefinition.findAll", query="SELECT m FROM Measuredefinition m")
})
@XmlRootElement
@Table(name="measuredefinition")
public class Measuredefinition implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int meaDef_id;

	@Lob
	private String meaDef_description;

	@Lob
	private String meaDef_name;

	@Lob
	private String meaDef_type;

	@Lob
	private String meaDef_unit;
	

	private double MET;

	public double getMET() {
		return MET;
	}

	public void setMET(double mET) {
		MET = mET;
	}

	/*
	//bi-directional many-to-one association to Goal
	@OneToMany(mappedBy="measuredefinition")
	private List<Goal> goals;

	//bi-directional many-to-one association to Lifestatus
	@OneToMany(mappedBy="measuredefinition")
	private List<Lifestatus> lifestatuses;

	//bi-directional many-to-one association to Measuredefaultrange
	@OneToMany(mappedBy="measuredefinition")
	private List<Measuredefaultrange> measuredefaultranges;

	//bi-directional many-to-one association to Measurehistory
	@OneToMany(mappedBy="measuredefinition")
	private List<Measurehistory> measurehistories;
*/
	public Measuredefinition() {
	}

	public int getMeaDef_id() {
		return this.meaDef_id;
	}

	public void setMeaDef_id(int meaDef_id) {
		this.meaDef_id = meaDef_id;
	}

	public String getMeaDef_description() {
		return this.meaDef_description;
	}

	public void setMeaDef_description(String meaDef_description) {
		this.meaDef_description = meaDef_description;
	}

	public String getMeaDef_name() {
		return this.meaDef_name;
	}

	public void setMeaDef_name(String meaDef_name) {
		this.meaDef_name = meaDef_name;
	}

	public String getMeaDef_type() {
		return this.meaDef_type;
	}

	public void setMeaDef_type(String meaDef_type) {
		this.meaDef_type = meaDef_type;
	}

	public String getMeaDef_unit() {
		return this.meaDef_unit;
	}

	public void setMeaDef_unit(String meaDef_unit) {
		this.meaDef_unit = meaDef_unit;
	}
/*
	public List<Goal> getGoals() {
		return this.goals;
	}

	public void setGoals(List<Goal> goals) {
		this.goals = goals;
	}

	public Goal addGoal(Goal goal) {
		getGoals().add(goal);
		goal.setMeasuredefinition(this);

		return goal;
	}

	public Goal removeGoal(Goal goal) {
		getGoals().remove(goal);
		goal.setMeasuredefinition(null);

		return goal;
	}

	public List<Lifestatus> getLifestatuses() {
		return this.lifestatuses;
	}

	public void setLifestatuses(List<Lifestatus> lifestatuses) {
		this.lifestatuses = lifestatuses;
	}

	public Lifestatus addLifestatus(Lifestatus lifestatus) {
		getLifestatuses().add(lifestatus);
		lifestatus.setMeasuredefinition(this);

		return lifestatus;
	}

	public Lifestatus removeLifestatus(Lifestatus lifestatus) {
		getLifestatuses().remove(lifestatus);
		lifestatus.setMeasuredefinition(null);

		return lifestatus;
	}

	public List<Measuredefaultrange> getMeasuredefaultranges() {
		return this.measuredefaultranges;
	}

	public void setMeasuredefaultranges(List<Measuredefaultrange> measuredefaultranges) {
		this.measuredefaultranges = measuredefaultranges;
	}

	public Measuredefaultrange addMeasuredefaultrange(Measuredefaultrange measuredefaultrange) {
		getMeasuredefaultranges().add(measuredefaultrange);
		measuredefaultrange.setMeasuredefinition(this);

		return measuredefaultrange;
	}

	public Measuredefaultrange removeMeasuredefaultrange(Measuredefaultrange measuredefaultrange) {
		getMeasuredefaultranges().remove(measuredefaultrange);
		measuredefaultrange.setMeasuredefinition(null);

		return measuredefaultrange;
	}

	public List<Measurehistory> getMeasurehistories() {
		return this.measurehistories;
	}

	public void setMeasurehistories(List<Measurehistory> measurehistories) {
		this.measurehistories = measurehistories;
	}

	public Measurehistory addMeasurehistory(Measurehistory measurehistory) {
		getMeasurehistories().add(measurehistory);
		measurehistory.setMeasuredefinition(this);

		return measurehistory;
	}

	public Measurehistory removeMeasurehistory(Measurehistory measurehistory) {
		getMeasurehistories().remove(measurehistory);
		measurehistory.setMeasuredefinition(null);

		return measurehistory;
	}
*/
    ////////////////////////////////// 
    // CRUD operation for measure definition Model 
      /////////////////////////////////

public static Measuredefinition getMeasureDefById(Integer id) {
     EntityManager em = DBHelper.instance.createEntityManager();
     Measuredefinition md = em.find(Measuredefinition.class, id);
     DBHelper.instance.closeConnections(em);
     return md;
}

public static Measuredefinition addmeasuredefinition(Measuredefinition md){
	
	// to-do : check data before insert
     EntityManager em = DBHelper.instance.createEntityManager();
     EntityTransaction tx = em.getTransaction();

     tx.begin();
     em.persist(md);
     tx.commit();


    DBHelper.instance.closeConnections(em);
    return md;
}
public static boolean removemeasuredefinition(int id)
{

   EntityManager em = DBHelper.instance.createEntityManager();

    Measuredefinition md = em.find(Measuredefinition.class, id);

    if (md == null){
    	return false;
    }
      
   EntityTransaction tx = em.getTransaction();

   tx.begin();
   md = em.merge(md);
   em.remove(md);
   tx.commit();

   em.close();

   return true;
}

public static Measuredefinition updatemeasuredefinition(Measuredefinition md){
	
	Measuredefinition measuredef =Measuredefinition.getMeasureDefById(md.getMeaDef_id());
	
	if (measuredef == null)
		return null;
	// to-do : add if
	measuredef.setMeaDef_name(md.getMeaDef_name());
	measuredef.setMeaDef_type(md.getMeaDef_type());
	measuredef.setMeaDef_description(md.getMeaDef_description());
	measuredef.setMeaDef_unit(md.getMeaDef_unit());
	  
	
	  EntityManager em =DBHelper.instance.createEntityManager();
	  EntityTransaction tx = em.getTransaction();

	   tx.begin();
	   measuredef = em.merge(measuredef);
	   tx.commit();

	   em.close();
	
	   return measuredef;
}


public static List<Measuredefinition> getAll() {
   EntityManager em = DBHelper.instance.createEntityManager();
    List<Measuredefinition> list = em.createNamedQuery("Measuredefinition.findAll")
            .getResultList();

    em.close();
    return list;
}
}