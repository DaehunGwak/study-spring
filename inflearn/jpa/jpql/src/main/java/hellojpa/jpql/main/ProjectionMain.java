package hellojpa.jpql.main;

import hellojpa.jpql.domain.Address;
import hellojpa.jpql.domain.Member;
import hellojpa.jpql.domain.MemberDto;
import hellojpa.jpql.domain.Team;

import javax.persistence.*;
import java.util.List;

public class ProjectionMain {

    private static EntityManager em;

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
        em = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

//            entityProjectionMain();
//            embeddedProjectionMain();
//            scalarProjectionMain();

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            em.close();
        }

        entityManagerFactory.close();
    }

    private static void entityProjectionMain() {
        // Entity
        // 영속성 관리 됨
        List<Member> resultMembers = em.createQuery("select m from Member m", Member.class).getResultList();
        Member findMember = resultMembers.get(0);
        findMember.setAge(20);

        // join 으로 나감... (묵시적 join)
        List<Team> resultTeam = em.createQuery("select m.team from Member m", Team.class).getResultList();
    }

    private static void embeddedProjectionMain() {
        // embedded
        List<Address> resultAdress = em.createQuery("select o.address from Order o", Address.class)
                .getResultList();
    }

    private static void scalarProjectionMain() {
        // scalar
        scalarUsingQuery();
        scalarUsingObjectArray();
        scalarUsingJpqlNew();
    }

    private static void scalarUsingQuery() {
        List resultList = em.createQuery("select m.age, m.username from Member m").getResultList();
        Object o  = resultList.get(0);
        Object[] result = (Object[]) o;
        System.out.println("age = " + result[0]);
        System.out.println("username = " + result[1]);
    }

    private static void scalarUsingObjectArray() {
        List<Object[]> resultList = em.createQuery("select m.age, m.username from Member m").getResultList();
        for (Object[] result : resultList) {
            System.out.println("age = " + result[0]);
            System.out.println("username = " + result[1]);
        }
    }

    private static void scalarUsingJpqlNew() {
        List<MemberDto> resultList = em.createQuery(
                "select new hellojpa.jpql.domain.MemberDto(m.username, m.age) from Member m",
                MemberDto.class).getResultList();
        for (MemberDto member : resultList) {
            System.out.println("username = " + member.getUsername());
            System.out.println("age = " + member.getAge());
        }
    }
}
