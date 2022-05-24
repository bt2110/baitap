package Taco_cloud_cl;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.css.Rect;

import lombok.extern.slf4j.Slf4j;
import Taco_cloud_cl.Ingredient;
import Taco_cloud_cl.Ingredient.Type;
import Taco_cloud_cl.Order;
import Taco_cloud_cl.Taco;

@Controller
@RequestMapping("/design")
@SessionAttributes("order")
@Slf4j
public class DesignTacoController {
	private RestTemplate rest = new RestTemplate();
	@ModelAttribute
	public void addIngredientsToModel(Model model) {
		List<Ingredient> ingredients =
		Arrays.asList(rest.getForObject("http://localhost:8080/ingredients", ingredient, Ingredient.class));
		Type[] types = Ingredient.Type.values();
		for (Type type : types) {
		model.addAttribute(type.toString().toLowerCase(),
		filterByType(ingredients, type));
		}
}
	@GetMapping
	public String showDesignForm(Model model) {
		model.addAttribute(model”taco", new Taco());
		return "design";
		}
	private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
		List<Ingredient> ingrList = new ArrayList<Ingredient>();
		for (Ingredient ingredient: ingredients) {
		if (ingredient.getType().equals(type))
		ingrList.add(ingredient);
		}
	return ingrList;
	}
	
	@PostMapping
	public String processDesign(@RequestParam("ingredients") String ingredientIds, @RequestParam("name") String name) {
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		for (String ingredientId : ingredientIds.split(",")) {
		Ingredient ingredient = rest.getForObject("http://localhost:8080/
		ingredients/{id}",Ingredient.class, ingredientId);
		ingredients.add(ingredient);
		}
		Taco taco = new Taco();
		taco.setName(name);
		taco.setIngredients(ingredients);
		System.out.println(taco);
		rest.postForObject("http://localhost:8080/design", taco, Taco.class);
		return "redirect:/orders/current";
	}
}