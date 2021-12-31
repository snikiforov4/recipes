package recipes.business.service;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import recipes.business.ext.ListsElementsRemover;
import recipes.business.mapper.RecipeMapper;
import recipes.business.security.UserDetailsImpl;
import recipes.persistence.entity.Direction;
import recipes.persistence.entity.Ingredient;
import recipes.persistence.entity.Recipe;
import recipes.persistence.repository.RecipeRepository;
import recipes.presentation.dto.RecipeDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RecipeService {

    private final RecipeRepository repository;
    private final RecipeMapper recipeMapper;

    public Optional<RecipeDto> getRecipe(long id) {
        return repository.findById(id).map(recipeMapper.reverse()::convert);
    }

    public long saveRecipe(RecipeDto recipeDto, UserDetailsImpl userDetails) {
        Objects.requireNonNull(recipeDto, "recipe");
        Recipe recipeEntity = Objects.requireNonNull(recipeMapper.convert(recipeDto));
        recipeEntity.setLastUpdateDate(LocalDateTime.now());
        recipeEntity.setUserId(userDetails.getUserId());
        Recipe savedEntity = repository.save(recipeEntity);
        return savedEntity.getId();
    }

    public void deleteRecipeById(long id, UserDetailsImpl userDetails)
            throws ReceiptNotFoundException, NotOwnerException {
        Recipe recipe = repository.findById(id).orElseThrow(ReceiptNotFoundException::new);
        checkUserIsOwnerOfRecipe(recipe, userDetails);
        repository.delete(recipe);
    }

    private void checkUserIsOwnerOfRecipe(Recipe recipe, UserDetailsImpl userDetails) {
        if (recipe.getUserId() != userDetails.getUserId()) {
            throw new NotOwnerException();
        }
    }

    public void updateRecipe(long id, RecipeDto recipeDto, UserDetailsImpl userDetails) throws ReceiptNotFoundException {
        Objects.requireNonNull(recipeDto, "recipe");
        Recipe recipe = repository.findById(id).orElseThrow(ReceiptNotFoundException::new);
        checkUserIsOwnerOfRecipe(recipe, userDetails);
        recipe.setName(recipeDto.getName());
        recipe.setDescription(recipeDto.getDescription());
        recipe.setCategory(recipeDto.getCategory());
        recipe.setLastUpdateDate(LocalDateTime.now());
        updateIngredients(recipe.getIngredients(), recipeDto.getIngredients());
        updateDirections(recipe.getDirections(), recipeDto.getDirections());
        repository.save(recipe);
    }

    private void updateIngredients(List<Ingredient> ingredients, String[] ingredientsDto) {
        for (int i = 0; i < ingredientsDto.length; i++) {
            String ingredientName = ingredientsDto[i];
            if (ingredients.size() <= i) {
                ingredients.add(Ingredient.fromName(ingredientName));
            } else {
                ingredients.get(i).setName(ingredientName);
            }
        }
        if (ingredients.size() > ingredientsDto.length) {
            ListsElementsRemover.removeStartingFromIndex(ingredients, ingredientsDto.length);
        }
    }

    private void updateDirections(List<Direction> directions, String[] directionsDto) {
        for (int i = 0; i < directionsDto.length; i++) {
            String directionDescription = directionsDto[i];
            if (directions.size() <= i) {
                Direction direction = new Direction();
                direction.setDescription(directionDescription);
                direction.setOrderNumber(i);
                directions.add(direction);
            } else {
                directions.get(i).setDescription(directionDescription);
            }
        }
        if (directions.size() > directionsDto.length) {
            ListsElementsRemover.removeStartingFromIndex(directions, directionsDto.length);
        }
    }

    public List<RecipeDto> searchByName(String name) {
        List<Recipe> entities = repository.findAllByNameContainingIgnoreCaseOrderByLastUpdateDateDesc(name);
        return Lists.newArrayList(recipeMapper.reverse().convertAll(entities));
    }

    public List<RecipeDto> searchByCategory(String category) {
        List<Recipe> entities = repository.findAllByCategoryIgnoreCaseOrderByLastUpdateDateDesc(category);
        return Lists.newArrayList(recipeMapper.reverse().convertAll(entities));
    }
}
