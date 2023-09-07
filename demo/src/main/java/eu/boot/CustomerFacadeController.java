package eu.boot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
import jakarta.transaction.Transactional;

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

    // @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    // public String getAllCustomers() throws JsonProcessingException {
    //     List<Customer> customers = customerRepo.findAll();
       
    //     List<CustomerDto> dtoList = customers.stream().map(customer -> { 
            
    //         return autoSerialize(customer); 
    //     }).collect(Collectors.toList());
    //     String listAsJson = MapperUtil.toJson(dtoList); 
    //    // System.out.println(listAsJson);
    //     //printAll(dtoList);
    //     //System.out.println(MapperUtil.toJson(dtoList));
    //     System.out.println(dtoList);
    //     return listAsJson;
    // }
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<Object[]> getAllCustomers() throws JsonProcessingException {
        List<Customer> customers = customerRepo.findAll();
        List<Object[]> responseMap = new ArrayList<>();
        for (Customer customer : customers) {
            Object[] customerArray = new Object[3]; // Τρία στοιχεία: customerJson, customerContent, filename

            CustomerDto responedCustomer = autoSerialize(customer);
            customerArray[0] = MapperUtil.toJson(responedCustomer);
    
            // Convert LOB data to Base64
            byte[] content = customer.getContent();
            customerArray[1] = content;
    
            customerArray[2] = customer.getFileName();
    
            responseMap.add(customerArray);
            }

        return responseMap;
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
 @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<List<Object>> createCustomer(
    @RequestPart("customerData") String customerData,
    @RequestPart("file_0") MultipartFile file0){
        Customer newCustomer;
        
       
        
        try {
            newCustomer = autoDeserialize(MapperUtil.fromJson(customerData));  
           
            byte[] fileBytes = file0.getBytes();
            String originalFilename = file0.getOriginalFilename();
             newCustomer.setContent(fileBytes);
            newCustomer.setFileName(originalFilename);
            Customer cust = customerRepo.save(newCustomer);

            List<Address> savedAddresses = new ArrayList<>();
            System.out.println(cust.getContent());
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
            System.out.println(cust.content);

            List<Object> customerArray = new ArrayList<>();
            CustomerDto responedCustomer = autoSerialize(cust);
            customerArray.add(MapperUtil.toJson(responedCustomer));
            customerArray.add(cust.getContent());
            customerArray.add(cust.getFileName());
    
           
          
            return ResponseEntity.ok(customerArray);
            
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
      
    }


    // update customer
    @PutMapping("/update/{id}")
    public ResponseEntity<List<Object>> updateCustomer(
        @PathVariable(value = "id") Long customerId, 
        @RequestPart("customerData") String customerData,
        @RequestPart(name = "file_0", required = false) MultipartFile file0) throws ResourceNotFound {
       try {
            Customer editedCustomer=autoDeserialize(MapperUtil.fromJson(customerData));
            
            Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new ResourceNotFound("Customer not found for this id :: " + customerId));


            customer.setName(editedCustomer.getName());
            customer.setSurname(editedCustomer.getSurname()); 
            if (file0 != null) {
                try {
                    byte[] fileBytes = file0.getBytes();
                    String originalFilename = file0.getOriginalFilename();
                    customer.setContent(fileBytes);
                    customer.setFileName(originalFilename);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            customer.getAddress().clear();
            List<Address> savedAddresses = new ArrayList<>();
            
            editedCustomer.getAddress().forEach(address -> {
                address.setCustomer(customer);
                Address savedAddress = addressRepo.save(address);
                savedAddress.setCustomer(customer);
                savedAddresses.add(savedAddress);
            });
             customer.setAddress(savedAddresses); 
            final Customer updatedCustomer = customerRepo.save(customer); 
            List<Object> responedCustomer = new ArrayList<>();
            CustomerDto  customerArray= autoSerialize(updatedCustomer);
            responedCustomer.add(MapperUtil.toJson(customerArray));
            responedCustomer.add(updatedCustomer.getContent());
            responedCustomer.add(updatedCustomer.getFileName());
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
        // System.out.println(updaCustomer);
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
