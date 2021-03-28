package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    void save() {
        Member member = new Member();
        member.setName("test");
        repository.save(member);

        Member result = repository.findById(member.getId()).get();
        assertThat(result).isEqualTo(member);
    }

    @Test
    void findById() {
        Member member1 = new Member();
        member1.setName("test1");
        repository.save(member1);
        Member member2 = new Member();
        member2.setName("test2");
        repository.save(member2);

        Member result = repository.findById(member1.getId()).get();

        assertThat(result).isEqualTo(member1);
    }

    @Test
    void findByName() {
        Member member1 = new Member();
        member1.setName("test1");
        repository.save(member1);
        Member member2 = new Member();
        member2.setName("test2");
        repository.save(member2);

        Member result = repository.findByName("test1").get();

        assertThat(result).isEqualTo(member1);
    }

    @Test
    void findAll() {
        Member member1 = new Member();
        member1.setName("test1");
        repository.save(member1);
        Member member2 = new Member();
        member2.setName("test2");
        repository.save(member2);

        List<Member> members = repository.findAll();

        assertThat(members.size()).isEqualTo(2);
    }
}