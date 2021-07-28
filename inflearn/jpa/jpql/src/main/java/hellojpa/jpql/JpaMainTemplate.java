package hellojpa.jpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMainTemplate {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
        EntityManager em = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {


            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            em.close();
        }

        entityManagerFactory.close();
    }
}
