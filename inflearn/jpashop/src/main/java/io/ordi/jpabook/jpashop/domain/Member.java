package io.ordi.jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member", fetch = LAZY) // 연관관계 주인이 아님을 설정, Order의 대상 필드 변수 이름
    private List<Order> orders = new ArrayList<>();

    /*
        1. 쌩 엔티티
        2. 프록시
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Member member = (Member) o;
        return id.equals(member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
