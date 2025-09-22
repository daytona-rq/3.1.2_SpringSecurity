package academy.kata.securityapp.services;

import academy.kata.securityapp.dto.AbstractUserDto;
import academy.kata.securityapp.dto.UserCreationDto;
import academy.kata.securityapp.dto.UserEditDto;
import academy.kata.securityapp.models.UserEntity;
import academy.kata.securityapp.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleServiceImpl roleServiceImpl;
    private final PasswordEncoder passwordEncoder;

    private void executeAndCheckThatIdWasFound(Long id, Supplier<Integer> repositoryAction) {
        Integer affectedRows = repositoryAction.get();
        if (affectedRows == 0) {
            throw new EntityNotFoundException("User with id " + id + " was not found");
        }
    }

    private <T extends AbstractUserDto> void setFieldsFromDto(T userDto, UserEntity userDest) {
        userDest.setUsername(userDto.getUsername());
        userDest.setAge(userDto.getAge());
        userDest.setHasCar(userDto.getHasCar());
        userDest.setRoles(
                Arrays.stream(userDto.getRoleIds())
                        .map(roleServiceImpl::findById)
                        .map(Optional::get)
                        .collect(Collectors.toSet()));
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserEntity> findAllWithRoles() {
        return userRepository.findAllWithRoles();
    }

    @Transactional(readOnly = true)
    @Override
    public UserEntity getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("User with id " + id + " was not found")
                );
    }

    @Transactional
    @Override
    public void saveUser(UserCreationDto userCreationDto) {

        UserEntity userEntity = new UserEntity();
        setFieldsFromDto(userCreationDto, userEntity);
        userEntity.setPassword(passwordEncoder.encode(userCreationDto.getPassword()));

        userRepository.save(userEntity);
    }

    @Transactional
    @Override
    public void updateUser(Long id, UserEditDto userEditDto) {

        UserEntity userEntity = userRepository.findById(id).orElseThrow();
        setFieldsFromDto(userEditDto, userEntity);

        userRepository.save(userEntity);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        executeAndCheckThatIdWasFound(id, () -> userRepository.delete(id));
    }

}
