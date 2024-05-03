package gregtech.common.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import gregtech.api.util.GT_LanguageManager;

/**
 * The casings are split into separate files because they are registered as regular blocks, and a regular block can have
 * 16 subtypes at most.
 */
public class GT_Item_Casings9 extends GT_Item_Casings_Abstract {

    public GT_Item_Casings9(Block block) {
        super(block);
    }

    @Override
    public void addInformation(ItemStack aStack, EntityPlayer aPlayer, List<String> aList, boolean aF3_H) {
        // Add tooltip info if it was given
        String localizedTooltip = GT_LanguageManager.getTranslation(aStack.getUnlocalizedName() + ".tooltip");
        // getTranslation returns the key if no translation was found, but this just means
        // no tooltip was set.
        if (localizedTooltip.startsWith("gt.")) {
            aList.add(localizedTooltip);
        }
        super.addInformation(aStack, aPlayer, aList, aF3_H);
    }
}
