package hellojpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity  // jpa 를 사용하는 친구구나를 알리는 어노테이션
//@Table(name = "USER")  // 테이블 이름이 다를 경우
public class Member {

    @Id  // primary key는 알려야함
    private Long id;

//    @Column(name = "username")  // 필드 이름이 다를 경우
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
