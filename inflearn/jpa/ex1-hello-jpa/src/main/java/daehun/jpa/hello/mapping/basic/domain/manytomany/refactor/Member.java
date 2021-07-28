package daehun.jpa.hello.mapping.basic.domain.manytomany.refactor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "R_MEMBER")
@Getter
@Setter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String username;

    @OneToMany(mappedBy = "member")
    private List<Product> products = new ArrayList<>();

}
