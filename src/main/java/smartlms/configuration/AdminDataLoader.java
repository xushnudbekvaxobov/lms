package smartlms.configuration;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import smartlms.entity.UserEntity;
import smartlms.entity.enums.UserRole;
import smartlms.entity.enums.UserStatus;
import smartlms.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class AdminDataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.sql.init.mode}")
    private String mode;

    @Override
    public void run(String... args) {
        if (mode.equals("always") && userRepository.findByUsername("admin").isEmpty()) {

            UserEntity admin = UserEntity.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .role(UserRole.ADMIN)
                    .status(UserStatus.ACTIVE)
                    .isEnabled(true)
                    .isAccountNonLocked(true)
                    .isAccountNonExpired(true)
                    .isCredentialsNonExpired(true)
                    .build();

            userRepository.save(admin);
            System.out.println(">>> [SUCCESS] Admin foydalanuvchisi yaratildi: username=admin, password=admin123");
        } else {
            System.out.println(">>> [INFO] Admin foydalanuvchisi bazada allaqachon mavjud.");
        }
    }
}
