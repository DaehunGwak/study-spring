package daehun.jpa.hello.mapping.basic.domain.manytomany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "MM_PRODUCT")
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue
    @Column(name = "PRODUCT_ID")
    private Long id;

    @ManyToMany(mappedBy = "products")
    private List<Member> members = new ArrayList<>();

}
