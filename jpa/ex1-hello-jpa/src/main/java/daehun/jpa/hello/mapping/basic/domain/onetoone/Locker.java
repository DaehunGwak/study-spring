package daehun.jpa.hello.mapping.basic.domain.onetoone;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "ONETOONE_LOCKER")
@Getter
@Setter
@NoArgsConstructor
public class Locker {

    @Id
    @GeneratedValue
    @Column(name = "LOCKER_ID")
    private Long id;

    private String name;

    @OneToOne(mappedBy = "locker")
    private Member member;

}
