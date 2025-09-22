package academy.kata.securityapp.repositories;

import academy.kata.securityapp.models.UserEntity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    @Query("SELECT DISTINCT u FROM UserEntity u JOIN FETCH u.roles")
    List<UserEntity> findAllWithRoles();

    @Modifying
    @Query("DELETE UserEntity u WHERE u.id = :id")
    Integer delete(Long id);

}
