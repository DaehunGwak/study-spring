package jpabook.jpashop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = JpashopApplication.class)
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    @Transactional  // TC 에 transactional 이 붙으면 실행 이후 rollback 함
    @Rollback(false)  // 롤백 무시는 이렇게 가능
    public void testMember() throws Exception {
        // given
        Member member = new Member();
        member.setUsername("memberA");

        // when
        Long savedId = memberRepository.save(member);
        Member findMember = memberRepository.find(savedId);

        // then
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember).isEqualTo(member);
        System.out.println(findMember == member);
    }
}