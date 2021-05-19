package daehun.jpa.hello.mapping.basic.domain.onetomany.one;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class OneToManyMain {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {

            Member member = new Member();
            member.setUsername("member1");
            entityManager.persist(member);

            Team team = new Team();
            team.setName("teamA");
            // team을 수정했는데 MEMBER 테이블이 업데이트 됨
            // 업데이트 쿼리 하나 더 나가게됨 (성능상 이슈)
            team.getMembers().add(member);

            entityManager.persist(team);

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();
    }
}
