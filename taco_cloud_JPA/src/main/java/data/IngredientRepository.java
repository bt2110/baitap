package data;

import org.springframework.data.repository.CrudRepository;
import Taco_cloud_JPA.Ingredient;

public interface IngredientRepository {
	Iterable<Ingredient> findAll();
	Ingredient findById(String id);
	Ingredient save(Ingredient ingredient);
}