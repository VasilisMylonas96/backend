package eu.avaca.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity 
@Table(name="address") 
public class Address extends BaseRecord {

    @Column(name="street")
    public String street ;
    
    @Column(name="city")
    public String city ;

    
    @ManyToOne
    @JoinColumn(name = "customer_fk")
    public Customer customer ;

    public Address(){}
    
    public Customer getCustomer(){
        return customer;
    }
    public String getStreet() {
        return street;
    }
    public String getCity() {
        return city;
    }

    public void setCustomer(Customer cust){
        this.customer=cust;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public void setCity(String city) {
        this.city = city;
    }
   
    public String toString(){
        return "id\tcity\tstreet\tcustomer_fk\n"+ this.getID() +"\t"+ this.city + "\t" + this.street +"\t"+"\n"; 
    }
}
