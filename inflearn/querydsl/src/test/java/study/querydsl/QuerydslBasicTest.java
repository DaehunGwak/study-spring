package study.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;
import study.querydsl.entity.Team;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class QuerydslBasicTest {

    @Autowired
    EntityManager em;

    @BeforeEach
    public void setUpEach() {
        // given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);
    }

    @Test
    public void startJPSQL() {
        // 실제 오류 발생 시점은 런타임 (호출했을 때)
        Member findMember = em.createQuery("select m from Member m where m.username = :username", Member.class)
                .setParameter("username", "member1")
                .getSingleResult();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    public void startQuerydsl() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QMember m = QMember.member; // 앞은 기본 인스턴스, new QMember("m"); // 이름 지정 인스턴스 (같은 테이블 조인해야하는 경우만)

        // 컴파일 타임에 오류를 잡을 수 있음
        // prepared statements 에 자동으로 바인딩? (미리 DB에 해당 쿼리문을 컴파일해서 재사용하는 것)
        /**
         * https://dev.mysql.com/doc/refman/8.0/en/sql-prepared-statements.html
         * https://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html
         * https://diqmwl-programming.tistory.com/82
         */
        Member findMember = queryFactory.select(m)
                .from(m)
                .where(m.username.eq("member1"))
                .fetchOne(); // jpql 로 음

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }
}
