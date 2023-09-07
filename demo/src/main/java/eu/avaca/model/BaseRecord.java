package eu.avaca.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long ID;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

}
