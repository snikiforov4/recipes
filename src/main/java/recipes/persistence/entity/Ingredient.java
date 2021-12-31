package recipes.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "ingredients")
@Data
@NoArgsConstructor
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    long id;

    @NonNull
    @Column(name = "name", nullable = false)
    String name;

    public static Ingredient fromName(String name) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(name);
        return ingredient;
    }
}
