package jpa.collection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "C_ADDRESS")
@Getter
@Setter
@NoArgsConstructor
public class AddressEntity {

    @Id @GeneratedValue
    private Long id;

    private Address address;

    public AddressEntity(String city, String street, String zipcode) {
        address = new Address(city, street, zipcode);
    }
}
