package gregtech.common.tileentities.machines.multi.compressor;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlock;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.onElementPass;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.transpose;
import static gregtech.api.enums.GT_HatchElement.Energy;
import static gregtech.api.enums.GT_HatchElement.InputBus;
import static gregtech.api.enums.GT_HatchElement.Maintenance;
import static gregtech.api.enums.GT_HatchElement.OutputBus;
import static gregtech.api.enums.GT_Values.AuthorFourIsTheNumber;
import static gregtech.api.enums.GT_Values.Ollie;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_MULTI_COMPRESSOR;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_MULTI_COMPRESSOR_ACTIVE;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_MULTI_COMPRESSOR_ACTIVE_GLOW;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_MULTI_COMPRESSOR_COOLING;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_MULTI_COMPRESSOR_COOLING_GLOW;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_MULTI_COMPRESSOR_GLOW;
import static gregtech.api.util.GT_StructureUtility.buildHatchAdder;
import static gregtech.api.util.GT_StructureUtility.ofFrame;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;

import gregtech.api.GregTech_API;
import gregtech.api.enums.HeatingCoilLevel;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_ExtendedPowerMultiBlockBase;
import gregtech.api.multitileentity.multiblock.casing.Glasses;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_Multiblock_Tooltip_Builder;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import gregtech.api.util.shutdown.SimpleShutDownReason;
import gregtech.common.blocks.GT_Block_Casings8;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;

