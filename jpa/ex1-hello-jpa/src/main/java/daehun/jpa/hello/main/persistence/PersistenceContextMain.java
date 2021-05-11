package daehun.jpa.hello.main.persistence;

import daehun.jpa.hello.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class PersistenceContextMain {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            // 비영속 (new/trasient)
            Member member = new Member();
            member.setId(5L);
            member.setName("diffff name");

            // 영속 (managed)
            System.out.println("==== BEFORE ====");
            entityManager.persist(member);
            System.out.println("==== AFTER ====");

            // 준영속 (detached)
            // 이렇게 되면 준영속 상태라 저장이 안됨 (쿼리가 전송되지 않음)
            entityManager.detach(member);

            // 삭제 (removed)
            // 준영속은 쿼리가 안날라가지만, 삭제는 쿼리가 날라감
            entityManager.remove(member);

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
