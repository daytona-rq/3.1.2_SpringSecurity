package academy.kata.securityapp.controllers.secured;

import academy.kata.securityapp.dto.UserCreationDto;
import academy.kata.securityapp.dto.UserEditDto;
import academy.kata.securityapp.models.RoleEntity;
import academy.kata.securityapp.models.UserEntity;
import academy.kata.securityapp.repositories.RoleRepository;
import academy.kata.securityapp.services.RoleService;
import academy.kata.securityapp.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/admin")
@Controller
public class PrivateAdminController {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final RoleService roleService;

    @Autowired
    public PrivateAdminController(UserService userService, RoleRepository roleRepository, RoleService roleService) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.roleService = roleService;
    }

    @GetMapping
    public String adminPanel(Model model) {
        model.addAttribute("users", userService.findAllWithRoles());
        return "/admin/panel";
    }

    @GetMapping("/add-user")
    public String addUser(Model model) {
        model.addAttribute("user", new UserCreationDto());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "/admin/add-user";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("user") UserCreationDto userCreationDto,
                         BindingResult bindingResult,
                         Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("allRoles", roleService.getAllRoles());
            return "/admin/add-user";
        }
        userService.saveUser(userCreationDto);
        return "redirect:/admin";
    }

    @PostMapping("/delete-user")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/edit-user")
    public String updateUser(@RequestParam("id") Long id, Model model) {
        UserEntity currentUser = userService.getUserById(id);
        UserEditDto userEditDto = new UserEditDto();

        userEditDto.setId(id);
        userEditDto.setAge(currentUser.getAge());
        userEditDto.setUsername(currentUser.getUsername());
        userEditDto.setHasCar(currentUser.getHasCar());

        if (!currentUser.getRoles().isEmpty()) {
            userEditDto.setRoleIds(currentUser.getRoles()
                    .stream()
                    .map(RoleEntity::getId)
                    .toArray(Long[]::new));
        }

        model.addAttribute("allRoles", roleService.getAllRoles());
        model.addAttribute("user", userEditDto);
        return "/admin/edit-user";
    }

    @PostMapping("/edit-user")
    public String edit(@Valid @ModelAttribute("user") UserEditDto userEditDto,
                       BindingResult bindingResult,
                       Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("allRoles", roleService.getAllRoles());
            return "/admin/edit-user";
        }

        userService.updateUser(userEditDto.getId(), userEditDto);
        return "redirect:/admin";
    }
}
