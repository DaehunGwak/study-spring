package study.querydsl.intermediate;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Commit;
import study.querydsl.entity.Member;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class IntermediateBulkQueryTest extends IntermediateTestCore {

    /**
     * 문제점? 영속성 컨텍스트
     * bulk 처리는 영속성 컨텍스트에 변경을 반영하지 않는다
     */
    @Test
//    @Commit
    public void bulkUpdate() {
        // when
        long count = queryFactory.update(qMember)
                .set(qMember.username, "비회원")
                .where(qMember.age.lt(28))
                .execute();
        em.flush();
        em.clear();

        List<Member> results = queryFactory.selectFrom(qMember)
                .fetch();

        // then
        assertThat(count).isEqualTo(2);
        assertThat(results.stream()
                .filter(member -> member.getUsername().equals("비회원"))
                .count()).isEqualTo(2);
    }

    @Test
    public void bulkAdd() throws Exception {
        // when
        long count = queryFactory.update(qMember)
                .set(qMember.age, qMember.age.add(5))
                .execute();
    }

    @Test
    public void bulkDelete() throws Exception {
        // when
        long count = queryFactory.delete(qMember)
                .where(qMember.age.gt(18))
                .execute();
    }
}
