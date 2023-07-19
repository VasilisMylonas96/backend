package eu.avaca.model;

import java.util.ArrayList;


import java.util.List;




import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity  
@Table(name = "customer")
public class Customer extends BaseRecord {
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "surname")
    private String surname;

    @Transient
    private List<Address> address=new ArrayList<>();
  


    public Customer() {}

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    public void addAddress(Address address) {
        if (address == null) {
            throw new NullPointerException("Can't add null address");
        }
        if (address.getCustomer() != null) {
            throw new IllegalStateException("Address is already assigned to a Customer");
        }
        getAddress().add(address);
        address.setCustomer(this);
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("id\t name\t\tsurname\n");
        buf.append(this.getID() + "\t");
        buf.append(this.name + "\t");
        buf.append(this.surname + "\n\n");
        return buf.toString();
    }

}
