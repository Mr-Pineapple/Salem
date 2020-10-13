package club.mcmodding.salem.recipes;

import club.mcmodding.salem.Salem;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Recipes {

    public static final RecipeType<SpellRecipe> SPELL_TYPE = registerRecipeType("spell");
    public static final RecipeSerializer<SpellRecipe> SPELL_RECIPE_SERIALIZER = registerRecipeSerializer("spell", SpellRecipe.Serializer.INSTANCE);

    private static <T extends Recipe<?>> RecipeType<T> registerRecipeType(String name) {
        return Registry.register(Registry.RECIPE_TYPE, new Identifier(Salem.MOD_ID, name), new RecipeType<T>() {
            @Override
            public String toString() {
                return new Identifier(Salem.MOD_ID, name).toString();
            }
        });
    }

    private static <T extends Recipe<?>> RecipeSerializer<T> registerRecipeSerializer(String name, RecipeSerializer<T> type) {
        return Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(Salem.MOD_ID, name), type);
    }

    public static void init() {}

}
