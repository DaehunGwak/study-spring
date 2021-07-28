package daehun.jpa.hello.main.entity;

import daehun.jpa.hello.entity.MemberIdentity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class IdentityMain {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {

            MemberIdentity member = new MemberIdentity();
            member.setName("asdf");

            System.out.println("-------------");
            entityManager.persist(member);
            System.out.println("member.id = " + member.getId());
            System.out.println("-------------");

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();
    }
}
