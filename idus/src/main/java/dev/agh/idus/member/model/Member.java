package dev.agh.idus.member.model;

import dev.agh.idus.order.model.Orders;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Builder
public class Member implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String username;
    private String email;
    private String password;
    private String nickname;
    private String sex;
    private String phone;

    @OneToMany(mappedBy = "member")
    private List<Orders> orders;

    //인증받은 사람만
    //회원가입 완료 but 인증 X
    private boolean enabled;

    public void userVerify() {
        this.enabled = true;
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Collection<GrantedAuthority> authorities = new ArrayList<>();
//        GrantedAuthority authority = new SimpleGrantedAuthority(role);
//
//        authorities.add(authority);
//        return authorities;
//    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
