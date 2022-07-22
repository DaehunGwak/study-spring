package study.querydsl.intermediate;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;
import study.querydsl.entity.QTeam;
import study.querydsl.entity.Team;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@SpringBootTest
@Transactional
public class IntermediateTestCore {

    @Autowired
    protected EntityManager em;

    protected JPAQueryFactory queryFactory;

    protected static final QMember qMember = QMember.member; // 기본 인스턴스, new QMember("m"); // 이름 지정 인스턴스 (같은 테이블 조인해야하는 경우만)
    protected static final QTeam qTeam = QTeam.team;

    @BeforeEach
    public void setUpEach() {
        // given
        queryFactory = new JPAQueryFactory(em);

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
}
