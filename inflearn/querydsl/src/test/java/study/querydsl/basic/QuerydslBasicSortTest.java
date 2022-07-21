package study.querydsl.basic;

import org.junit.jupiter.api.Test;
import study.querydsl.entity.Member;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QuerydslBasicSortTest extends QuerydslBasicTestCore {

    /**
     * 1. 나이 내림차숨
     * 2. 이름 올림 차순
     * 이름 없으면 마지막에 출력(nulls last)
     */
    @Test
    public void sort() {
        em.persist(new Member(null, 100));
        em.persist(new Member("member5", 100));
        em.persist(new Member("member6", 100));

        List<Member> findMembers = queryFactory.selectFrom(qMember)
                .where(qMember.age.eq(100))
                .orderBy(qMember.age.desc(), qMember.username.asc().nullsLast())
                .fetch();

        Member member5 = findMembers.get(0);
        Member member6 = findMembers.get(1);
        Member memberNull = findMembers.get(2);
        assertThat(member5.getUsername()).isEqualTo("member5");
        assertThat(member6.getUsername()).isEqualTo("member6");
        assertThat(memberNull.getUsername()).isNull();
    }
}
