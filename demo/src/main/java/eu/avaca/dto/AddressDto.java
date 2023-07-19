package eu.avaca.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Transient;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressDto extends BaseRecordDto {

    public String street ;
    
    public String city ;

    public CustomerDto customer ;
    
    @Transient
    public String reduxID ;
    
    public AddressDto(){


    }
    public CustomerDto getCustomer(){
        return customer;
    }
    public String getStreet() {
        return street;
    }
    public String getCity() {
        return city;
    }

    public void setCustomer(CustomerDto customerDto){
        this.customer=customerDto;
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
