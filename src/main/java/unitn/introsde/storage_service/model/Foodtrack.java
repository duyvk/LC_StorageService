package unitn.introsde.storage_service.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import unitn.introsde.storage_service.DAO.DBHelper;
import unitn.introsde.storage_service.utils.Utils;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the foodtrack database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Foodtrack.findAll", query="SELECT f FROM Foodtrack f"),
	@NamedQuery(name="FoodTrack.getFoodTrackOfUserByTimeRange", query="select ft from Foodtrack ft where ft.user.userId = :user_id" +
									" and ft.foodtrackTime between :afterDate and :beforeDate "),
	@NamedQuery(name="FoodTrack.getFoodTracksByUserId", query="select ft from Foodtrack ft where ft.user.userId=:user_id")
	})
@XmlRootElement
@Table (name="foodtrack")
public class Foodtrack implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="foodtrack_id")
	private int foodtrackId;

	@Column(name="foodtrack_food_id")
	private int FoodId;

	@Lob
	@Column(name="foodtrack_meal")
	private String foodtrackMeal;
	
	
	@Column(name="foodtrack_amount")
	private double foodtrackAmount;
	
	@Lob
	@Column(name="foodtrack_unit")
	private String foodtrackUnit;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="foodtrack_time")
	private Date foodtrackTime;

	//bi-directional many-to-one association to Externalsource
	@ManyToOne
	@JoinColumn(name="exsource_id")
	private Externalsource externalsource;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	public Foodtrack() {
	}

	public int getFoodtrackId() {
		return this.foodtrackId;
	}

	public void setFoodtrackId(int foodtrackId) {
		this.foodtrackId = foodtrackId;
	}
	

	public double getFoodtrackAmount() {
		return foodtrackAmount;
	}
	
	public void setFoodtrackAmount(double foodtrackAmount) {
		this.foodtrackAmount = foodtrackAmount;
	}
	
	public String getFoodtrackUnit() {
		return foodtrackUnit;
	}
	
	public void setFoodtrackUnit(String foodtrackUnit) {
		this.foodtrackUnit = foodtrackUnit;
	}

	public int getFoodtrackFoodId() {
		return this.FoodId;
	}

	public void setFoodtrackFoodId(int foodtrackFoodId) {
		this.FoodId = foodtrackFoodId;
	}

	public String getFoodtrackMeal() {
		return this.foodtrackMeal;
	}

	public void setFoodtrackMeal(String foodtrackMeal) {
		this.foodtrackMeal = foodtrackMeal;
	}

	public Date getFoodtrackTime() {
		return this.foodtrackTime;
	}

	public void setFoodtrackTime(Date foodtrackTime) {
		this.foodtrackTime = foodtrackTime;
	}

	public Externalsource getExternalsource() {
		return this.externalsource;
	}

	public void setExternalsource(Externalsource externalsource) {
		this.externalsource = externalsource;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
    ////////////////////////////////// 
    // CRUD operation for food track Model 
      /////////////////////////////////


public static Foodtrack getfoodtrackbyid(Integer id) {
     EntityManager em = DBHelper.instance.createEntityManager();
     Foodtrack ft = em.find(Foodtrack.class, id);
     DBHelper.instance.closeConnections(em);
     return ft;
}

public static Foodtrack addfooFoodtrack(Foodtrack ft){
	if (ft == null)
		return null;
	if(ft.getExternalsource()==null | ft.getFoodtrackFoodId()==0||
			ft.getFoodtrackMeal()==null||ft.getFoodtrackTime()==null
			||ft.getUser()==null)
		return null;
     EntityManager em = DBHelper.instance.createEntityManager();
     EntityTransaction tx = em.getTransaction();

     tx.begin();
     em.persist(ft);
     tx.commit();


    DBHelper.instance.closeConnections(em);
    return ft;
}
public static boolean removefoodtrack(int id)
{

   EntityManager em = DBHelper.instance.createEntityManager();

    Foodtrack ft = em.find(Foodtrack.class, id);

    if (ft == null){
    	return false;
    }
      
   EntityTransaction tx = em.getTransaction();

   tx.begin();
   ft = em.merge(ft);
   em.remove(ft);
   tx.commit();

   em.close();

   return true;
}

public static Foodtrack updateFoodTrack(Foodtrack ft){
	
	Foodtrack foodtrack =Foodtrack.getfoodtrackbyid(ft.getFoodtrackId());
	
	if (foodtrack == null)
		return null;
	
	if(ft.getFoodtrackFoodId()!=0)foodtrack.setFoodtrackFoodId(ft.getFoodtrackFoodId());
	if(ft.getFoodtrackMeal()!=null)foodtrack.setFoodtrackMeal(ft.getFoodtrackMeal());
	if(ft.getFoodtrackTime()!=null)foodtrack.setFoodtrackTime(ft.getFoodtrackTime());
	if(ft.getExternalsource()!=null)foodtrack.setExternalsource(ft.getExternalsource());
	if(ft.getUser()!=null) foodtrack.setUser(ft.getUser());
	if(ft.getFoodtrackAmount()!=0) foodtrack.setFoodtrackAmount(ft.getFoodtrackAmount());
	if(ft.getFoodtrackUnit()!=null) foodtrack.setFoodtrackUnit(ft.getFoodtrackUnit());
	  
	
	 EntityManager em =DBHelper.instance.createEntityManager();
	 EntityTransaction tx = em.getTransaction();

	 tx.begin();
	 foodtrack = em.merge(foodtrack);
	 tx.commit();

	 em.close();
	
	 return foodtrack;
}

public static List<Foodtrack> getFoodTrackOfUserByTimeRange(int user_id, Date fromDate, Date toDate){
	EntityManager em = DBHelper.instance.createEntityManager();
	List <Foodtrack> result = em.createNamedQuery("FoodTrack.getFoodTrackOfUserByTimeRange")
									.setParameter("user_id", user_id)
									.setParameter("afterDate", (fromDate))
									.setParameter("beforeDate", Utils.getDateafter(toDate))
									.getResultList();
	em.close();
	return result;
}

public static List<Foodtrack> getFoodTracksByUserId(int user_id){
	EntityManager em = DBHelper.instance.createEntityManager();
	List <Foodtrack> result = em.createNamedQuery("FoodTrack.getFoodTracksByUserId")
									.setParameter("user_id", user_id)
									.getResultList();
	em.close();
	return result;
}

public static List<Foodtrack> getAll() {
   EntityManager em = DBHelper.instance.createEntityManager();
    List<Foodtrack> list = em.createNamedQuery("Foodtrack.findAll")
            .getResultList();

    em.close();
    return list;
}


public static void main(String[] args) {
	/*List<Foodtrack> ft = Foodtrack.getFoodTrackOfUserByTimeRange(1, 
			Utils.strToDate("2014-01-01"), Utils.strToDate("2014-01-10"));*/
	List<Foodtrack> ft = Foodtrack.getFoodTracksByUserId(1);
	System.out.println(ft.size());
	System.out.println(ft.get(0).getFoodtrackMeal());
}

}