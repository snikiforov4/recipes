package recipes.presentation.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class UserDto {
    @Pattern(regexp=".+@.+\\..+", message="Please provide a valid email address")
    final String email;
    @Pattern(regexp = "\\b\\w{8,}\\b")
    final String password;
}
