package study.querydsl.intermediate;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;
import study.querydsl.entity.Member;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class IntermediateDynamicQueryTest extends IntermediateTestCore {

    @Test
    public void booleanBuilder() throws Exception {
        // given
        String usernameParam = "member1";
        Integer ageParam = null;

        // when
        List<Member> results = searchMember1(usernameParam, ageParam);

        // then
        assertThat(results).hasSize(1);
    }

    private List<Member> searchMember1(String usernameParam, Integer ageParam) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (Objects.nonNull(usernameParam)) {
            booleanBuilder.and(qMember.username.eq(usernameParam));
        }
        if (Objects.nonNull(ageParam)) {
            booleanBuilder.and(qMember.age.eq(ageParam));
        }

        return queryFactory.selectFrom(qMember)
                .where(booleanBuilder)
                .fetch();
    }

    @Test
    public void whereParam() throws Exception {
        // given
        String usernameParam = "member1";
        Integer ageParam = null;

        // when
        List<Member> results = searchMember2(usernameParam, ageParam);

        // then
        assertThat(results).hasSize(1);
    }

    private List<Member> searchMember2(String usernameParam, Integer ageParam) {
        return queryFactory.selectFrom(qMember)
//                .where(usernameEq(usernameParam), ageEq(ageParam)) // and 조건 연결, null 조건 무시
                .where(allEqBy(usernameParam, ageParam))
                .fetch();
    }

    private BooleanExpression allEqBy(String usernameParam, Integer ageParam) {
        // null 처리는 이게 최선?
        // return usernameEq(usernameParam).and(ageEq(ageParam)); // 원본
        return Optional.ofNullable(usernameEq(usernameParam))
                .map(expression -> expression.and(ageEq(ageParam)))
                .orElseGet(() -> ageEq(ageParam));
    }

    private BooleanExpression usernameEq(String usernameParam) {
        return StringUtils.hasText(usernameParam) ? qMember.username.eq(usernameParam) : null;
    }

    private BooleanExpression ageEq(Integer ageParam) {
        return Objects.isNull(ageParam) ? null : qMember.age.eq(ageParam);
    }


}
