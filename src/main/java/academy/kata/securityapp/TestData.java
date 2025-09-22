package academy.kata.securityapp;

import academy.kata.securityapp.models.RoleEntity;
import academy.kata.securityapp.models.UserEntity;
import academy.kata.securityapp.models.RoleEnum;
import academy.kata.securityapp.repositories.RoleRepository;
import academy.kata.securityapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class TestData implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public TestData(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        RoleEntity userRole = roleRepository.findByName(RoleEnum.USER.name())
                .orElseGet(() -> roleRepository.save(new RoleEntity(null, RoleEnum.USER.name())));
        RoleEntity adminRole = roleRepository.findByName(RoleEnum.ADMIN.name())
                .orElseGet(() -> roleRepository.save(new RoleEntity(null, RoleEnum.ADMIN.name())));

        if (userRepository.findByUsername("user").isEmpty()) {
            UserEntity testUser = new UserEntity();
            testUser.setUsername("user");
            testUser.setPassword(passwordEncoder.encode("user"));
            testUser.setAge(30);
            testUser.getRoles().add(userRole);
            userRepository.save(testUser);
        }

        if (userRepository.findByUsername("admin").isEmpty()) {
            UserEntity admin = new UserEntity();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setAge(45);
            admin.getRoles().add(userRole);
            admin.getRoles().add(adminRole);
            userRepository.save(admin);
        }

    }
}
