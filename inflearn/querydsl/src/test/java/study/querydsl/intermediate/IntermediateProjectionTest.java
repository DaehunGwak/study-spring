package study.querydsl.intermediate;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import study.querydsl.dto.MemberDto;
import study.querydsl.dto.QMemberDto;
import study.querydsl.dto.UserDto;
import study.querydsl.entity.QMember;

import java.util.List;

@Slf4j
public class IntermediateProjectionTest extends IntermediateTestCore {

    @Test
    public void simpleProjection() throws Exception {
        // when
        List<String> results = queryFactory
                .select(qMember.username)
                .from(qMember)
                .fetch();

        // then
        results.forEach(log::info);
    }

    /**
     * tuple 은 repository 내부에서만 사용
     */
    @Test
    public void tupleProjection() throws Exception {
        // when
        List<Tuple> results = queryFactory.select(qMember.username, qMember.age)
                .from(qMember)
                .fetch();

        // then
        results.forEach(tuple -> {
            log.info("username={}, age={}", tuple.get(qMember.username), tuple.get(qMember.age));
        });
    }

    /**
     * jpql dto 조회 문제점
     * - new 명령어
     * - package 패스 다 지정해줘야함
     * - 생성자 방식만 지원함
     */
    @Test
    public void findDtoByJPQL() throws Exception {
        // when
        List<MemberDto> results = em.createQuery(
                "select new study.querydsl.dto.MemberDto(m.username, m.age) from Member m", MemberDto.class)
                .getResultList();

        // then
        results.forEach(dto -> log.info("{}", dto));
    }

    /**
     * querydsl 빈 생성 (bean population)
     * - 프로퍼티 접근
     * - 필드 직접 접근
     * - 생성자 이용
     */
    @Test
    public void findDtoBySetter() throws Exception {
        // when
        List<MemberDto> results = queryFactory
                .select(Projections.bean(MemberDto.class, qMember.username, qMember.age))
                .from(qMember)
                .fetch();

        // then
        results.forEach(dto -> log.info("{}", dto));
    }

    @Test
    public void findDtoByFields() throws Exception {
        // when
        List<MemberDto> results = queryFactory
                .select(Projections.fields(MemberDto.class, qMember.username, qMember.age))
                .from(qMember)
                .fetch();

        // then
        results.forEach(dto -> log.info("{}", dto));
    }

    @Test
    public void findDtoByConstructor() throws Exception {
        // when
        List<MemberDto> results = queryFactory
                .select(Projections.constructor(MemberDto.class, qMember.username, qMember.age))
                .from(qMember)
                .fetch();

        // then
        results.forEach(dto -> log.info("{}", dto));
    }

    @Test
    public void findUserDto() throws Exception {
        // when
        List<UserDto> results = queryFactory
                .select(Projections.fields(
                        UserDto.class,
                        qMember.username.as("name"),
                        qMember.age))
                .from(qMember)
                .fetch();

        // then
        results.forEach(dto -> log.info("{}", dto));
    }

    @Test
    public void findUserDtoSubQuery() throws Exception {
        QMember qMemberSub = new QMember("memberSub");

        // when
        List<UserDto> results = queryFactory
                .select(Projections.fields(
                        UserDto.class,
                        qMember.username.as("name"),
                        ExpressionUtils.as(JPAExpressions.select(qMemberSub.age.max()).from(qMemberSub), "age")))
                .from(qMember)
                .fetch();

        // then
        results.forEach(dto -> log.info("{}", dto));
    }

    @Test
    public void findUserDtoByConstructor() throws Exception {
        // when
        List<UserDto> results = queryFactory
                .select(Projections.constructor(
                        UserDto.class,
                        qMember.username,
                        qMember.age))
                .from(qMember)
                .fetch();

        // then
        results.forEach(dto -> log.info("{}", dto));
    }

    /**
     * 쿼리 프로젝션
     * 컴파일 시점에 문법 에러도 잡아주고 좋긴한데...
     * 고민거리: 큐파일 생성 필요, DTO 가 querydsl 에 의존됨 (음 안쓰는게 좋을 듯)
     */
    @Test
    public void findDtoByQueryProjection() throws Exception {
        // when
        List<MemberDto> results = queryFactory
                .select(new QMemberDto(qMember.username, qMember.age))
                .from(qMember)
                .fetch();

        // then
        results.forEach(dto -> log.info("{}", dto));
    }
}
