package daehun.jpa.hello.mapping.basic.domain.manytomany.refactor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "R_MEMBER_PRODUCT")
@Getter
@Setter
@NoArgsConstructor
public class MemberProduct {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    private Integer count;

    private LocalDateTime orderDateTime;

}
