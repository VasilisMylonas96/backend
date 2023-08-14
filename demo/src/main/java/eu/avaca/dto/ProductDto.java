package eu.avaca.dto;

public class ProductDto extends BaseRecordDto{
    
    String name ;
    String code;
    Double price ;

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public Double getPrice() {
        return price;
    }

    public ProductDto(){}

    
}
