package recipes.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.persistence.entity.Recipe;

import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {

    List<Recipe> findAllByCategoryIgnoreCaseOrderByLastUpdateDateDesc(String category);

    List<Recipe> findAllByNameContainingIgnoreCaseOrderByLastUpdateDateDesc(String name);

}
