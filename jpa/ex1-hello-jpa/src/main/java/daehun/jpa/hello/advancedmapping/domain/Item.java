package daehun.jpa.hello.advancedmapping.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "AM_ITEM")
@Inheritance(strategy = InheritanceType.JOINED)
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 기본 타입
@DiscriminatorColumn // DTYPE 컬럼 생성
@Getter
@Setter
@NoArgsConstructor
public class Item {

    @Id @GeneratedValue
    private Long id;

    private String name;
    private Long price;

}
