package study.querydsl.basic;

import com.querydsl.core.Tuple;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import study.querydsl.entity.Member;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class QuerydslBasicJoinTest extends QuerydslBasicTestCore {

    /**
     * team A 에 소속된 모든 회원
     */
    @Test
    public void join() {
        /*
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

        /*
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

    /**
     * 회원과 팀을 조인하면서, 팀 이름이 teamA인 팀만 조인, 회원은 모두 조회
     * jpql: select m, t from Member m left join m.team t on t.name = 'teamA'
     */
    @Test
    public void joinOnFiltering() {
        // left join 이 필요할 때만
        List<Tuple> results = queryFactory.select(qMember, qTeam)
                .from(qMember)
                .leftJoin(qMember.team, qTeam)
                .on(qTeam.name.eq("teamA"))
                .fetch(); // 4건 (teamA 인 Member 2명 + 나머지 team이 포함 안된 member 2명)

        // inner join 으로만 필터링 할거라면
//        List<Tuple> results = queryFactory.select(qMember, qTeam)
//                .from(qMember)
//                .join(qMember.team, qTeam)
//                .where(qTeam.name.eq("teamA"))
//                .fetch(); // 2건 (teamA 인 Member 2명)

        results.forEach(result -> log.info("{}", result));
    }

    /**
     * 연관관계가 없는 외부 조인
     * 회원의 이름이 팀 이름과 같은 대상 외부 조인
     */
    @Test
    public void joinOnNoRelation() {
        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));
        em.persist(new Member("teamC"));

        List<Tuple> results = queryFactory.select(qMember, qTeam)
                .from(qMember)
                .leftJoin(qTeam) // 이렇게 파라미터로 하나만 들어감
                .on(qMember.username.eq(qTeam.name))
                .fetch();

        results.forEach(result -> log.info("{}", result));
    }

    @PersistenceUnit
    EntityManagerFactory emf;

    @Test
    public void fetchJoinNo() throws Exception {
        // when
        em.flush();
        em.clear();
        Member findMember = queryFactory.selectFrom(qMember)
                .where(qMember.username.eq("member1"))
                .fetchOne();

        // then
        assertThat(emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam()))
                .as("패치 조인 미적용 시 로드 안됨을 확인")
                .isFalse();
    }

    @Test
    public void fetchJoin() throws Exception {
        // when
        em.flush();
        em.clear();
        Member findMember = queryFactory.selectFrom(qMember)
                .join(qMember.team, qTeam)
                .fetchJoin()
                .where(qMember.username.eq("member1"))
                .fetchOne();

        // then
        assertThat(emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam()))
                .as("패치 조인 미적용 시 로드 안됨을 확인")
                .isTrue();
    }
}
