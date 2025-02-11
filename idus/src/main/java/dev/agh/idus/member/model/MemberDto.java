package dev.agh.idus.member.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

public class MemberDto {
    @Getter
    @Schema(description = "회원가입 시 필요한 데이터")
    public static class SignupRequest {
        private String username;
        private String email;
        private String password;
        private String nickname;
        private String sex;
        private String phone;

        public Member toEntity(String encodedPassword) {
            Member member = Member.builder()
                    .username(username)
                    .email(email)
                    .password(encodedPassword)
                    .nickname(nickname)
//                    .role(role)
                    .phone(phone)
                    .sex(sex)
                    .build();

            return member;
        }
    }

    @Getter
    @Builder
    public static class DetailResponse {
        private String username;
        private String email;
        private String nickname;
        private String sex;
        private String phone;

        public static DetailResponse fromEntity(Member member) {
            return DetailResponse.builder()
                    .username(member.getUsername())
                    .email(member.getEmail())
                    .nickname(member.getNickname())
                    .sex(member.getSex())
                    .phone(member.getPhone())
                    .build();
        }
    }

}
