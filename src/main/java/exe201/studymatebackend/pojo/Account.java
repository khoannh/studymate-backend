package exe201.studymatebackend.pojo;

import exe201.studymatebackend.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table
@Data
@AllArgsConstructor
public class Account implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accountID;  // viết liền ID như vầy nha

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private int coin;

    @Column(nullable = true)
    private LocalDateTime createdAt;

    @Column(nullable = true)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private Boolean isActive;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AccountRoom> accountRoomList;

    public Account() {
    }

    public Account(String email, String password, String username, Role role, int coin, LocalDateTime createdAt, LocalDateTime updatedAt, Boolean isActive) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.role = role;
        this.coin = coin;
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
        return this.isActive;
    }


}
