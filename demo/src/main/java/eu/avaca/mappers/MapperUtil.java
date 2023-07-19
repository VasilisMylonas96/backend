package eu.avaca.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import eu.avaca.dto.AddressDto;
import eu.avaca.dto.CustomerDto;
import eu.avaca.model.Address;
import eu.avaca.model.Customer;
import eu.avaca.repositories.AddressRepository;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

public class MapperUtil {
    
    @Autowired
    AddressRepository addressRepo;
    
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static CustomerDto serialize(Customer customer) {

        CustomerDto dto = new CustomerDto();
        dto.setID(customer.getID());
        dto.setName(customer.getName());
        dto.setSurname(customer.getSurname());

        // List<AddressDto> addressDtos = new ArrayList<>();
        // List<Address> addresses = customer.getAddress();
        // if (addresses != null) {
        //     for (Address address : addresses) {
        //         AddressDto addressDto = new AddressDto();
        //         addressDto.setID(address.getID());
        //         addressDto.setStreet(address.getStreet());
        //         addressDto.setCity(address.getCity());
        //         addressDto.setCustomer(dto);
        //         addressDtos.add(addressDto);
        //     }
        // }

        // dto.setAddress(addressDtos);

        return dto;
    }


        public static AddressDto serialize(Address address){
                AddressDto addressDto = new AddressDto();
                addressDto.setID(address.getID());
                addressDto.setStreet(address.getStreet());
                addressDto.setCity(address.getCity());
                addressDto.setCustomer(serialize(address.getCustomer()));
                return addressDto;
        };
        

    public static List<AddressDto> serialize( List<Address> address){
        List<AddressDto> addr= address.stream().map(addrRec -> {
                AddressDto addressDto = serialize(addrRec);
                return addressDto;
        }).collect(Collectors.toList());
        
        return addr;
    }

    public  CustomerDto autoSerialize(Customer customer){
            CustomerDto custDto = MapperUtil.serialize(customer);
            List<Address> addresses = addressRepo.findByCustomer_ID(custDto.getID());
            List<AddressDto> addrDtoList = MapperUtil.serialize(addresses); //TODO: Complete
            //System.out.println(addrDtoList);
            custDto.setAddress(addrDtoList);

            return custDto;
    }







    public static Customer deserialize(CustomerDto dto) {
        Customer customer = new Customer();
        
        customer.setID(dto.getID());
        customer.setName(dto.getName());
        customer.setSurname(dto.getSurname());
        return customer;
    }

    public static Address deserialize(AddressDto addressdto){
            System.out.println(addressdto);
            Address address = new Address();
            address.setID(addressdto.getID());
            address.setStreet(addressdto.getStreet());
            address.setCity(addressdto.getCity());
            address.setCustomer(deserialize(addressdto.getCustomer()));
        return address;
    } 

    public static List<Address> deserialize( List<AddressDto> addressDto){
        List<Address> address= addressDto.stream().map(addrRec -> {
        Address addr = deserialize(addrRec);
        return addr;
        }).collect(Collectors.toList());
        return address;

    }



    public static String toJson(List<CustomerDto> object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public static CustomerDto fromJson(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, new TypeReference<CustomerDto>() {
        });
    }
}