public class GT_MetaTileEntity_NeutroniumCompressor
    extends GT_MetaTileEntity_ExtendedPowerMultiBlockBase<GT_MetaTileEntity_NeutroniumCompressor>
    implements ISurvivalConstructable {

    private static final String STRUCTURE_PIECE_MAIN = "main";
    private static final IStructureDefinition<GT_MetaTileEntity_NeutroniumCompressor> STRUCTURE_DEFINITION = StructureDefinition
        .<GT_MetaTileEntity_NeutroniumCompressor>builder()
        .addShape(
            STRUCTURE_PIECE_MAIN,
            // spotless:off
            transpose(new String[][]{
                {"  CEEEEEC  "," CE     EC ","CE       EC","E         E","E         E","E         E","E         E","E         E","CE       EC"," CE     EC ","  CEEEEEC  "},
                {" CE     EC ","C  BBBBB  C","E BBBBBBB E"," BBCCCCCBB "," BBCBBBCBB "," BBCCCCCBB "," BBCBBBCBB "," BBCCCCCBB ","E BBBBBBB E","C  BBBBB  C"," CE     EC "},
                {"CE       EC","E BBBBBBB E"," B       B "," D       D "," B       B "," B       B "," D       D "," B       B "," B       B ","E BDDDBDB E","CE       EC"},
                {"E         E"," BBAAAAABB "," B       B "," D       D "," B       B "," D       D "," D       D "," B       B "," B       B "," BBDBBBDBB ","E         E"},
                {"E         E"," BBAAAAABB "," B       B "," D       D "," B       B "," B       B "," D       D "," D       D "," D       D "," BDDBDDDDB ","E         E"},
                {"E         E"," BBAAAAABB "," D       D "," D       D "," D       D "," B       B "," B       B "," B       B "," B       B "," BBDBBBBDB ","E         E"},
                {"E         E"," BBAAAAABB "," B       B "," B       B "," D       D "," B       B "," B       B "," D       D "," B       B "," BBDBBDBBB ","E         E"},
                {"E         E"," BBAAAAABB "," B       B "," D       D "," D       D "," B       B "," D       D "," D       D "," D       D "," BBBBBDDDB ","E         E"},
                {"CE       EC","E BBBBBBB E"," B       B "," D       D "," B       B "," B       B "," D       D "," B       B "," B       B ","E BDDBBBB E","CE       EC"},
                {" CE     EC ","C  BB~BB  C","E BBBBBBB E"," BBBBBBBBB "," BBBBBBBBB "," BBBBBBBBB "," BBBBBBBBB "," BBBBBBBBB ","E BBBBBBB E","C  BBBBB  C"," CE     EC "},
                {"  CEEEEEC  "," CE     EC ","CE       EC","E         E","E         E","E         E","E         E","E         E","CE       EC"," CE     EC ","  CEEEEEC  "}
            }))
            //spotless:on
        .addElement('A', Glasses.chainAllGlasses())
        .addElement(
            'B',
            buildHatchAdder(GT_MetaTileEntity_NeutroniumCompressor.class)
                .atLeast(InputBus, OutputBus, Maintenance, Energy)
                .casingIndex(((GT_Block_Casings8) GregTech_API.sBlockCasings8).getTextureIndex(5))
                .dot(1)
                .buildAndChain(
                    onElementPass(
                        GT_MetaTileEntity_NeutroniumCompressor::onCasingAdded,
                        ofBlock(GregTech_API.sBlockCasings8, 5))))
        .addElement('C', ofBlock(GregTech_API.sBlockCasings8, 10))
        .addElement('D', ofBlock(GregTech_API.sBlockCasings4, 1))
        .addElement('E', ofFrame(Materials.NaquadahAlloy))
        .build();

    private HeatingCoilLevel heatLevel;
    private int coolingCounter = 0;

    private int heat = 0;
    private boolean cooling = false;

    public GT_MetaTileEntity_NeutroniumCompressor(final int aID, final String aName, final String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GT_MetaTileEntity_NeutroniumCompressor(String aName) {
        super(aName);
    }

    @Override
    public IStructureDefinition<GT_MetaTileEntity_NeutroniumCompressor> getStructureDefinition() {
        return STRUCTURE_DEFINITION;
    }

    @Override
    public boolean isCorrectMachinePart(ItemStack aStack) {
        return true;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_NeutroniumCompressor(this.mName);
    }

    @Override
    public void onValueUpdate(byte aValue) {
        boolean oCooling = cooling;
        cooling = (aValue & 1) == 1;
        if (oCooling != cooling) getBaseMetaTileEntity().issueTextureUpdate();
    }

    @Override
    public byte getUpdateData() {
        return (byte) (cooling ? 1 : 0);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity baseMetaTileEntity, ForgeDirection side, ForgeDirection aFacing,
        int colorIndex, boolean aActive, boolean redstoneLevel) {
        ITexture[] rTexture;
        if (side == aFacing) {
            if (cooling) {
                rTexture = new ITexture[] {
                    Textures.BlockIcons
                        .getCasingTextureForId(GT_Utility.getCasingTextureIndex(GregTech_API.sBlockCasings2, 0)),
                    TextureFactory.builder()
                        .addIcon(OVERLAY_FRONT_MULTI_COMPRESSOR_COOLING)
                        .extFacing()
                        .build(),
                    TextureFactory.builder()
                        .addIcon(OVERLAY_FRONT_MULTI_COMPRESSOR_COOLING_GLOW)
                        .extFacing()
                        .glow()
                        .build() };
            } else if (aActive) {
                rTexture = new ITexture[] {
                    Textures.BlockIcons
                        .getCasingTextureForId(GT_Utility.getCasingTextureIndex(GregTech_API.sBlockCasings2, 0)),
                    TextureFactory.builder()
                        .addIcon(OVERLAY_FRONT_MULTI_COMPRESSOR_ACTIVE)
                        .extFacing()
                        .build(),
                    TextureFactory.builder()
                        .addIcon(OVERLAY_FRONT_MULTI_COMPRESSOR_ACTIVE_GLOW)
                        .extFacing()
                        .glow()
                        .build() };
            } else {
                rTexture = new ITexture[] {
                    Textures.BlockIcons
                        .getCasingTextureForId(GT_Utility.getCasingTextureIndex(GregTech_API.sBlockCasings2, 0)),
                    TextureFactory.builder()
                        .addIcon(OVERLAY_FRONT_MULTI_COMPRESSOR)
                        .extFacing()
                        .build(),
                    TextureFactory.builder()
                        .addIcon(OVERLAY_FRONT_MULTI_COMPRESSOR_GLOW)
                        .extFacing()
                        .glow()
                        .build() };
            }
        } else {
            rTexture = new ITexture[] { Textures.BlockIcons
                .getCasingTextureForId(GT_Utility.getCasingTextureIndex(GregTech_API.sBlockCasings2, 0)) };
        }
        return rTexture;
    }

    @Override
    protected GT_Multiblock_Tooltip_Builder createTooltip() {
        GT_Multiblock_Tooltip_Builder tt = new GT_Multiblock_Tooltip_Builder();
        tt.addMachineType("Compressor/Neutronium Compressor")
            .addInfo("Controller Block for the Neutronium Compressor")
            .addInfo(EnumChatFormatting.BOLD + "This multi uses the HIP Compression mechanics!")
            .addInfo("Capable of compressing matter into " + EnumChatFormatting.GOLD + "singularities")
            .addInfo(AuthorFourIsTheNumber + EnumChatFormatting.RESET + " & " + Ollie)
            .addSeparator()
            .beginStructureBlock(11, 11, 11, true)
            .addController("Front Center")
            .addCasingInfoMin("Solid Steel Machine Casing", 85, false)
            .addInputBus("Any Solid Steel Casing", 1)
            .addOutputBus("Any Solid Steel Casing", 1)
            .addInputHatch("Any Solid Steel Casing", 1)
            .addOutputHatch("Any Solid Steel Casing", 1)
            .addEnergyHatch("Any Solid Steel Casing", 1)
            .addMaintenanceHatch("Any Solid Steel Casing", 1)
            .toolTipFinisher("GregTech");
        return tt;
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        buildPiece(STRUCTURE_PIECE_MAIN, stackSize, hintsOnly, 5, 9, 1);
    }

    @Override
    public int survivalConstruct(ItemStack stackSize, int elementBudget, ISurvivalBuildEnvironment env) {
        if (mMachine) return -1;
        return survivialBuildPiece(STRUCTURE_PIECE_MAIN, stackSize, 5, 9, 1, elementBudget, env, false, true);
    }

    private int mCasingAmount;

    void onCasingAdded() {
        mCasingAmount++;
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        setCoilLevel(HeatingCoilLevel.None);
        mCasingAmount = 0;
        mEnergyHatches.clear();

        if (!checkPiece(STRUCTURE_PIECE_MAIN, 5, 9, 1)) return false;
        if (mCasingAmount < 0) return false;

        // All checks passed!
        return true;
    }

    @Override
    protected void setProcessingLogicPower(ProcessingLogic logic) {
        logic.setAvailableVoltage(GT_Utility.roundUpVoltage(this.getMaxInputVoltage()));
        logic.setAvailableAmperage(1L);
    }

    @Override
    public void getWailaNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag, World world, int x, int y,
        int z) {
        super.getWailaNBTData(player, tile, tag, world, x, y, z);
        tag.setInteger("heat", heat);
        tag.setBoolean("cooling", cooling);
    }

    @Override
    public void getWailaBody(ItemStack itemStack, List<String> currentTip, IWailaDataAccessor accessor,
        IWailaConfigHandler config) {
        super.getWailaBody(itemStack, currentTip, accessor, config);
        final NBTTagCompound tag = accessor.getNBTData();
        if (tag.getBoolean("cooling")) currentTip.add(
            "HIP Heat: " + EnumChatFormatting.RED
                + EnumChatFormatting.BOLD
                + tag.getInteger("heat")
                + "%"
                + EnumChatFormatting.RESET);
        else currentTip.add(
            "HIP Heat: " + EnumChatFormatting.AQUA
                + EnumChatFormatting.BOLD
                + tag.getInteger("heat")
                + "%"
                + EnumChatFormatting.RESET);
    }

    @Override
    protected ProcessingLogic createProcessingLogic() {
        return new ProcessingLogic() {

            @NotNull
            @Override
            protected Stream<GT_Recipe> findRecipeMatches(@Nullable RecipeMap<?> map) {
                Stream<GT_Recipe> compressorRecipes = RecipeMaps.compressorRecipes.findRecipeQuery()
                    .items(inputItems)
                    .cachedRecipe(lastRecipe)
                    .findAll();
                /*
                 * if (neutroniumEnabled) {
                 * Stream<GT_Recipe> neutroniumRecipes = RecipeMaps.neutroniumCompressorRecipes.findRecipeQuery()
                 * .items(inputItems)
                 * .cachedRecipe(lastRecipe)
                 * .findAll();
                 * compressorRecipes = Stream.concat(compressorRecipes, neutroniumRecipes);
                 * }
                 */
                return compressorRecipes;
            }
        }.setSpeedBonus(1F / 2F);
        // .setMaxParallelSupplier(this::getMaxParallelRecipes);
    }

    @Override
    public boolean onRunningTick(ItemStack aStack) {
        if (cooling) {
            stopMachine(SimpleShutDownReason.ofCritical("overheated"));
        } else {
            heat = heat + 1;
            if (heat >= 100) {
                heat = 100;
                cooling = true;
            }
        }
        return super.onRunningTick(aStack);
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPostTick(aBaseMetaTileEntity, aTick);

        if (aBaseMetaTileEntity.isServerSide()) {
            // Updates every 10 sec
            if (mUpdate <= -150) mUpdate = 50;
        }

        if (coolingCounter >= 4) {
            coolingCounter = 0;
            heat -= 1;
            if (heat <= 0) {
                heat = 0;
                cooling = false;
            }
        } else coolingCounter += 1;

    }

    public int getMaxParallelRecipes() {
        return (8 * GT_Utility.getTier(this.getMaxInputVoltage()));
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return RecipeMaps.compressorRecipes;
    }

    @Nonnull
    @Override
    public Collection<RecipeMap<?>> getAvailableRecipeMaps() {
        return Arrays.asList(RecipeMaps.compressorRecipes, RecipeMaps.neutroniumCompressorRecipes);
    }

    @Override
    public int getMaxEfficiency(ItemStack aStack) {
        return 10000;
    }

    @Override
    public int getDamageToComponent(ItemStack aStack) {
        return 0;
    }

    @Override
    public boolean explodesOnComponentBreak(ItemStack aStack) {
        return false;
    }

    @Override
    public boolean supportsVoidProtection() {
        return true;
    }

    @Override
    public boolean supportsBatchMode() {
        return true;
    }

    @Override
    public boolean supportsInputSeparation() {
        return true;
    }

    @Override
    public boolean supportsSingleRecipeLocking() {
        return true;
    }

    public HeatingCoilLevel getCoilLevel() {
        return heatLevel;
    }

    public void setCoilLevel(HeatingCoilLevel aCoilLevel) {
        heatLevel = aCoilLevel;
    }
}
