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

	public List<Foodtrack> getFoodtracks() {
		return this.foodtracks;
	}

	public void setFoodtracks(List<Foodtrack> foodtracks) {
		this.foodtracks = foodtracks;
	}

	public Foodtrack addFoodtrack(Foodtrack foodtrack) {
		getFoodtracks().add(foodtrack);
		foodtrack.setExternalsource(this);

		return foodtrack;
	}

	public Foodtrack removeFoodtrack(Foodtrack foodtrack) {
		getFoodtracks().remove(foodtrack);
		foodtrack.setExternalsource(null);

		return foodtrack;
	}

}