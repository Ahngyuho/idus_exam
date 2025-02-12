package dev.agh.idus.member.model;


import dev.agh.idus.order.model.OrderDto;
import dev.agh.idus.order.model.Orders;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Comparator;

/*
*
* @Patter : 정규표현식을 이용해서
* @Null : null 만 허용
* @NotNull : 빈 문자열 "", 공백은 허용 " ",null 은 안됨
* @NotEmpty : " " 허용, null 혹은 빈 문자열은 안됨
*
*
*
* */

public class MemberDto {
    @Getter
    @Schema(description = "회원가입 시 필요한 데이터")
    public static class SignupRequest {
        @Pattern(regexp = "^[가-힣a-zA-Z]+$",message = "이름은 한글, 영문 재소문자만 허용됩니다.")
        private String username;

        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
        @Email @NotBlank(message = "이메일은 필수로 입력해야 합니다.")
        private String email;

        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).+$")
        @Size(min = 10,message = "비밀번호는 최소 10글자 이상 입력해야 하고, 영문 대문자, 영문 소문자, 특수 문자, 숫자를 최소 1개 이상씩 포함해야 합니다.")
        private String password;

        @Pattern(regexp = "^[a-z]+$",message = "별명은 영문 소문자만 허용 됩니다.")
        private String nickname;
        private String sex;
        @Pattern(regexp = "^\\d+$",message = "전화번호는 숫자만 허용됩니다.")
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

    @Getter
    @Builder
    public static class MemberResponse {
        private String username;
        private String email;
        private String nickname;
        private String sex;
        private String phone;
        private OrderDto.OrderResponse lastOrder;

        public static MemberResponse fromEntity(Member member) {
            return MemberResponse.builder()
                    .username(member.getUsername())
                    .email(member.getEmail())
                    .nickname(member.getNickname())
                    .sex(member.getSex())
                    .phone(member.getPhone())
                    .lastOrder(OrderDto.OrderResponse.fromEntity(
                                    member.getOrders().stream().sorted(Comparator.comparing(Orders::getOrderDate).reversed()).findFirst().orElse(null)))
                    .build();
        }
    }

}
