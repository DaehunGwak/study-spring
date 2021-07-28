package hellojpa.jpql.main;

import hellojpa.jpql.domain.Member;

import javax.persistence.*;
import java.util.List;

public class JpqlSyntaxMain {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
        EntityManager em = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            // 타입 정보가 명확한 케이스
            TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);

            // 반환 타입이 명확하지 않을 때 사용
            Query query1 = em.createQuery("select m.username, m.age from Member m");

            // 결과 여러개
            List<Member> resultList = query.getResultList();

            // 단일 결과
            Member singleResult = query.getSingleResult();

            // 파라미터 바인딩
            Member singleResult2 =
                    em.createQuery("select m from Member m where m.username = :username", Member.class)
                            .setParameter("username", "member1")
                            .getSingleResult();
            System.out.println(singleResult2);

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            em.close();
        }

        entityManagerFactory.close();
    }
}
