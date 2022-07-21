package study.querydsl.basic;

import com.querydsl.core.QueryResults;
import org.junit.jupiter.api.Test;
import study.querydsl.entity.Member;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QuerydslBasicPagingTest extends QuerydslBasicTestCore {

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
}
