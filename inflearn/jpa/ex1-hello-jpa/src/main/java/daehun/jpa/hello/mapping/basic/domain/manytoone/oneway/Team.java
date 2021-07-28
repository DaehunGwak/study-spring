package daehun.jpa.hello.mapping.basic.domain.manytoone.oneway;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "T_TEAM")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Team {

    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;
}
