package hellojpa.jpql.main;

import hellojpa.jpql.domain.Member;
import hellojpa.jpql.domain.Team;

import javax.persistence.*;
import java.util.List;

public class JoinMain {

    private static EntityManager em;

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
        em = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member10");
            member.setAge(10);
            member.changeTeam(team);
            em.persist(member);
            em.flush();
            em.clear();

//            innerJoin();
//            outerJoin();
            joinFiltering();

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            em.close();
        }

        entityManagerFactory.close();
    }

    private static void innerJoin() {
        String query = "select m from Member m inner join m.team t";
        List<Member> resultList = em.createQuery(query, Member.class).getResultList();
        System.out.println(resultList.size());
    }

    private static void outerJoin() {
        String query = "select m from Member m left join m.team t";
        List<Member> resultList = em.createQuery(query, Member.class).getResultList();
        for (Member resultMember : resultList) {
            System.out.println(resultMember.getId() + " " + resultMember.getUsername() + " " + resultMember.getAge());
        }
    }

    private static void joinFiltering() {
        String query = "select m from Member m left join m.team t on t.name='teamA'";
        List<Member> resultList = em.createQuery(query, Member.class).getResultList();
        System.out.println(resultList.size());
    }
}
