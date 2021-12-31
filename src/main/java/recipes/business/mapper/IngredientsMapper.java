package recipes.business.mapper;

import com.google.common.base.Converter;
import org.springframework.stereotype.Component;
import recipes.persistence.entity.Ingredient;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class IngredientsMapper extends Converter<String[], List<Ingredient>> {

    @Override
    @Nonnull
    protected List<Ingredient> doForward(@Nonnull String[] ingredients) {
        return Arrays.stream(ingredients)
                .map(Ingredient::fromName)
                .collect(toList());
    }

    @Override
    @Nonnull
    protected String[] doBackward(@Nonnull List<Ingredient> ingredients) {
        return ingredients.stream().map(Ingredient::getName).toArray(String[]::new);
    }
}
