package dev.agh.idus.order.model;

import dev.agh.idus.member.model.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Orders {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String orderNo;
    private String productName;

    //날짜 시간 모두 저장
    private LocalDateTime orderDate;

    @ManyToOne
    @JoinColumn(name = "member_idx")
    private Member member;
}
