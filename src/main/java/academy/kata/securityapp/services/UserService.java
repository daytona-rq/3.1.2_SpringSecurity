package academy.kata.securityapp.services;

import academy.kata.securityapp.dto.UserCreationDto;
import academy.kata.securityapp.dto.UserEditDto;
import academy.kata.securityapp.models.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity getUserById(Long id);

    void saveUser(UserCreationDto userCreationDto);

    void updateUser(Long id, UserEditDto userEditDto);

    void deleteUser(Long id);

    List<UserEntity> findAllWithRoles();
}
