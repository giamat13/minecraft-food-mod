package com.food.giamat.recipe;

import com.food.giamat.FoodBygiamat;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;

public class ModRecipes {

    public static final RecipeSerializer<DoughRecipe> DOUGH_SERIALIZER = Registry.register(
            BuiltInRegistries.RECIPE_SERIALIZER,
            Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "dough"),
            new RecipeSerializer<>(DoughRecipe.MAP_CODEC, DoughRecipe.STREAM_CODEC));

    public static final RecipeSerializer<SaltRecipe> SALT_SERIALIZER = Registry.register(
            BuiltInRegistries.RECIPE_SERIALIZER,
            Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "salt"),
            new RecipeSerializer<>(SaltRecipe.MAP_CODEC, SaltRecipe.STREAM_CODEC));

    public static final RecipeSerializer<GelatinRecipe> GELATIN_SERIALIZER = Registry.register(
            BuiltInRegistries.RECIPE_SERIALIZER,
            Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "gelatin"),
            new RecipeSerializer<>(GelatinRecipe.MAP_CODEC, GelatinRecipe.STREAM_CODEC));

    public static final RecipeSerializer<ShokoRecipe> SHOKO_SERIALIZER = Registry.register(
            BuiltInRegistries.RECIPE_SERIALIZER,
            Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "shoko"),
            new RecipeSerializer<>(ShokoRecipe.MAP_CODEC, ShokoRecipe.STREAM_CODEC));

    public static final RecipeSerializer<SausageRecipe> SAUSAGE_SERIALIZER = Registry.register(
            BuiltInRegistries.RECIPE_SERIALIZER,
            Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "sausage"),
            new RecipeSerializer<>(SausageRecipe.MAP_CODEC, SausageRecipe.STREAM_CODEC));

    public static final RecipeSerializer<PizzaToppingRecipe> PIZZA_TOPPING_SERIALIZER = Registry.register(
            BuiltInRegistries.RECIPE_SERIALIZER,
            Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "pizza_topping"),
            new RecipeSerializer<>(PizzaToppingRecipe.MAP_CODEC, PizzaToppingRecipe.STREAM_CODEC));

    public static final RecipeSerializer<SusStewFoodRecipe> SUS_STEW_FOOD_SERIALIZER = Registry.register(
            BuiltInRegistries.RECIPE_SERIALIZER,
            Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "sus_stew_food"),
            new RecipeSerializer<>(SusStewFoodRecipe.MAP_CODEC, SusStewFoodRecipe.STREAM_CODEC));

    public static final RecipeSerializer<CreamRecipe> CREAM_SERIALIZER = Registry.register(
            BuiltInRegistries.RECIPE_SERIALIZER,
            Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "cream"),
            new RecipeSerializer<>(CreamRecipe.MAP_CODEC, CreamRecipe.STREAM_CODEC));

    public static final RecipeSerializer<CombinedFoodRecipe> COMBINED_FOOD_SERIALIZER = Registry.register(
            BuiltInRegistries.RECIPE_SERIALIZER,
            Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "combined_food"),
            new RecipeSerializer<>(CombinedFoodRecipe.MAP_CODEC, CombinedFoodRecipe.STREAM_CODEC));

    public static final RecipeSerializer<RamenRecipe> RAMEN_SERIALIZER = Registry.register(
            BuiltInRegistries.RECIPE_SERIALIZER,
            Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "ramen"),
            new RecipeSerializer<>(RamenRecipe.MAP_CODEC, RamenRecipe.STREAM_CODEC));

    public static void initialize() {}
}
