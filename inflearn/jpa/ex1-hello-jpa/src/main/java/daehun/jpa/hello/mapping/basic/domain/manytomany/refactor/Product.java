package daehun.jpa.hello.mapping.basic.domain.manytomany.refactor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "R_PRODUCT")
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue
    @Column(name = "PRODUCT_ID")
    private Long id;

    @OneToMany(mappedBy = "product")
    private List<MemberProduct> memberProducts = new ArrayList<>();

}
