package daehun.jpa.hello.main.persistence;

import daehun.jpa.hello.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class PersistenceContextMain03 {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            // 조회 쿼리가 몇번 발생되는가?
            // 한번만 발생
            Member findMember = entityManager.find(Member.class, 101L);
            Member findMember2 = entityManager.find(Member.class, 101L);

            // 동일성 보장
            System.out.println(findMember == findMember2);  // true

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
