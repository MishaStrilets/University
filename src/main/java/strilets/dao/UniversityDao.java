/** 
 * @author Mykhailo Strilets
 */

package strilets.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import strilets.entity.Department;
import strilets.entity.Lector;

public interface UniversityDao {

	public String showHeadOfDepartment(Department department) throws SQLException;

	public HashMap<String, Long> showStatistic(Department department) throws SQLException;

	public Double showAverageSalary(Department department) throws SQLException;

	public Long showCountOfEmployee(Department department) throws SQLException;

	public List<String> globalSearch(Lector lector) throws SQLException;
	
	public boolean isDepartmentExist(Department department);

}
