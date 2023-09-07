package eu.avaca.model;

import java.util.ArrayList;


import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity  
@Table(name = "customer")
public class Customer extends BaseRecord {
      



    @Column(name = "content")
    @Lob
    public byte[] content ;
    @Column(name = "file_name")
    private String fileName;




    

    @Column(name = "name")
    private String name;
    
    @Column(name = "surname")
    private String surname;

    @Transient
    private List<Address> address=new ArrayList<>();
  
    public byte[] getContent() {
        return content;
    }
    public void setContent(byte[] content) {
        this.content = content;
    }



    public Customer() {}

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

     public String getFileName() {
        return fileName;
    }



    public List<Address> getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }
     public void setFileName(String fileName) {
        this.fileName = fileName;
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

//file

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
