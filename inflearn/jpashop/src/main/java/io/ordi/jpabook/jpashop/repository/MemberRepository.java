package io.ordi.jpabook.jpashop.repository;

import io.ordi.jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
@Transactional(readOnly = true)
public class MemberRepository {

    private final EntityManager em;

    @Transactional
    public void save(Member member) {
        em.persist(member); // 영속성 컨텍스트 등록
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id); // 단건 조회
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList(); // jpql 을 활용한 전체 조회
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name" , Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
