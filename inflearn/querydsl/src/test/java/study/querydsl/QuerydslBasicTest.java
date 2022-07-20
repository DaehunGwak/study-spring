package study.querydsl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;
import study.querydsl.entity.QTeam;
import study.querydsl.entity.Team;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class QuerydslBasicTest {

    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;

    private static final QMember qMember = QMember.member; // 기본 인스턴스, new QMember("m"); // 이름 지정 인스턴스 (같은 테이블 조인해야하는 경우만)
    private static final QTeam qTeam = QTeam.team;

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
        // 컴파일 타임에 오류를 잡을 수 있음
        // prepared statements 에 자동으로 바인딩? (미리 DB에 해당 쿼리문을 컴파일해서 재사용하는 것)
        /**
         * https://dev.mysql.com/doc/refman/8.0/en/sql-prepared-statements.html
         * https://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html
         * https://diqmwl-programming.tistory.com/82
         */
        Member findMember = queryFactory.select(qMember)
                .from(qMember)
                .where(qMember.username.eq("member1"))
                .fetchOne(); // jpql 로 음

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    public void search() {
        Member findMember = queryFactory.selectFrom(qMember)
                .where(qMember.username.eq("member2")
                        .and(qMember.age.eq(20)))
                .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member2");
    }

    @Test
    public void searchAndParam() {
        // where 절에 , 로 and 로 표현 가능 (위의 방식이 의도 표현하기 좋다는 생각)
        // 동적 쿼리 만들때 아래 방식이 좋다는데?
        Member findMember = queryFactory.selectFrom(qMember)
                .where(qMember.username.eq("member2"), qMember.age.eq(20))
                .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member2");
    }

    /**
     * fetch()
     * fetchOne()
     * fetchFirst(): limit(1).fetchOne()
     * fetchResults(): 페이징 정보 포함, total count 쿼리 추가 실행 (카운트 쿼리, 일반 셀렉 쿼리 두개가 나감)
     * fetchCount(): 카운트 쿼리로 변경
     */
    @Test
    public void resultFetch() {
        List<Member> members = queryFactory.selectFrom(qMember)
                .fetch();

        Member member = queryFactory.selectFrom(qMember)
                .where(qMember.age.eq(30))
                .fetchOne();

        Member firstMember = queryFactory.selectFrom(qMember)
                .fetchFirst();

        /**
         * 단순 select 의 count 쿼리로의 변환은 지원하나 복잡한 쿼리(동적 쿼리, 서브 쿼리 등)의 지원은 안됨
         * https://www.inflearn.com/questions/23280?re_comment_id=156501
         *
         * <해결방안>
         *     1. native query (어느정도 단순할 때)
         *     2. fetch().count() (데이터 많음)
         *     3. Blaze-Persistence 활용 (공식 문서 추천, 외부 의존성이므로 성능 테스트 필요)
         */
        QueryResults<Member> memberQueryResults = queryFactory.selectFrom(qMember)
                .fetchResults(); // deprecated?
        memberQueryResults.getTotal();
        List<Member> membersFromFetchResults = memberQueryResults.getResults();

        long totalCount = queryFactory.selectFrom(qMember)
                .fetchCount(); // deprecated?
    }

    /**
     * 1. 나이 내림차숨
     * 2. 이름 올림 차순
     * 이름 없으면 마지막에 출력(nulls last)
     */
    @Test
    public void sort() {
        em.persist(new Member(null, 100));
        em.persist(new Member("member5", 100));
        em.persist(new Member("member6", 100));

        List<Member> findMembers = queryFactory.selectFrom(qMember)
                .where(qMember.age.eq(100))
                .orderBy(qMember.age.desc(), qMember.username.asc().nullsLast())
                .fetch();

        Member member5 = findMembers.get(0);
        Member member6 = findMembers.get(1);
        Member memberNull = findMembers.get(2);
        assertThat(member5.getUsername()).isEqualTo("member5");
        assertThat(member6.getUsername()).isEqualTo("member6");
        assertThat(memberNull.getUsername()).isNull();
    }

    @Test
    public void paging1() {
        List<Member> findMembers = queryFactory.selectFrom(qMember)
                .orderBy(qMember.username.desc())
                .offset(1)
                .limit(2)
                .fetch();

        assertThat(findMembers).hasSize(2);
    }

    @Test
    public void paging2() {
        QueryResults<Member> queryResults = queryFactory.selectFrom(qMember)
                .orderBy(qMember.username.desc())
                .offset(1)
                .limit(2)
                .fetchResults();

        assertThat(queryResults.getTotal()).isEqualTo(4);
        assertThat(queryResults.getLimit()).isEqualTo(2);
        assertThat(queryResults.getOffset()).isEqualTo(1);
        assertThat(queryResults.getResults()).hasSize(2);
    }

    @Test // 집합
    public void aggregation() throws Exception {
        // tuple?? -> DTO 로 직접 뽑기
        Tuple resultTuple = queryFactory.select(
                        qMember.count(),
                        qMember.age.sum(),
                        qMember.age.avg(),
                        qMember.age.min(),
                        qMember.age.max())
                .from(qMember)
                .fetchOne();

        assertThat(resultTuple.get(qMember.count())).isEqualTo(4);
        assertThat(resultTuple.get(qMember.age.sum())).isEqualTo(100);
        assertThat(resultTuple.get(qMember.age.avg())).isEqualTo(25);
        assertThat(resultTuple.get(qMember.age.min())).isEqualTo(10);
        assertThat(resultTuple.get(qMember.age.max())).isEqualTo(40);
    }

    /**
     * 팀 이름과 각 팀 평균 연령을 구해라
     */
    @Test
    public void groupBy() throws Exception {
        // when
        List<Tuple> results = queryFactory.select(qTeam.name, qMember.age.avg())
                .from(qMember)
                .join(qMember.team, qTeam)
                .groupBy(qTeam)
                .fetch();

        // then
        Tuple teamAResult = results.get(0);
        Tuple teamBResult = results.get(1);
        assertThat(teamAResult.get(qTeam.name)).isEqualTo("teamA");
        assertThat(teamAResult.get(qMember.age.avg())).isEqualTo(15);
        assertThat(teamBResult.get(qTeam.name)).isEqualTo("teamB");
        assertThat(teamBResult.get(qMember.age.avg())).isEqualTo(35);
    }

    /**
     * team A 에 소속된 모든 회원
     */
    @Test
    public void join() {
        /**
         * [jpql]
         * select
         *         member1
         *     from
         *         Member member1
         *     inner join
         *         member1.team as team
         *     where
         *         team.name = ?1
         * [native]
         * select
         *        member0_.member_id as member_i1_1_,
         *        member0_.age as age2_1_,
         *        member0_.team_id as team_id4_1_,
         *        member0_.username as username3_1_
         * from
         *        member member0_
         * inner join
         *        team team1_
         *        on member0_.team_id = team1_.team_id
         * where
         *        team1_.name =?
         */
        List<Member> teamAMembers = queryFactory.selectFrom(qMember)
                .join(qMember.team, qTeam)
                .where(qTeam.name.eq("teamA"))
                .fetch();

        /**
         * [jpql]
         * select
         *         member1
         *     from
         *         Member member1
         *     where
         *         member1.team.name = ?1
         * [native]
         * select
         *             member0_.member_id as member_i1_1_,
         *             member0_.age as age2_1_,
         *             member0_.team_id as team_id4_1_,
         *             member0_.username as username3_1_
         *         from
         *             member member0_ cross  <- cartesian product (곱집합, 막조인?)
         *         join
         *             team team1_
         *         where
         *             member0_.team_id=team1_.team_id
         *             and team1_.name=?
         */
//        List<Member> teamAMembers = queryFactory.selectFrom(qMember)
//                .where(qMember.team.name.eq("teamA"))
//                .fetch();

        assertThat(teamAMembers).hasSize(2);
    }
}
