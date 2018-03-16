/** 
 * @author Mykhailo Strilets
 */

package strilets.driver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import strilets.dao.UniversityDao;
import strilets.entity.Department;
import strilets.entity.Lector;

public class App {

	@Autowired
	private UniversityDao universityDao;

	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	private BufferedReader getReader() {
		return reader;
	}

	public void run() throws IOException, SQLException {
		System.out.println("Welcome to university! \nWrite instructions below:");
		System.out.println("\t Who is head of department {department_name}");
		System.out.println("\t Show {department_name} statistic");
		System.out.println("\t Show the average salary for department {department_name}");
		System.out.println("\t Show count of employee for {department_name}");
		System.out.println("\t Global search by {template}");
		System.out.println("\t Exit");

		BufferedReader reader = getReader();

		while (true) {
			String command;
			command = reader.readLine();
			String[] operation = command.split(" ");

			if (operation.length == 6 && "Who".equals(operation[0]))
				showHead(operation[5]);

			else if ("Show".equals(operation[0])) {
				if (operation.length == 3 && "statistic".equals(operation[2]))
					showStatistic(operation[1]);
				else if (operation.length == 7 && "the".equals(operation[1]))
					showSalary(operation[6]);
				else if (operation.length == 6 && "count".equals(operation[1]))
					showCountEmployee(operation[5]);
				else
					System.out.println("Invalid input. Try again.");
			}

			else if (operation.length == 4 && "Global".equals(operation[0]))
				search(operation[3]);

			else if (operation.length == 1 && "Exit".equals(operation[0])) {
				System.out.println("Bye!");
				break;
			}

			else
				System.out.println("Invalid input. Try again.");
		}

		closeReader();
	}

	private void showHead(String departmentName) throws IOException, SQLException {
		Department department = new Department();
		department.setDepartmentName(departmentName);

		if (universityDao.isDepartmentExist(department)) {
			department.setDepartmentHead(universityDao.showHeadOfDepartment(department));
			System.out.println("Answer: Head of " + department.getDepartmentName() + " department is "
					+ department.getDepartmentHead());
		} else
			System.out.println("There is no such department.");
	}

	private void showStatistic(String departmentName) throws IOException, SQLException {
		Department department = new Department();
		department.setDepartmentName(departmentName);

		if (universityDao.isDepartmentExist(department)) {
			HashMap<String, Long> statistic = new HashMap<String, Long>();
			statistic = universityDao.showStatistic(department);
			System.out.println("Answer:  assistans - " + statistic.get("assistans") + "\n \t associate professors - "
					+ statistic.get("associate professors") + "\n \t professors - " + statistic.get("professors"));
		} else
			System.out.println("There is no such department.");
	}

	private void showSalary(String departmentName) throws IOException, SQLException {
		Department department = new Department();
		department.setDepartmentName(departmentName);

		if (universityDao.isDepartmentExist(department)) {
			Double averageSalary = universityDao.showAverageSalary(department);
			System.out.println(
					"Answer: The average salary of " + department.getDepartmentName() + " is " + averageSalary);
		} else
			System.out.println("There is no such department.");
	}

	private void showCountEmployee(String departmentName) throws IOException, SQLException {
		Department department = new Department();
		department.setDepartmentName(departmentName);

		if (universityDao.isDepartmentExist(department)) {
			Long countEmployee = universityDao.showCountOfEmployee(department);
			System.out.println("Answer: " + countEmployee);
		} else
			System.out.println("There is no such department.");
	}

	private void search(String lectorName) throws IOException, SQLException {
		Lector lector = new Lector();
		lector.setLectorName(lectorName);
		List<String> lectorList = new ArrayList<String>();
		lectorList = universityDao.globalSearch(lector);

		if (lectorList.size() == 0)
			System.out.println("No result search.");
		else {
			System.out.print("Answer: ");
			for (int i = 0; i < lectorList.size(); i++)
				System.out.print(lectorList.get(i) + ", ");
			System.out.println();
		}

	}

	private void closeReader() throws IOException {
		if (reader != null) {
			reader.close();
		}
	}

}
