package eu.boot;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;

// import eu.avaca.test.App;

@ComponentScan({"com.example.demo", "eu.avaca", "eu.boot", "eu.avaca.model", "eu.avaca.repositories"})


@EntityScan("eu.avaca.model")
@EnableJpaRepositories("eu.avaca.repositories")
@SpringBootApplication
@RestController
public class DemoApplication 
{

	@Autowired
	CustomerController app;

    public static void main(String[] args) 
	{
      SpringApplication.run(DemoApplication.class, args);
    }


	// @Bean
	// public void runTestCustomer()
	// {
	// 	app.getAllCustomers();
	// }

	// @Bean
	// public void runTest()
	// {
	// 	try {
	// 		app.run();
	// 	} catch (SQLException e) {
	// 		e.printStackTrace();
	// 	}
	// }
	
    
    // @GetMapping("/hello")
    // public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
    //   return String.format("Hello %s!", name);
    // }


	
}
