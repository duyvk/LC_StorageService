package unitn.introsde.storage_service.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import java.math.BigDecimal;


/**
 * The persistent class for the measuredefaultrange database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Measuredefaultrange.findAll", query="SELECT m FROM Measuredefaultrange m")
	})
@XmlRootElement
@Table(name="measuredefaultrange")
public class Measuredefaultrange implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int meaRange_id;

	private int meaRange_alarm_Level;

	private BigDecimal meaRange_end_Value;

	@Lob
	private String meaRange_name;

	private BigDecimal meaRange_start_Value;

	//bi-directional many-to-one association to Measuredefinition
	@ManyToOne
	@JoinColumn(name="MeaDef_id")
	private Measuredefinition measuredefinition;

	public Measuredefaultrange() {
	}

	public int getMeaRange_id() {
		return this.meaRange_id;
	}

	public void setMeaRange_id(int meaRange_id) {
		this.meaRange_id = meaRange_id;
	}

	public int getMeaRange_alarm_Level() {
		return this.meaRange_alarm_Level;
	}

	public void setMeaRange_alarm_Level(int meaRange_alarm_Level) {
		this.meaRange_alarm_Level = meaRange_alarm_Level;
	}

	public BigDecimal getMeaRange_end_Value() {
		return this.meaRange_end_Value;
	}

	public void setMeaRange_end_Value(BigDecimal meaRange_end_Value) {
		this.meaRange_end_Value = meaRange_end_Value;
	}

	public String getMeaRange_name() {
		return this.meaRange_name;
	}

	public void setMeaRange_name(String meaRange_name) {
		this.meaRange_name = meaRange_name;
	}

	public BigDecimal getMeaRange_start_Value() {
		return this.meaRange_start_Value;
	}

	public void setMeaRange_start_Value(BigDecimal meaRange_start_Value) {
		this.meaRange_start_Value = meaRange_start_Value;
	}

	public Measuredefinition getMeasuredefinition() {
		return this.measuredefinition;
	}

	public void setMeasuredefinition(Measuredefinition measuredefinition) {
		this.measuredefinition = measuredefinition;
	}

}