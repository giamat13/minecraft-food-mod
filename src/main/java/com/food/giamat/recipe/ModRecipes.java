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

    public static void initialize() {
        // Registration happens in the static initializer above; this just ensures the
        // class is loaded during mod init.
    }
}
