package com.food.giamat.recipe;

import com.food.giamat.FoodBygiamat;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {

    public static final RecipeSerializer<DoughRecipe> DOUGH_SERIALIZER = Registry.register(
            Registries.RECIPE_SERIALIZER,
            Identifier.of(FoodBygiamat.MOD_ID, "dough"),
            new SpecialCraftingRecipe.SpecialRecipeSerializer<>(DoughRecipe::new));

    public static final RecipeSerializer<SaltRecipe> SALT_SERIALIZER = Registry.register(
            Registries.RECIPE_SERIALIZER,
            Identifier.of(FoodBygiamat.MOD_ID, "salt"),
            new SpecialCraftingRecipe.SpecialRecipeSerializer<>(SaltRecipe::new));

    public static final RecipeSerializer<GelatinRecipe> GELATIN_SERIALIZER = Registry.register(
            Registries.RECIPE_SERIALIZER,
            Identifier.of(FoodBygiamat.MOD_ID, "gelatin"),
            new SpecialCraftingRecipe.SpecialRecipeSerializer<>(GelatinRecipe::new));

    public static final RecipeSerializer<ShokoRecipe> SHOKO_SERIALIZER = Registry.register(
            Registries.RECIPE_SERIALIZER,
            Identifier.of(FoodBygiamat.MOD_ID, "shoko"),
            new SpecialCraftingRecipe.SpecialRecipeSerializer<>(ShokoRecipe::new));

    public static final RecipeSerializer<SausageRecipe> SAUSAGE_SERIALIZER = Registry.register(
            Registries.RECIPE_SERIALIZER,
            Identifier.of(FoodBygiamat.MOD_ID, "sausage"),
            new SpecialCraftingRecipe.SpecialRecipeSerializer<>(SausageRecipe::new));

    public static final RecipeSerializer<PizzaToppingRecipe> PIZZA_TOPPING_SERIALIZER = Registry.register(
            Registries.RECIPE_SERIALIZER,
            Identifier.of(FoodBygiamat.MOD_ID, "pizza_topping"),
            new SpecialCraftingRecipe.SpecialRecipeSerializer<>(PizzaToppingRecipe::new));

    public static final RecipeSerializer<SusStewFoodRecipe> SUS_STEW_FOOD_SERIALIZER = Registry.register(
            Registries.RECIPE_SERIALIZER,
            Identifier.of(FoodBygiamat.MOD_ID, "sus_stew_food"),
            new SpecialCraftingRecipe.SpecialRecipeSerializer<>(SusStewFoodRecipe::new));

    public static final RecipeSerializer<CreamRecipe> CREAM_SERIALIZER = Registry.register(
            Registries.RECIPE_SERIALIZER,
            Identifier.of(FoodBygiamat.MOD_ID, "cream"),
            new SpecialCraftingRecipe.SpecialRecipeSerializer<>(CreamRecipe::new));

    public static final RecipeSerializer<CombinedFoodRecipe> COMBINED_FOOD_SERIALIZER = Registry.register(
            Registries.RECIPE_SERIALIZER,
            Identifier.of(FoodBygiamat.MOD_ID, "combined_food"),
            new SpecialCraftingRecipe.SpecialRecipeSerializer<>(CombinedFoodRecipe::new));

    public static void initialize() {
        // Registration happens in the static initializer above; this just ensures the
        // class is loaded during mod init.
    }
}
