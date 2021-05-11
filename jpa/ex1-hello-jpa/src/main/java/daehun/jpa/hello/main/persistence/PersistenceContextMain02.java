package daehun.jpa.hello.main.persistence;

import daehun.jpa.hello.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class PersistenceContextMain02 {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            // 비영속 (new/trasient)
            Member member = new Member();
            member.setId(101L);
            member.setName("HelloJPA");

            // 영속 (managed)
            System.out.println("==== BEFORE ====");
            entityManager.persist(member);
            System.out.println("==== AFTER ====");

            Member findMember = entityManager.find(Member.class, 101L);

            // 조회 쿼리가 발생할까?
            // 조회 쿼리가 발생안함 (1차 캐시에 있기 때문)
            System.out.println(findMember.getId() + findMember.getName());

            // 쿼리 발생 시점
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();
    }

}
