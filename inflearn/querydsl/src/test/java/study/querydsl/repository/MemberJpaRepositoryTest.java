package study.querydsl.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.querydsl.dto.MemberSearchCondition;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.entity.Member;
import study.querydsl.entity.Team;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    public void basicTest() throws Exception {
        Member member = new Member("member1", 10);
        memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.findById(member.getId()).get();
        assertThat(findMember).isEqualTo(member);

        List<Member> findMembers1 = memberJpaRepository.findAll();
        assertThat(findMembers1).containsExactly(member);

        List<Member> findMembers2 = memberJpaRepository.findByUsername("member1");
        assertThat(findMembers2).containsExactly(member);

        List<Member> findMembers3 = memberJpaRepository.findAllUsingQueryDsl();
        assertThat(findMembers3).containsExactly(member);

        List<Member> findMembers4 = memberJpaRepository.findByUsernameUsingQueryDsl("member1");
        assertThat(findMembers4).containsExactly(member);
    }

    @Test
    public void searchTest() throws Exception {
        // given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        MemberSearchCondition condition = new MemberSearchCondition();
//        condition.setAgeGoe(35);
//        condition.setAgeLoe(40);
        condition.setTeamName("teamB");

        // when
        List<MemberTeamDto> results = memberJpaRepository.searchByBuilder(condition);

        // then
        assertThat(results).extracting("username")
                .containsExactly("member3", "member4");
    }

    @Test
    public void searchWhereParamTest() throws Exception {
        // given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setAgeGoe(30);
        condition.setAgeLoe(30);
        condition.setTeamName("teamB");

        // when
        List<MemberTeamDto> results = memberJpaRepository.search(condition);

        // then
        assertThat(results).extracting("username")
                .containsExactly("member3");
    }
}
