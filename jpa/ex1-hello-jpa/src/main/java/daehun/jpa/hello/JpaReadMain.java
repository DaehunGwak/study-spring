package daehun.jpa.hello;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaReadMain {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            Member findMember = entityManager.find(Member.class, 1L);
            System.out.println("findMember.id = " + findMember.getId());
            System.out.println("findMember.id = " + findMember.getName());

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();
    }

}
