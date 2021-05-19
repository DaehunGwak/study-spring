package daehun.jpa.hello.mapping.basic.main;

import daehun.jpa.hello.mapping.basic.domain.manytoone.oneway.Member;
import daehun.jpa.hello.mapping.basic.domain.manytoone.oneway.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class ManyToOneMain {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {

            Team team = new Team();
            team.setName("TeamA");
            entityManager.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setTeam(team); // 객체 지향으로 개선
            entityManager.persist(member);

            entityManager.flush(); // 영속성 컨텍스트 쿼리 flush
            entityManager.clear(); // 영속성 컨텍스트 1차캐시 초기화

            Member findMember = entityManager.find(Member.class, member.getId()); // 조인 쿼리가 알아서 나감
            Team findTeam = findMember.getTeam();
            System.out.println(findTeam.toString());

            // 연관관계 수정
            // Team newTeam = entityManager.find(Team.class, 100L);
            // findMember.setTeam(newTeam);

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();
    }
}
