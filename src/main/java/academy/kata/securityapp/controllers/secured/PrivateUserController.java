package academy.kata.securityapp.controllers.secured;

import academy.kata.securityapp.models.UserEntity;
import academy.kata.securityapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/user")
public class PrivateUserController {

    private final UserRepository userRepository;

    @Autowired
    public PrivateUserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String profile(Model model, Authentication authentication) {
        UserEntity user = userRepository.findByUsername(authentication.getName()).orElseThrow();
        model.addAttribute("user", user);
        return "user/profile";
    }

}
