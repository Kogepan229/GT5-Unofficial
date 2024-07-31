package gregtech.loaders.postload.recipes;

import com.github.technus.tectech.util.ItemStackLong;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GT_OreDictUnificator;

import static gregtech.api.recipe.RecipeMaps.neutroniumCompressorRecipes;
import static gregtech.api.util.GT_RecipeBuilder.SECONDS;

public class NeutroniumCompressorRecipes implements Runnable {

    @Override
    public void run() {
        GT_Values.RA.stdBuilder()
            .special(new ItemStackLong(GT_OreDictUnificator.get(OrePrefixes.block, Materials.Iron, 1), 15000))
            .itemOutputs(ItemList.Sensor_IV.get(1))
            .duration(3 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(neutroniumCompressorRecipes);
    }


}
