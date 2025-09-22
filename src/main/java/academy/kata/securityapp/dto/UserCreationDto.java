package academy.kata.securityapp.dto;

import academy.kata.securityapp.utils.ValidationTexts;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserCreationDto extends AbstractUserDto {

    @NotBlank(message = ValidationTexts.NOT_BLANK)
    @Size(min = 2, max = 50, message = ValidationTexts.INCORRECT_NAME_LENGTH)
    private String password;

}
