package study.querydsl.basic;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class QuerydslBasicSubQueryTest extends QuerydslBasicTestCore {

    /**
     * 나이가 가장 많은 회원 조회
     *
     * jpa subquery 의 한계: from 절의 서브 쿼리는 안됨 (하이버네이트에서 select 절의 서브쿼리를 지원)
     * (Book: SQL AntiPatterns)
     *
     * from 절 서브쿼리 해결방안
     *  - 서브쿼리를 조인으로 변경
     *  - 쿼리를 분리
     *  - nativeSQL
     **/
    @Test
    public void subQuery() throws Exception {
        QMember qMemberSub = new QMember("memberSub");

        // when
        List<Member> results = queryFactory.selectFrom(qMember)
                .where(qMember.age.eq(
                        JPAExpressions.select(qMemberSub.age.max())
                                .from(qMemberSub)
                ))
                .fetch();

        // then
        results.forEach(member -> log.info("{}", member));
        assertThat(results).extracting("age")
                .containsExactly(40);
    }

    /**
     * 나이가 평균 이상인 회원 조회
     */
    @Test
    public void subQueryGoe() throws Exception {
        QMember qMemberSub = new QMember("memberSub");

        // when
        List<Member> results = queryFactory.selectFrom(qMember)
                .where(qMember.age.goe(
                        JPAExpressions.select(qMemberSub.age.avg())
                                .from(qMemberSub)
                ))
                .fetch();

        // then
        results.forEach(member -> log.info("{}", member));
        assertThat(results).extracting("age")
                .containsExactly(30, 40);
    }

    /**
     * 나이가 가장 많은 회원 조회
     */
    @Test
    public void subQueryIn() throws Exception {
        QMember qMemberSub = new QMember("memberSub");

        // when
        List<Member> results = queryFactory.selectFrom(qMember)
                .where(qMember.age.in(
                        JPAExpressions.select(qMemberSub.age)
                                .from(qMemberSub)
                                .where(qMemberSub.age.gt(10))
                ))
                .fetch();

        // then
        results.forEach(member -> log.info("{}", member));
        assertThat(results).extracting("age")
                .containsExactly(20, 30, 40);
    }

    @Test
    public void selectSubQuery() throws Exception {
        QMember qMemberSub = new QMember("memberSub");

        // when
        List<Tuple> results = queryFactory
                .select(qMember.username,
                        JPAExpressions.select(qMemberSub.age.avg())
                                .from(qMemberSub))
                .from(qMember)
                .fetch();

        // then
        results.forEach(tuple -> log.info("{}", tuple));
    }
}
