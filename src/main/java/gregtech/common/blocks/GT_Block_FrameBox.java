package gregtech.common.blocks;

import static gregtech.api.enums.GT_Values.W;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_LanguageManager;
import gregtech.common.render.GT_Renderer_Block;

public class GT_Block_FrameBox extends Block {

    protected final String mUnlocalizedName;

    private static final String DOT_NAME = ".name";
    private static final String DOT_TOOLTIP = ".tooltip";

    public GT_Block_FrameBox(String aName) {
        super(Material.rock);
        this.isBlockContainer = true;
        setBlockName(mUnlocalizedName = aName);
        GameRegistry.registerBlock(this, GT_Item_Frames.class, getUnlocalizedName());
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + W + ".name", "Any Sub Block of this one");

        for (int i = 1; i < GregTech_API.sGeneratedMaterials.length; i++) {
            if (GregTech_API.sGeneratedMaterials[i] != null) {
                GT_LanguageManager.addStringLocalization(
                    getUnlocalizedName() + "." + i + DOT_NAME,
                    GT_LanguageManager.i18nPlaceholder ? getLocalizedNameFormat(GregTech_API.sGeneratedMaterials[i])
                        : getLocalizedName(GregTech_API.sGeneratedMaterials[i]));
                GT_LanguageManager.addStringLocalization(
                    getUnlocalizedName() + "." + i + DOT_TOOLTIP,
                    GregTech_API.sGeneratedMaterials[i].getToolTip());
            }
        }

        // TODO: Register oredict for this frame item here, since we don't want to use the TE version for crafting

    }

    public String getLocalizedNameFormat(Materials aMaterial) {
        return switch (aMaterial.mName) {
            case "InfusedAir", "InfusedDull", "InfusedEarth", "InfusedEntropy", "InfusedFire", "InfusedOrder", "InfusedVis", "InfusedWater" -> "%material Infused Stone";
            case "Vermiculite", "Bentonite", "Kaolinite", "Talc", "BasalticMineralSand", "GraniticMineralSand", "GlauconiteSand", "CassiteriteSand", "GarnetSand", "QuartzSand", "Pitchblende", "FullersEarth" -> "%material";
            default -> "%material" + OrePrefixes.ore.mLocalizedMaterialPost;
        };
    }

    @Override
    public String getUnlocalizedName() {
        return mUnlocalizedName;
    }

    public String getLocalizedName(Materials aMaterial) {
        return aMaterial.getDefaultLocalizedNameForItem(getLocalizedNameFormat(aMaterial));
    }

    @Override
    public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float subX,
        float subY, float subZ) {
        // TODO: Check if held item is a cover, and if so convert this into a TileEntity framebox and add the cover.
        // GT_Utility.sendChatToPlayer(player, "Activating frame box: " + mMaterial.mLocalizedName);
        return super.onBlockActivated(worldIn, x, y, z, player, side, subX, subY, subZ);
    }

    @Override
    public int getRenderType() {
        if (GT_Renderer_Block.INSTANCE == null) {
            return super.getRenderType();
        }
        return GT_Renderer_Block.INSTANCE.mRenderID;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item aItem, CreativeTabs aTab, List<ItemStack> aList) {
        for (int i = 0; i < GregTech_API.sGeneratedMaterials.length; i++) {
            Materials tMaterial = GregTech_API.sGeneratedMaterials[i];
            // If material is not null and has a frame box item associated with it
            if ((tMaterial != null) && ((tMaterial.mTypes & 0x02) != 0)) {
                aList.add(new ItemStack(aItem, 1, i));
            }
        }
    }

    @Override
    public IIcon getIcon(IBlockAccess worldIn, int x, int y, int z, int side) {
        // TODO: I think this doesn't actually apply the color of the frame box material.
        // Not sure how we can get around this without statically generating all the textures.
        // Maybe need to bite the bullet and also spawn a TE in the same location that can render the proper
        // texture. I don't know.
        // return mMaterial.mIconSet.mTextures[OrePrefixes.frameGt.mTextureIndex].getIcon();
        return super.getIcon(worldIn, x, y, z, side);
    }
}
