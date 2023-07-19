package eu.boot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eu.avaca.model.Address;
import eu.avaca.model.BaseRecord;
import eu.avaca.repositories.AddressRepository;
import eu.boot.exception.ResourceNotFound;

@RestController
@RequestMapping("/address")
public class AddressController<T extends BaseRecord>
{
    @Autowired
    AddressRepository addressRepo ;

    public AddressController(){}


    //load all custommers
    @GetMapping("address")
    public List<Address> getAllAddress()
    {
        List<Address> lst = (List<Address>) addressRepo.findAll();
        printAll((List<T>) lst);
        return lst;
    }

    
    public void printAll(List<T> lst)
    {
        lst.stream().forEach(rec -> System.out.println(rec.toString()));
    }

    //load customer by id
    @GetMapping("/address/{id}")
	public ResponseEntity<Address> getCustomerById(@PathVariable(value = "id") Long addressId) throws ResourceNotFound {
		Address address = addressRepo.findById(addressId).orElseThrow(() -> new ResourceNotFound("Address not found for this id :: " + addressId));;
		return ResponseEntity.ok().body(address);
	}

    //save customer
    @PostMapping("/save")
	public Address createCustomer( @RequestBody Address address) {
		return addressRepo.save(address);
	}

    //update customer 
    @PutMapping("/address/{id}")
	public ResponseEntity<Address> updateCustomer(@PathVariable(value = "id") Long addressId,@RequestBody Address addressData) throws ResourceNotFound {
		Address address = addressRepo.findById(addressId).orElseThrow(() -> new ResourceNotFound("Address not found for this id :: " + addressId));
        
        
        address.setID(addressData.getID());
		address.setCity(addressData.getCity());
		address.setStreet(addressData.getStreet());
        //address.setCustomer(addressData.getCustomer());
		final Address updatedAddress = addressRepo.save(address);
		return ResponseEntity.ok(updatedAddress);
	}

    @GetMapping("/addresses/{customer_fk}")
    public List<Address> getAddressesByCustomer(@PathVariable(value = "customer_fk") Long customerFk) {
        List<Address> addresses = addressRepo.findByCustomer_ID(customerFk);
        return addresses;
    }

    @DeleteMapping("/address/{id}")
	public Map<String, Boolean> deleteCustomer(@PathVariable(value = "id") Long addressId)throws ResourceNotFound{
		Address address = addressRepo.findById(addressId).orElseThrow(() -> new ResourceNotFound("Customer not found for this id :: " + addressId));

		addressRepo.delete(address);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
