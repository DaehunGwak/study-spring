package hellojpa.jpql.main;

import hellojpa.jpql.domain.Member;

import javax.persistence.*;
import java.util.List;

public class PagingMain {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
        EntityManager em = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {

            for (int i = 0; i < 100; i++) {
                Member member = new Member();
                member.setUsername("member" + i);
                member.setAge(i);
                em.persist(member);
            }
            em.flush();
            em.clear();

            List<Member> resultList = em.createQuery("select m from Member m order by m.age desc", Member.class)
                    .setFirstResult(10)
                    .setMaxResults(10)
                    .getResultList();

            System.out.println("size: " + resultList.size());
            for (Member member : resultList) {
                System.out.println(member);
            }

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            em.close();
        }

        entityManagerFactory.close();
    }
}
