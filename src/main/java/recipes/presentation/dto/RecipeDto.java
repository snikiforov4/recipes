package recipes.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDto {
    @NotBlank(message = "`name` must be not empty")
    String name;
    @NotBlank(message = "`description` must be not empty")
    String description;
    @NotBlank(message = "`category` must be not empty")
    String category;
    @Nullable
    LocalDateTime date;
    @NotNull
    @Size(min = 1, message = "should contain at least 1 ingredient")
    String[] ingredients;
    @NotNull
    @Size(min = 1, message = "should contain at least 1 direction")
    String[] directions;
}
