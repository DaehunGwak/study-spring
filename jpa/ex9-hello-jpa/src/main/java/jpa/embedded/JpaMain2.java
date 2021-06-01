package jpa.embedded;

import jpa.embedded.Address;
import jpa.embedded.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain2 {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {

            Address address = new Address();
            address.setCity("oldCity");

            Member member1 = new Member();
            member1.setName("1");
            member1.setHomeAddress(address);

            Member member2 = new Member();
            member2.setName("2");

            // 2. 이렇게 카피로 쓰면 바뀔일 없음
            member2.setHomeAddress(new Address(address.getCity(), address.getStreet(), address.getZipcode()));

            // 1.member1, member2 둘다 바뀌게 됨 ㅠ
            member1.getHomeAddress().setCity("newCity");

            entityManager.persist(member1);
            entityManager.persist(member2);

            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();
    }
}
