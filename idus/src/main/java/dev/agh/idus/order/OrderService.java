package dev.agh.idus.order;

import dev.agh.idus.member.model.Member;
import dev.agh.idus.order.model.OrderDto;
import dev.agh.idus.order.model.Orders;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public void save(OrderDto.OrderRequest request, Member member) {
        UUID orderNo = UUID.randomUUID();
        Orders entity = request.toEntity(orderNo.toString().substring(0, 12), LocalDateTime.now(),member);
        orderRepository.save(entity);
    }
}
