package eu.boot;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.avaca.model.BaseRecord;
import eu.avaca.model.Customer;
import eu.avaca.repositories.CustomerRepository;
import eu.boot.exception.ResourceNotFound;

@RestController
@RequestMapping("/cust")
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController<T extends BaseRecord>
{
    @Autowired
    CustomerRepository   customerRepo ;

    public CustomerController(){}


    //load all custommers
    @GetMapping("getAll")
    public List<Customer> getAllCustomers()
    {
        List<Customer> lst = (List<Customer>) customerRepo.findAll();
        printAll((List<T>) lst);
        return lst;
    }

    
    public void printAll(List<T> lst)
    {
        lst.stream().forEach(rec -> System.out.println(rec.toString()));
    }

    //load customer by id
    @GetMapping("/record/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable(value = "id") Long customerId) throws ResourceNotFound {
		Customer customer = customerRepo.findById(customerId).orElseThrow(() -> new ResourceNotFound("Customer not found for this id :: " + customerId));;
		return ResponseEntity.ok().body(customer);
	}

    //save customer
    @RequestMapping(value = "/customers", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Customer createCustomer( @RequestBody Customer customer) 
    {
		Customer cust =  customerRepo.save(customer);
        System.out.println("Record saved");
        System.out.println("ID is: " + customer.getID());   
        return cust;
	}

    //update customer 
    @PutMapping("/update/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable(value = "id") Long customerId,@RequestBody Customer customerData) throws ResourceNotFound {
		

        Customer customer = customerRepo.findById(customerId).orElseThrow(() -> new ResourceNotFound("Customer not found for this id :: " + customerId));
  
        
        customer.setID(customerData.getID());
		customer.setName(customerData.getName());
		customer.setSurname(customerData.getSurname());
        //customer.setAddress(customerData.getAddress());
		final Customer updatedCustomer = customerRepo.save(customer);
		return ResponseEntity.ok(updatedCustomer);
	}

    @DeleteMapping("/customer/{id}")
	public Map<String, Boolean> deleteCustomer(@PathVariable(value = "id") Long customerId)throws ResourceNotFound{
		Customer customer = customerRepo.findById(customerId).orElseThrow(() -> new ResourceNotFound("Customer not found for this id :: " + customerId));

		customerRepo.delete(customer);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
