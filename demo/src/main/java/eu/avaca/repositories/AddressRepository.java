package eu.avaca.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.avaca.model.Address;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;

@NamedQueries(value = 
{
    @NamedQuery(
        name="addressListByCustomer",
        query="select cust from Address addr.customer cust WHERE cust.ID = :custID"
    )
})
@Repository("AddressRepository")
public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByID(Long id);

    Address findById(long id);

    List<Address> findByCustomer_ID(Long ID);
}
