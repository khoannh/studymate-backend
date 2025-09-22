package exe201.studymatebackend.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import exe201.studymatebackend.enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "account")
@Data
public class Account implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accountid")
    private Integer accountID;

    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "token", nullable = false)
    private int token;

    @Column(name = "created_at", nullable = true)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
    @Column(name = "avatar", columnDefinition = "BYTEA")
    private byte[] avatar;

    public Account() {
    }

    public Account(String email, String password, String username, Role role, int token, LocalDateTime createdAt, LocalDateTime updatedAt, Boolean isActive) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.role = role;
        this.token = token;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isActive = isActive;
    }

    // Nếu role là Enum, bạn có thể sử dụng đoạn mã sau:
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Lấy tên chuỗi của enum (ví dụ: RoleType.ADMIN.name() sẽ trả về "ADMIN")
        String roleName = this.role.name();
        // Trả về một Collection chứa SimpleGrantedAuthority với tiền tố "ROLE_"
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + roleName));
    }


    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
