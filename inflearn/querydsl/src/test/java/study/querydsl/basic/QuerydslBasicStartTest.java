package study.querydsl.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import study.querydsl.entity.Member;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class QuerydslBasicStartTest extends QuerydslBasicTestCore {

    @Test
    public void startJPSQL() {
        // 실제 오류 발생 시점은 런타임 (호출했을 때)
        Member findMember = em.createQuery("select m from Member m where m.username = :username", Member.class)
                .setParameter("username", "member1")
                .getSingleResult();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    public void startQuerydsl() {
        // 컴파일 타임에 오류를 잡을 수 있음
        // prepared statements 에 자동으로 바인딩? (미리 DB에 해당 쿼리문을 컴파일해서 재사용하는 것)
        /*
         * https://dev.mysql.com/doc/refman/8.0/en/sql-prepared-statements.html
         * https://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html
         * https://diqmwl-programming.tistory.com/82
         */
        Member findMember = queryFactory.select(qMember)
                .from(qMember)
                .where(qMember.username.eq("member1"))
                .fetchOne(); // jpql 로 음

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }
}
