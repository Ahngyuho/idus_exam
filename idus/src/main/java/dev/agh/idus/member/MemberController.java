package dev.agh.idus.member;

import dev.agh.idus.member.model.MemberDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


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
}
