package study.querydsl.basic;

import com.querydsl.core.QueryResults;
import org.junit.jupiter.api.Test;
import study.querydsl.entity.Member;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QuerydslBasicSearchTest extends QuerydslBasicTestCore {

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

        /*
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
}
