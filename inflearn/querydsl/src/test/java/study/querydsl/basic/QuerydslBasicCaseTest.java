package study.querydsl.basic;

import com.querydsl.core.types.dsl.CaseBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;

@Slf4j
public class QuerydslBasicCaseTest extends QuerydslBasicTestCore {

    /**
     * 왠만하면 권장하지 않고, db에서는 최소한의 필터링만
     * case 문은 애플리케이션에서 작성하면 어떨까~
     */
    @Test
    public void basicCase() throws Exception {
        // when
        List<String> results = queryFactory
                .select(qMember.age
                        .when(10).then("열살")
                        .when(20).then("스무살")
                        .otherwise("기타"))
                .from(qMember)
                .fetch();

        // then
        results.forEach(log::info);
    }

    @Test
    public void complexCase() throws Exception {
        // when
        List<String> results = queryFactory
                .select(new CaseBuilder()
                        .when(qMember.age.between(0, 20)).then("0~20살")
                        .when(qMember.age.between(21, 30)).then("21~30살")
                        .otherwise("기타"))
                .from(qMember)
                .fetch();

        // then
        results.forEach(log::info);
    }
}
