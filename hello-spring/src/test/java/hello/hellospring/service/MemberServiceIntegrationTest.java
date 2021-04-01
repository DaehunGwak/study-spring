package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest  // Spring 을 띄어서 테스트
@Transactional  // 테스트는 반복할 수 있어야
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    void 회원가입() {
        // given
        Member member = new Member();
        member.setName("spring");

        // when
        memberService.join(member);  // 핵심은 중복 로직이 잘 되는가

        // then
        Member findMember = memberService.findOne(member.getId()).get();
        assertThat(findMember.getName()).isEqualTo(member.getName());
    }

    @Test
    void 중복회원가입() {
        // given
        String name = "test";
        Member member1 = new Member();
        member1.setName(name);
        Member member2 = new Member();
        member2.setName(name);

        // when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        // then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}