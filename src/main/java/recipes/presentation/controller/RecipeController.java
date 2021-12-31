package recipes.presentation.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.business.security.UserDetailsImpl;
import recipes.business.service.RecipeService;
import recipes.presentation.dto.NewReceiptResponseDto;
import recipes.presentation.dto.RecipeDto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping("/api/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping("/new")
    @ResponseBody
    NewReceiptResponseDto newRecipe(@Valid @RequestBody RecipeDto newRecipe,
                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {
        long newId = recipeService.saveRecipe(newRecipe, userDetails);
        return new NewReceiptResponseDto(newId);
    }

    @GetMapping("/{id}")
    RecipeDto getRecipe(@PathVariable long id) {
        return recipeService.getRecipe(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateRecipe(@PathVariable long id, @Valid @RequestBody RecipeDto recipe,
                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        recipeService.updateRecipe(id, recipe, userDetails);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteRecipe(@PathVariable long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        recipeService.deleteRecipeById(id, userDetails);
    }

    @GetMapping(value = "/search", params = {"name", "!category"})
    List<RecipeDto> searchByName(@RequestParam("name") @NotBlank String name) {
        return recipeService.searchByName(name);
    }

    @GetMapping(value = "/search", params = {"category", "!name"})
    List<RecipeDto> searchByCategory(@RequestParam("category") @NotBlank String category) {
        return recipeService.searchByCategory(category);
    }
}
