/** 
 * @author Mykhailo Strilets
 */

package strilets.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import strilets.entity.Department;
import strilets.entity.Lector;

public class UniversityDaoImpl implements UniversityDao {
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public String showHeadOfDepartment(Department department) throws SQLException {
		return entityManager
				.createQuery(
						"SELECT department.departmentHead FROM Department department WHERE department.departmentName LIKE :departmentName")
				.setParameter("departmentName", department.getDepartmentName()).getSingleResult().toString();
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public HashMap<String, Long> showStatistic(Department department) throws SQLException {
		HashMap<String, Long> statistic = new HashMap<String, Long>();

		Query queryAssistans = entityManager
				.createQuery(
						"SELECT COUNT(lector.lectorName) FROM Lector lector JOIN lector.departments d WHERE lector.lectorDegree='assistant' AND d.departmentName LIKE :departmentName")
				.setParameter("departmentName", department.getDepartmentName());
		Long countAssistants = (Long) queryAssistans.getSingleResult();
		statistic.put("assistans", countAssistants);

		Query queryAssociateProfessors = entityManager
				.createQuery(
						"SELECT COUNT(lector.lectorName) FROM Lector lector JOIN lector.departments d WHERE lector.lectorDegree='associate professor' AND d.departmentName LIKE :departmentName")
				.setParameter("departmentName", department.getDepartmentName());
		Long countAssociateProfessors = (Long) queryAssociateProfessors.getSingleResult();
		statistic.put("associate professors", countAssociateProfessors);

		Query queryProfessors = entityManager
				.createQuery(
						"SELECT COUNT(lector.lectorName) FROM Lector lector JOIN lector.departments d WHERE lector.lectorDegree='professor' AND d.departmentName LIKE :departmentName")
				.setParameter("departmentName", department.getDepartmentName());
		Long countProfessors = (Long) queryProfessors.getSingleResult();
		statistic.put("professors", countProfessors);

		return statistic;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Double showAverageSalary(Department department) throws SQLException {
		Query query = entityManager
				.createQuery(
						"SELECT AVG(lector.lectorSalary) FROM Lector lector JOIN lector.departments d WHERE d.departmentName LIKE :departmentName")
				.setParameter("departmentName", department.getDepartmentName());
		return (Double) query.getSingleResult();
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Long showCountOfEmployee(Department department) throws SQLException {
		Query query = entityManager
				.createQuery(
						"SELECT COUNT(lector.lectorName) FROM Lector lector JOIN lector.departments d WHERE d.departmentName LIKE :departmentName")
				.setParameter("departmentName", department.getDepartmentName());
		return (Long) query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<String> globalSearch(Lector lector) throws SQLException {
		return entityManager
				.createQuery("SELECT lector.lectorName FROM Lector lector WHERE lector.lectorName LIKE :lectorName")
				.setParameter("lectorName", "%" + lector.getLectorName() + "%").getResultList();
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public boolean isDepartmentExist(Department department) {
		Query query = entityManager
				.createQuery(
						"SELECT COUNT(department) FROM Department department WHERE department.departmentName LIKE :departmentName")
				.setParameter("departmentName", department.getDepartmentName());
		Long count = (Long) query.getSingleResult();
		return ((count.equals(0L)) ? false : true);
	}

}
