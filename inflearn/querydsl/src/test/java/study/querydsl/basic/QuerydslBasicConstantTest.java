package study.querydsl.basic;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;

@Slf4j
public class QuerydslBasicConstantTest extends QuerydslBasicTestCore {

    @Test
    public void constant() throws Exception {
        // when
        List<Tuple> results = queryFactory
                .select(qMember.username, Expressions.constant("A"))
                .from(qMember)
                .fetch();

        // then
        results.forEach(tuple -> log.info("tuple = {}", tuple));
    }
}
