package smartlms.mapper;

import org.springframework.stereotype.Component;
import smartlms.entity.UserEntity;
import smartlms.entity.enums.UserRole;
import smartlms.entity.enums.UserStatus;

@Component
public class UserMapper {

    public UserEntity toEntity(String username, String password, UserRole role) {
        return UserEntity.builder()
                .username(username)
                .password(password)
                .role(role)
                .status(UserStatus.ACTIVE)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .build();
    }
}