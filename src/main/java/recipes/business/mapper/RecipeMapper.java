package recipes.business.mapper;

import com.google.common.base.Converter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import recipes.persistence.entity.Recipe;
import recipes.presentation.dto.RecipeDto;

import javax.annotation.Nonnull;

@Component
@AllArgsConstructor
public class RecipeMapper extends Converter<RecipeDto, Recipe> {

    private final IngredientsMapper ingredientsMapper;
    private final DirectionsMapper directionsMapper;

    @Override
    @Nonnull
    protected Recipe doForward(@Nonnull RecipeDto recipeDto) {
        Recipe result = new Recipe();
        result.setName(recipeDto.getName());
        result.setDescription(recipeDto.getDescription());
        result.setCategory(recipeDto.getCategory());
        result.setIngredients(ingredientsMapper.convert(recipeDto.getIngredients()));
        result.setDirections(directionsMapper.convert(recipeDto.getDirections()));
        return result;
    }

    @Override
    @Nonnull
    protected RecipeDto doBackward(@Nonnull Recipe recipe) {
        RecipeDto result = new RecipeDto();
        result.setName(recipe.getName());
        result.setDescription(recipe.getDescription());
        result.setCategory(recipe.getCategory());
        result.setDate(recipe.getLastUpdateDate());
        result.setIngredients(ingredientsMapper.reverse().convert(recipe.getIngredients()));
        result.setDirections(directionsMapper.reverse().convert(recipe.getDirections()));
        return result;
    }
}
