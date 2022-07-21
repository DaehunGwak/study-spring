package study.querydsl.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;

@Slf4j
public class QuerydslBasicConcatTest extends QuerydslBasicTestCore {

    @Test
    public void concat() throws Exception {
        // when
        // {username}_{age}
        List<String> results = queryFactory
                .select(qMember.username.concat("_").concat(qMember.age.stringValue()))
                .from(qMember)
                .fetch();

        // then
        results.forEach(log::info);
    }
}
