package dev.agh.idus.member;

import dev.agh.idus.member.model.MemberDto;
import dev.agh.idus.member.model.PageResponse;
import dev.agh.idus.order.model.OrderDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @Operation(summary = "회원 등록", description = "회원을 등록하는 기능입니다.")
    @PostMapping("/signup")
    public void signup(@RequestBody MemberDto.SignupRequest dto) {
        memberService.save(dto.toEntity(passwordEncoder.encode(dto.getPassword())));
    }

    @Operation(summary = "회원 조회", description = "회원을 조회하는 기능입니다.")
    @GetMapping("/{idx}")
    public ResponseEntity<MemberDto.DetailResponse> detail(@PathVariable Long idx) {
        return ResponseEntity.ok(memberService.getDetail(idx));
    }

    @Operation(summary = "단일 회원의 주문 목록 조회", description = "단일 회원의 주문 목록을 조회하는 기능입니다.")
    @GetMapping("/{idx}/orders")
    public ResponseEntity<List<OrderDto.OrderResponse>> detailWithOrders(@PathVariable Long idx) {
        return ResponseEntity.ok(memberService.getMemberWithOrders(idx));
    }

    @Operation(summary = "여러 회원 목록 조회", description = "여러 회원 목록을 조회하는 기능입니다.")
    @GetMapping("/list")
    public ResponseEntity<PageResponse<MemberDto.MemberResponse>> list(int page, int size,
                                                               @RequestParam(required = false) String username,
                                                               @RequestParam(required = false) String email) {
        return ResponseEntity.ok(PageResponse.from(memberService.getList(page, size, username, email) , MemberDto.MemberResponse::fromEntity));
    }

    @Deprecated
    @Operation(summary = "로그아웃", description = "로그아웃 기능 입니다.")
    @PostMapping("/logout")
    public void logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("ATOKEN", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // 즉시 만료

        response.addCookie(cookie);
    }
}
