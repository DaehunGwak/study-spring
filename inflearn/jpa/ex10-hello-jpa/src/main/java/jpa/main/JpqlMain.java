package jpa.main;

import jpa.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpqlMain {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
        EntityManager em = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {

            String qlString = "select m from Member m where m.name like '%kim%'";
            List<Member> resultList = em.createQuery(qlString, Member.class)
                    .getResultList();

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            em.close();
        }

        entityManagerFactory.close();
    }
}
