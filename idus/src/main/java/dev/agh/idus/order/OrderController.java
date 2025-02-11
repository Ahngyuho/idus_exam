package dev.agh.idus.order;

import dev.agh.idus.member.model.Member;
import dev.agh.idus.order.model.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/register")
    public void register(@RequestBody OrderDto.OrderRequest request, @AuthenticationPrincipal Member member) {
        orderService.save(request,member);
    }
}
