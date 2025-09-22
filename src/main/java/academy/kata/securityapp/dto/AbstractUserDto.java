package academy.kata.securityapp.dto;

import academy.kata.securityapp.utils.ValidationTexts;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AbstractUserDto {

    @NotBlank(message = ValidationTexts.NOT_BLANK)
    @Size(min = 2, max = 50, message = ValidationTexts.INCORRECT_NAME_LENGTH)
    private String username;

    @Min(value = 0, message = ValidationTexts.NAME_NOT_IN_RANGE)
    @Max(value = 150, message = ValidationTexts.NAME_NOT_IN_RANGE)
    private Integer age;

    private Boolean hasCar;

    @NotEmpty(message = "Select at least one role")
    private Long[] roleIds;
}
