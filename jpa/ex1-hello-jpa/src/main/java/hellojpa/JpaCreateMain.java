package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaCreateMain {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // jpa는 트랜잭션을 지정해주지 않으면 실행되지 않음
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // spring 에서 해당 관리를 해주게 되서 try 를 쓰지 않아도 되긴하지만
        // 지금은 학습용
        try {
            Member member = new Member();
            member.setId(3L);
            member.setName("hello C!C");

            entityManager.persist(member);

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();
    }

}
