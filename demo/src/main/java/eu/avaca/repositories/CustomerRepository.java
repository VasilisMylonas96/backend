package eu.avaca.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.avaca.model.Customer;

@Repository("customerRepository")
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findBySurname(String surname);

    Customer findById(long id);
    
}
