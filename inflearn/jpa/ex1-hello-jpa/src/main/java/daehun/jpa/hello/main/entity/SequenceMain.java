package daehun.jpa.hello.main.entity;

import daehun.jpa.hello.entity.MemberIdentity;
import daehun.jpa.hello.entity.MemberSequence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class SequenceMain {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {

            MemberSequence member1 = new MemberSequence();
            member1.setName("A");
            MemberSequence member2 = new MemberSequence();
            member2.setName("B");
            MemberSequence member3 = new MemberSequence();
            member3.setName("C");

            System.out.println("-------------");

            entityManager.persist(member1);
            System.out.println("member1.id = " + member1.getId());

            entityManager.persist(member2);
            System.out.println("member2.id = " + member2.getId());

            entityManager.persist(member3);
            System.out.println("member3.id = " + member3.getId());

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
