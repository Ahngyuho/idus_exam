package dev.agh.idus.order.model;

import dev.agh.idus.member.model.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class OrderDto {
    @Getter
    public static class OrderRequest{
        private String productName;

        public Orders toEntity(String orderNo, LocalDateTime orderDate, Member member){
            return Orders.builder()
                    .orderNo(orderNo)
                    .productName(productName)
                    .member(member)
                    .orderDate(orderDate)
                    .build();
        }
    }

    @Getter
    @Builder
    public static class OrderResponse{
        private String orderNo;
        private String productName;
        //날짜 시간 모두 저장
        private LocalDateTime orderDate;

        public static OrderResponse fromEntity(Orders entity){
            if(entity == null) return null;
            return OrderResponse.builder()
                    .productName(entity.getProductName())
                    .orderDate(entity.getOrderDate())
                    .orderNo(entity.getOrderNo())
                    .build();
        }
    }
}
