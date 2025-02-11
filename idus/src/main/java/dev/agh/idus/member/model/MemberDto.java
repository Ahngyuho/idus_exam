package dev.agh.idus.member.model;


import dev.agh.idus.order.model.OrderDto;
import dev.agh.idus.order.model.Orders;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Comparator;

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
