package club.mcmodding.salem.recipes;

import club.mcmodding.salem.blocks.spell_cauldron.SpellCauldronInventory;
import com.google.gson.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicReference;

public class SpellRecipe implements Recipe<SpellCauldronInventory> {

    private final DefaultedList<Ingredient> inputs;
    private final HashMap<Item, Identifier> outputs;

    private final Identifier id;

    public SpellRecipe(DefaultedList<Ingredient> inputs, HashMap<Item, Identifier> outputs, Identifier id) {
        this.inputs = inputs;
        this.outputs = outputs;

        this.id = id;
    }

    public DefaultedList<Ingredient> getInputs() {
        return inputs;
    }

    public HashMap<Item, Identifier> getOutputs() {
        return outputs;
    }

    @Override
    public boolean matches(SpellCauldronInventory inv, World world) {
        return inv.size() >= 20;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    public static class Serializer implements RecipeSerializer<SpellRecipe> {

        @Override
        public SpellRecipe read(Identifier id, JsonObject json) {
            PotteryRecipeJSONFormat recipeJSON = new Gson().fromJson(json, PotteryRecipeJSONFormat.class);

            if (recipeJSON.ingredients == null) throw new JsonSyntaxException("Spell recipe \"" + id.toString() + "\" missing \"ingredients\" attribute.");
            if (recipeJSON.outputs == null) throw new JsonSyntaxException("Spell recipe \"" + id.toString() + "\" missing \"outputs\" attribute.");

            DefaultedList<Ingredient> ingredients = DefaultedList.of();
            JsonArray inputsJsonArray = JsonHelper.asArray(recipeJSON.ingredients, "ingredients");

            for (int i = 0; i < inputsJsonArray.size(); i++) {
                Ingredient ingredient = Ingredient.fromJson(inputsJsonArray.get(i));
                if (!ingredient.isEmpty()) {
                    ingredients.add(ingredient);
                }
            }

            HashMap<Item, Identifier> outputs = new HashMap<>();

            boolean containsEmpty = false;
            for (Entry<String, JsonElement> object : recipeJSON.outputs.entrySet()) {
                AtomicReference<Item> item = new AtomicReference<>(Items.AIR);
                Registry.ITEM.getOrEmpty(new Identifier(object.getKey())).ifPresent(item::set);

                if (item.get() == Items.AIR) containsEmpty = true;
                outputs.put(item.get(), new Identifier(object.getValue().getAsString()));
            }
            if (!containsEmpty) throw new JsonSyntaxException("Spell recipe \"" + id.toString() + "\" missing empty \"\" outputs asstribute.");

            return new SpellRecipe(ingredients, outputs, id);
        }

        @Override
        public void write(PacketByteBuf buf, SpellRecipe recipe) {
            buf.writeInt(recipe.getInputs().size());
            for (Ingredient ingredient : recipe.getInputs()) ingredient.write(buf);

            buf.writeInt(recipe.getOutputs().size());
            for (Entry<Item, Identifier> entry : recipe.getOutputs().entrySet()) {
                buf.writeItemStack(new ItemStack(entry.getKey()));
                buf.writeString(entry.getValue().toString());
            }
        }

        @Override
        public SpellRecipe read(Identifier id, PacketByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.of();
            int inputsSize = buf.readInt();
            for (int i = 0; i < inputsSize; i++) inputs.add(Ingredient.fromPacket(buf));

            HashMap<Item, Identifier> outputs = new HashMap<>();
            int outputsSize = buf.readInt();
            for (int i = 0; i < outputsSize; i++) outputs.put(buf.readItemStack().getItem(), new Identifier(buf.readString()));

            return new SpellRecipe(inputs, outputs, id);
        }

        class PotteryRecipeJSONFormat {
            JsonObject ingredients;
            JsonObject outputs;
        }

        public static final Serializer INSTANCE = new Serializer();

    }

    @Override
    public RecipeType<?> getType() {
        return Recipes.SPELL_TYPE;
    }

    @Override
    public Identifier getId() {
        return id;
    }


    // Unused
    @Override
    public ItemStack getOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack craft(SpellCauldronInventory inv) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean fits(int width, int height) {
        return false;
    }

}
