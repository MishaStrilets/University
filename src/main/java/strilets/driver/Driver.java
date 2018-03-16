/** 
 * @author Mykhailo Strilets
 */

package strilets.driver;

import strilets.configuration.JPAConfig;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.sql.SQLException;

public class Driver {

	public static void main(String[] args) throws IOException, SQLException {
		@SuppressWarnings("resource")
		ApplicationContext context = new AnnotationConfigApplicationContext(JPAConfig.class);
		App application = context.getBean(App.class);
		application.run();
	}

}
