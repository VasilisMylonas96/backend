package eu.avaca.dto;

import java.util.ArrayList;



import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Lob;


public class CustomerDto extends BaseRecordDto
{
      
    private List<byte[]> content;
    private String name;
    
    private String surname;

    private List<AddressDto> address=new ArrayList<>();

    public CustomerDto() {}
   @JsonIgnore
    public List<byte[]> getContent() {
        return content;
    }

    public void setContent(List<byte[]> content) {
        this.content = content;
    }
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public List<AddressDto> getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setAddress(List<AddressDto> address) {
        this.address = address;
    }

    public void addAddress(AddressDto address) {
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
        buf.append(this.content + "\n\n");
        return buf.toString();
    }
}
