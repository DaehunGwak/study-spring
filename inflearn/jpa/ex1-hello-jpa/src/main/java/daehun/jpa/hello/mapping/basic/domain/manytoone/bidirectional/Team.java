package daehun.jpa.hello.mapping.basic.domain.manytoone.bidirectional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "BI_TEAM")
@Getter
@Setter
@NoArgsConstructor
public class Team {

    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team") // 변수명 기입
    private List<Member> members = new ArrayList<>();

    /** 가급적 한쪽에만 편의 메서드를 구현하자 */
    public void addMember(Member member) {
        member.setTeam(this);
        members.add(member);
    }
}
