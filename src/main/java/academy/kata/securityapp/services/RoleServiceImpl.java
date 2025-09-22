package academy.kata.securityapp.services;


import academy.kata.securityapp.models.RoleEntity;
import academy.kata.securityapp.repositories.RoleRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    @Override
    public Optional<RoleEntity> findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    @Override
    public List<RoleEntity> getAllRoles() {
        return roleRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<RoleEntity> findById(Long id) {
        return roleRepository.findById(id);
    };

}
