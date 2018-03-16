/** 
 * @author Mykhailo Strilets
 */

package strilets.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "lector")
public class Lector {
	@Id
	@Column(name = "lector_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "lector_name")
	private String lectorName;

	@Column(name = "lector_degree")
	private String lectorDegree;

	@Column(name = "lector_salary")
	private int lectorSalary;

	@ManyToMany
	@JoinTable(name = "department_lector", joinColumns = @JoinColumn(name = "lector_id"), inverseJoinColumns = @JoinColumn(name = "department_id"))
	private List<Department> departments;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLectorName() {
		return lectorName;
	}

	public void setLectorName(String lectorName) {
		this.lectorName = lectorName;
	}

	public String getLectorDegree() {
		return lectorDegree;
	}

	public void setLectorDegree(String lectorDegree) {
		this.lectorDegree = lectorDegree;
	}

	public int getLectorSalary() {
		return lectorSalary;
	}

	public void setLectorSalary(int lectorSalary) {
		this.lectorSalary = lectorSalary;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

}
