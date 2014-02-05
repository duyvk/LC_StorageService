package unitn.introsde.storage_service.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import unitn.introsde.storage_service.DAO.DBHelper;


/**
 * The persistent class for the food database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Food.findAll", query="SELECT f FROM Food f"),
	@NamedQuery(name="Food.getLocalFoodsByUserId", query="select f from Food f where f.user.userId = :user_id "),
	})
@XmlRootElement
@Table(name="food")
public class Food implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int food_id;

	@Column(name="food_calories")
	private int foodCalories;

	@Column(name="food_fat")
	private double foodFat;

	@Lob
	private String food_name;

	@Column(name="food_protein")
	private double foodProtein;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	public Food() {
	}

	public int getFood_id() {
		return this.food_id;
	}

	public void setFood_id(int food_id) {
		this.food_id = food_id;
	}

	public int getFoodCalories() {
		return this.foodCalories;
	}

	public void setFoodCalories(int foodCalories) {
		this.foodCalories = foodCalories;
	}

	public double getFoodFat() {
		return this.foodFat;
	}

	public void setFoodFat(double foodFat) {
		this.foodFat = foodFat;
	}

	public String getFood_name() {
		return this.food_name;
	}

	public void setFood_name(String food_name) {
		this.food_name = food_name;
	}

	public double getFoodProtein() {
		return this.foodProtein;
	}

	public void setFoodProtein(double foodProtein) {
		this.foodProtein = foodProtein;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

    ////////////////////////////////// 
    // CRUD operation for food Model 
      /////////////////////////////////

public static Food getFoodById(Integer id) {
     EntityManager em = DBHelper.instance.createEntityManager();
     Food f = em.find(Food.class, id);
     DBHelper.instance.closeConnections(em);
     return f;
}

public static Food addFood(Food food){
	
	if (food == null)
		return null;
	if(food.getFood_name() == null || 
			food.getFoodCalories()==0||food.getUser()==null)
		return null;
	
     EntityManager em = DBHelper.instance.createEntityManager();
     EntityTransaction tx = em.getTransaction();

     tx.begin();
     em.persist(food);
     tx.commit();


    DBHelper.instance.closeConnections(em);
    return food;
}
public static boolean removeFood(int id)
{

   EntityManager em = DBHelper.instance.createEntityManager();

   Food f = em.find(Food.class, id);

    if (f == null){
    	return false;
    }
      
   EntityTransaction tx = em.getTransaction();

   tx.begin();
   f = em.merge(f);
   em.remove(f);
   tx.commit();

   em.close();

   return true;
}

public static Food updateFood(Food f){
	
	Food food =Food.getFoodById(f.getFood_id());
	
	if(f.getFood_name()!=null)food.setFood_name(f.getFood_name());
	if(f.getFoodCalories()!=0)food.setFoodCalories(f.getFoodCalories());
	if(f.getFoodFat()!=0)food.setFoodFat(f.getFoodFat());
	if(f.getFoodProtein()!=0)food.setFoodProtein(f.getFoodProtein());
	if(f.getUser()!=null) food.setUser(f.getUser());

	  EntityManager em =DBHelper.instance.createEntityManager();
	  EntityTransaction tx = em.getTransaction();

	   tx.begin();
	   food = em.merge(food);
	   tx.commit();

	   em.close();
	
	   return food;
}


public static List<Food> getLocalFoodsByUserId(int user_id){
	EntityManager em = DBHelper.instance.createEntityManager();
	List<Food> userLocalFoods = (List<Food>) em.createNamedQuery("Food.getLocalFoodsByUserId")
			         .setParameter("user_id", user_id)
			         .getResultList();
	
	DBHelper.instance.closeConnections(em);
	return userLocalFoods;
}


public static List<Food> getAll() {
   EntityManager em = DBHelper.instance.createEntityManager();
    List<Food> list = em.createNamedQuery("Food.findAll")
            .getResultList();

    em.close();
    return list;
}
	
}