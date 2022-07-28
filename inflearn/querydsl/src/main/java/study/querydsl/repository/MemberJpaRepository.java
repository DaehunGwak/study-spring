package study.querydsl.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import study.querydsl.dto.MemberSearchCondition;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.dto.QMemberTeamDto;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;
import study.querydsl.entity.QTeam;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 순수 JPA 리포지토리와 Querydsl
 */
@Repository
//@RequiredArgsConstructor // queryFactory 를 bean 으로 등록했을 시
public class MemberJpaRepository {

    private static final QMember qMember = QMember.member;
    private static final QTeam qTeam = QTeam.team;

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;
    // 동시성 문제? 빈으로 공유해도 문제 없음
    // EntityManager 에 의존
    // 트랜잭션마다 EntityManager가 격리되어 있음 (JPA 13.1 범위의 영속성 컨텍스트 참고)

    public MemberJpaRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public void save(Member member) {
        em.persist(member);
    }

    public Optional<Member> findById(Long id) {
        Member findMember = em.find(Member.class, id);
        return Optional.ofNullable(findMember);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findAllUsingQueryDsl() {
        return queryFactory.selectFrom(QMember.member)
                .fetch();
    }

    public List<Member> findByUsername(String username) {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByUsernameUsingQueryDsl(String username) {
        return queryFactory.selectFrom(QMember.member)
                .where(QMember.member.username.eq(username))
                .fetch();
    }

    /**
     * 동적 쿼리와 성능 최적화 조회 - Builder 사용
     *
     * 주의사항! 조건이 모두 없으면 안됨
     * 조건이 모두 없을땐 모든 데이터를 긁어오는 쿼리가 만들어짐
     * 페이징, LIMIT 를 기본적으로 적용 권장
     */
    public List<MemberTeamDto> searchByBuilder(MemberSearchCondition condition) {
        BooleanBuilder builder = new BooleanBuilder();

        if (StringUtils.hasText(condition.getUsername())) {
            builder.and(qMember.username.eq(condition.getUsername()));
        }
        if (StringUtils.hasText(condition.getTeamName())) {
            builder.and(qTeam.name.eq(condition.getTeamName()));
        }
        if (Objects.nonNull(condition.getAgeGoe())) {
            builder.and(qMember.age.goe(condition.getAgeGoe()));
        }
        if (Objects.nonNull(condition.getAgeLoe())) {
            builder.and(qMember.age.loe(condition.getAgeLoe()));
        }

        return queryFactory
                .select(new QMemberTeamDto(
                        qMember.id.as("memberId"),
                        qMember.username,
                        qMember.age,
                        qTeam.id.as("teamId"),
                        qTeam.name.as("teamName")
                ))
                .from(qMember)
                .leftJoin(qMember.team, qTeam)
                .where(builder)
                .fetch();
    }
}
