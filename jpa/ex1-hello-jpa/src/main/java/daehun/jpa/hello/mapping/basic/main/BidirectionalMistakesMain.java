package daehun.jpa.hello.mapping.basic.main;

import daehun.jpa.hello.mapping.basic.domain.manytoone.bidirectional.Member;
import daehun.jpa.hello.mapping.basic.domain.manytoone.bidirectional.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class BidirectionalMistakesMain {

    public static EntityManager entityManager;

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
        entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            setTeam();
            flush();



            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();
    }

    /** 양방향 매핑시 연관관계의 주인에 값을 입력해야 한다. */
    private static void setTeam() {
        Team team = new Team();
        team.setName("Team BBBB");
        entityManager.persist(team);

        Member member = new Member();
        member.setUsername("member1");
        member.changeTeam(team);
//        member.setTeam(team);
//        team.getMembers().add(member); // 여기도 추가해줘야 함 (영속성 컨텍스트에서는 반영이 안되어 있는 상태)
        entityManager.persist(member);

        team.addMember(member);

        Team findTeam = entityManager.find(Team.class, team.getId()); // 1차 캐시에 잡혀 있음
        List<Member> members = findTeam.getMembers();

        System.out.println("===========");
        for (Member m : members) {
            System.out.println(m.getUsername());
        }
        System.out.println("===========");
    }

    private static void flush() {
        entityManager.flush();
        entityManager.clear();
    }
}
