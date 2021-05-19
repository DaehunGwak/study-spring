package daehun.jpa.hello.mapping.basic.main;

import daehun.jpa.hello.mapping.basic.domain.manytoone.bidirectional.Member;
import daehun.jpa.hello.mapping.basic.domain.manytoone.bidirectional.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class BidrectionalMain {

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
            member.setTeam(team);
            entityManager.persist(member);

            entityManager.flush();
            entityManager.clear();

            Member findMember = entityManager.find(Member.class, member.getId());
            Team findTeam = findMember.getTeam();
            List<Member> members = findTeam.getMembers(); // 양방향 테스트

            for (Member m : members) {
                System.out.println(m.getUsername());
            }

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
