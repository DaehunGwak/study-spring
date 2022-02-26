package io.ordi.jpabook.jpashop.api;

import io.ordi.jpabook.jpashop.domain.Member;
import io.ordi.jpabook.jpashop.service.MemberService;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController // Controller + ResponseBody (json or xml 바로 보내는 기능)
public class MemberApiController {

    private final MemberService memberService;

    @Deprecated
    @GetMapping("/v1/members")
    public List<Member> getMembersV1() {
        return memberService.findMembers();
    }

    @GetMapping("/v2/members")
    public MembersResponse getMembersV2() {
        return new MembersResponse(memberService.findMembers());
    }

    @Getter
    static class MembersResponse {
        private final List<MemberResponse> members;

        public MembersResponse(List<Member> members) {
            this.members = members.stream()
                    .map(MemberResponse::new)
                    .collect(Collectors.toList());
        }

        @Getter
        static class MemberResponse {
            private final Long id;
            private final String name;

            public MemberResponse(Member member) {
                this.id = member.getId();
                this.name = member.getName();
            }
        }
    }

    @Deprecated
    @PostMapping("/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
        Long id = memberService.join(request.toMemberEntity());
        return new CreateMemberResponse(id);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class CreateMemberRequest {
        @NotEmpty
        private String name;

        public Member toMemberEntity() {
            Member member = new Member();
            member.setName(name);
            return member;
        }
    }

    @Data
    @AllArgsConstructor
    static class CreateMemberResponse {
        private Long id;
    }

    @PutMapping("/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,
                                               @RequestBody @Valid UpdateMemberRequest request) {
        memberService.update(id, request.getName()); // command
        Member member = memberService.findOne(id); // query
        return new UpdateMemberResponse(member.getId(), member.getName());
    }

    @Data
    @NoArgsConstructor
    static class UpdateMemberRequest {
        @NotEmpty
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }
}
