package study.querydsl.intermediate;

import com.querydsl.core.types.dsl.Expressions;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;

@Slf4j
public class IntermediateSqlFunctionTest extends IntermediateTestCore {

    @Test
    public void sqlFunction() throws Exception {
        // when
        // H2Dialect 에 정의된 함수만 쓸 수 있음
        // 없지만 RDBMS 에 있는 함수는 상속해서 커스텀하게 추가해야함
        List<String> results = queryFactory.select(Expressions.stringTemplate(
                        "function('replace', {0}, {1}, {2})", qMember.username, "member", "M"))
                .from(qMember)
                .fetch();

        // then
        results.forEach(log::info);
    }

    @Test
    public void sqlFunction2() throws Exception {
        // when
        List<String> results = queryFactory
                .select(qMember.username)
                .from(qMember)
//                .where(qMember.username.eq(
//                        Expressions.stringTemplate("function('lower', {0})", qMember.username)
//                ))
                .where(qMember.username.eq(qMember.username.lower()))
                .fetch();

        // then
        results.forEach(log::info);
    }
}
