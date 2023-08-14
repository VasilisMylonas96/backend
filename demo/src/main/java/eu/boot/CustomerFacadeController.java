package eu.boot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import eu.avaca.dto.AddressDto;
import eu.avaca.dto.CustomerDto;
import eu.avaca.mappers.MapperUtil;
import eu.avaca.model.Address;
import eu.avaca.model.BaseRecord;
import eu.avaca.model.Customer;
import eu.avaca.repositories.CustomerRepository;
import eu.avaca.repositories.AddressRepository;
import eu.boot.exception.ResourceNotFound;

@RestController
@RequestMapping("/Facade/cust")
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerFacadeController<T extends BaseRecord> {

    @Autowired
    CustomerRepository customerRepo;

    @Autowired
    AddressRepository addressRepo;

    public CustomerFacadeController() {
    }

    // // load all customers
    // @GetMapping("getAll")
    // public String getAllCustomersAsString() throws JsonProcessingException {
    //     List<Customer> customers = customerRepo.findAll();

    //     List<CustomerDto> dtoList = customers.stream().map(customer -> {
    //         List<Address> addresses = addressRepo.findByCustomer_ID(customer.getID());
    //         customer.setAddress(addresses);
    //         return MapperUtil.serialize(customer);
    //     }).collect(Collectors.toList());

    //     printAll(dtoList);
    //     String listAsJson = MapperUtil.toJson(dtoList); 
    //     System.out.println(listAsJson);
    //     return listAsJson;
    // }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public String getAllCustomers() throws JsonProcessingException {
        List<Customer> customers = customerRepo.findAll();

        List<CustomerDto> dtoList = customers.stream().map(customer -> { 
            return autoSerialize(customer); 
        }).collect(Collectors.toList());
        String listAsJson = MapperUtil.toJson(dtoList); 
        System.out.println(listAsJson);
        printAll(dtoList);
        //System.out.println(MapperUtil.toJson(dtoList));
        return listAsJson;
    }


    public <T> void printAll(List<T> lst) {
        lst.stream().forEach(rec -> System.out.println(rec.toString()));
        lst.stream().forEach(rec -> ((CustomerDto) rec).getAddress().stream().forEach(rec1->System.out.println(rec1.toString())));
    }

    // load customer by id
    @GetMapping("/record/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable(value = "id") Long customerId)
            throws ResourceNotFound {
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new ResourceNotFound("Customer not found for this id :: " + customerId));
        ;
        return ResponseEntity.ok().body(customer);
    }
    
    // save customer
    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody String customerData) {
        Customer newCustomer;
       
        try {
            newCustomer = autoDeserialize(MapperUtil.fromJson(customerData));  
             
            Customer cust = customerRepo.save(newCustomer);
            List<Address> savedAddresses = new ArrayList<>();
            
            newCustomer.getAddress().forEach(address -> {
                address.setCustomer(cust);
                Address savedAddress = addressRepo.save(address);
                savedAddress.setCustomer(cust);
                savedAddresses.add(savedAddress);
            });
            cust.setAddress(savedAddresses);
            System.out.println("Record saved");
            System.out.println("ID is: " + cust.getID());
            System.out.println(cust);
             System.out.println(cust.getAddress());
            CustomerDto responedCustomer=autoSerialize(cust);

            return ResponseEntity.ok(responedCustomer);
            
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
      
    }


    // update customer
    @PutMapping("/update/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable(value = "id") Long customerId,@RequestBody String customerData) throws ResourceNotFound {
        try {
            Customer editedCustomer=autoDeserialize(MapperUtil.fromJson(customerData));
            
            Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new ResourceNotFound("Customer not found for this id :: " + customerId));

            customer.setID(editedCustomer.getID());
            customer.setName(editedCustomer.getName());
            customer.setSurname(editedCustomer.getSurname()); 

            editedCustomer.getAddress().forEach(address -> {
                addressRepo.save(address);

            });
            final Customer updatedCustomer = customerRepo.save(customer); 
            CustomerDto responedCustomer=autoSerialize(updatedCustomer);
            return ResponseEntity.ok(responedCustomer);
            

        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
       

        
       
        // customer.setAddress(customerData.getAddress());
        //

       
    }

    @RequestMapping(value = "/deleteCustomer/{id}", method = RequestMethod.DELETE)
    public Map<String, Boolean> deleteCustomer(@PathVariable(value = "id") Long customerId)
            throws ResourceNotFound {
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new ResourceNotFound("Customer not found for this id :: " + customerId));

        List<Address> address=addressRepo.findByCustomer_ID(customer.getID());
        address.forEach(addr -> {
            addressRepo.delete(addr);
        });

        customerRepo.delete(customer);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @RequestMapping(value = "/deleteAddress/{id}", method = RequestMethod.DELETE)
    public Map<String, Boolean> deleteAddress(@PathVariable(value = "id") Long addressId)
            throws ResourceNotFound {
        Address address = addressRepo.findById(addressId)
                .orElseThrow(() -> new ResourceNotFound("Customer not found for this id :: " + addressId));
        addressRepo.delete(address);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }




    public static Customer autoDeserialize(CustomerDto customer){
        
        Customer updaCustomer=MapperUtil.deserialize(customer);
        
        List<Address> addresses=MapperUtil.deserialize(customer.getAddress());
        updaCustomer.setAddress(addresses);
         System.out.println(updaCustomer);
        return updaCustomer;
    }

    public  CustomerDto autoSerialize(Customer customer){
            CustomerDto custDto = MapperUtil.serialize(customer);

            List<Address> addresses = addressRepo.findByCustomer_ID(custDto.getID());
            List<AddressDto> addrDtoList = MapperUtil.serialize(addresses); //TODO: Complete
            //System.out.println(addrDtoList);
            custDto.setAddress(addrDtoList);

            return custDto;
    }



}
