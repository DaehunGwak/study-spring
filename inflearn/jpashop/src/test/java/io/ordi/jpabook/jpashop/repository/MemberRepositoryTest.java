package io.ordi.jpabook.jpashop.repository;

import io.ordi.jpabook.jpashop.domain.Member;
import io.ordi.jpabook.jpashop.service.MemberService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * https://effectivesquid.tistory.com/entry/Spring-Boot-starter-test-%EC%99%80-Junit5%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%9C-%ED%85%8C%EC%8A%A4%ED%8A%B8
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager entityManager;

    @Test
    void 회원가입() {
        // given
        Member member = new Member();
        member.setName("memberA");

        // when
        Long savedId = memberService.join(member);
        entityManager.flush();
        entityManager.clear();

        // then
        Member findMember = memberRepository.findOne(savedId);
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    void 회원가입_중복_예외() {
        // given
        Member member = new Member();
        member.setName("memberA");
        Member member2 = new Member();
        member2.setName("memberA");

        // when
        memberService.join(member);

        // then
        assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(() -> memberService.join(member2));
    }
}
