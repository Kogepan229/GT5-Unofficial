package gregtech.api.util;

import static gregtech.api.util.GT_Utility.filterValidMTEs;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Tuple;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.util.Constants;

import gregtech.api.enums.ItemList;
import gregtech.api.interfaces.IDataCopyable;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.multitileentity.interfaces.IMultiTileEntity;
import gregtech.common.items.behaviors.Behaviour_DataOrb;

public class GT_Util {

    // Last broken tile entity
    public static final ThreadLocal<TileEntity> LAST_BROKEN_TILEENTITY = new ThreadLocal<>();

    public static Tuple tuple(String key, Object value) {
        return new Tuple(key, value);
    }

    public static NBTTagCompound fuseNBT(NBTTagCompound aNBT1, NBTTagCompound aNBT2) {
        if (aNBT1 == null) return aNBT2 == null ? new NBTTagCompound() : (NBTTagCompound) aNBT2.copy();
        final NBTTagCompound rNBT = (NBTTagCompound) aNBT1.copy();
        if (aNBT2 == null) return rNBT;
        for (Object tKey : aNBT2.func_150296_c /* getKeySet */())
            if (!rNBT.hasKey(tKey.toString())) rNBT.setTag(tKey.toString(), aNBT2.getTag(tKey.toString()));
        return rNBT;
    }

    /**
     * Construct a NBTTagCompound from a series of key, value pairs. Inspired from GT6.
     */
    public static NBTTagCompound makeNBT(Tuple... aTags) {
        final NBTTagCompound rNBT = new NBTTagCompound();
        for (Tuple t : aTags) {
            if (t.getSecond() == null) continue;

            if (t.getSecond() instanceof Boolean) rNBT.setBoolean(
                t.getFirst()
                    .toString(),
                (Boolean) t.getSecond());
            else if (t.getSecond() instanceof Byte) rNBT.setByte(
                t.getFirst()
                    .toString(),
                (Byte) t.getSecond());
            else if (t.getSecond() instanceof Short) rNBT.setShort(
                t.getFirst()
                    .toString(),
                (Short) t.getSecond());
            else if (t.getSecond() instanceof Integer) rNBT.setInteger(
                t.getFirst()
                    .toString(),
                (Integer) t.getSecond());
            else if (t.getSecond() instanceof Long) rNBT.setLong(
                t.getFirst()
                    .toString(),
                (Long) t.getSecond());
            else if (t.getSecond() instanceof Float) rNBT.setFloat(
                t.getFirst()
                    .toString(),
                (Float) t.getSecond());
            else if (t.getSecond() instanceof Double) rNBT.setDouble(
                t.getFirst()
                    .toString(),
                (Double) t.getSecond());
            else if (t.getSecond() instanceof String) rNBT.setString(
                t.getFirst()
                    .toString(),
                (String) t.getSecond());
            else if (t.getSecond() instanceof NBTBase) rNBT.setTag(
                t.getFirst()
                    .toString(),
                (NBTBase) t.getSecond());
            else rNBT.setString(
                t.getFirst()
                    .toString(),
                t.getSecond()
                    .toString());
        }

        return rNBT;
    }

    /**
     * Get a TileEntity
     */
    public static TileEntity getTileEntity(World aWorld, int aX, int aY, int aZ, boolean aLoadUnloadedChunks) {
        if (aLoadUnloadedChunks || aWorld.blockExists(aX, aY, aZ)) {
            TileEntity rTileEntity = aWorld.getTileEntity(aX, aY, aZ);
            if (rTileEntity instanceof IMultiTileEntity && ((IMultiTileEntity) rTileEntity).isDead()) return null;
            if (rTileEntity != null) return rTileEntity;
            rTileEntity = LAST_BROKEN_TILEENTITY.get();
            if (rTileEntity != null && rTileEntity.xCoord == aX && rTileEntity.yCoord == aY && rTileEntity.zCoord == aZ)
                return rTileEntity;
        }
        return null;
    }

    /** Sets the TileEntity at the passed position, with the option of turning adjacent TileEntity updates off. */
    public static TileEntity setTileEntity(World aWorld, int aX, int aY, int aZ, TileEntity aTileEntity,
        boolean aCauseTileEntityUpdates) {
        if (aCauseTileEntityUpdates) aWorld.setTileEntity(aX, aY, aZ, aTileEntity);
        else {
            Chunk tChunk = aWorld.getChunkFromChunkCoords(aX >> 4, aZ >> 4);
            if (tChunk != null) {
                aWorld.addTileEntity(aTileEntity);
                tChunk.func_150812_a /* setBlockTileEntityInChunk */(aX & 15, aY, aZ & 15, aTileEntity);
                tChunk.setChunkModified();
            }
        }
        return aTileEntity;
    }

    public static boolean setTileEntity(World aWorld, int aX, int aY, int aZ, Block aBlock, short aMeta, long aFlags,
        boolean aRemoveGrassBelow) {
        if (aRemoveGrassBelow) {
            final Block tBlock = aWorld.getBlock(aX, aY - 1, aZ);
            if (tBlock == Blocks.grass || tBlock == Blocks.mycelium)
                aWorld.setBlock(aX, aY - 1, aZ, Blocks.dirt, 0, (byte) aFlags);
        }
        return aWorld.setBlock(aX, aY, aZ, aBlock, aMeta, (byte) aFlags);
    }

    public static TileEntity getTileEntity(World aWorld, ChunkCoordinates aCoords, boolean aLoadUnloadedChunks) {
        return getTileEntity(aWorld, aCoords.posX, aCoords.posY, aCoords.posZ, aLoadUnloadedChunks);
    }

    /** Marks a Chunk dirty so it is saved */
    public static boolean markChunkDirty(World aWorld, int aX, int aZ) {
        if (aWorld == null || aWorld.isRemote) return false;
        Chunk aChunk = aWorld.getChunkFromBlockCoords(aX, aZ);
        if (aChunk == null) {
            aWorld.getBlockMetadata(aX, 0, aZ);
            aChunk = aWorld.getChunkFromBlockCoords(aX, aZ);
            if (aChunk == null) {
                GT_Log.err.println(
                    "Some important Chunk does not exist for some reason at Coordinates X: " + aX + " and Z: " + aZ);
                return false;
            }
        }
        aChunk.setChunkModified();
        return true;
    }

    /** Marks a Chunk dirty so it is saved */
    public static boolean markChunkDirty(Object aTileEntity) {
        return aTileEntity instanceof TileEntity && markChunkDirty(
            ((TileEntity) aTileEntity).getWorldObj(),
            ((TileEntity) aTileEntity).xCoord,
            ((TileEntity) aTileEntity).zCoord);
    }

    public static int mixRGBInt(int aRGB1, int aRGB2) {
        return getRGBInt(
            new short[] { (short) ((getR(aRGB1) + getR(aRGB2)) >> 1), (short) ((getG(aRGB1) + getG(aRGB2)) >> 1),
                (short) ((getB(aRGB1) + getB(aRGB2)) >> 1) });
    }

    public static int getRGBInt(short[] aColors) {
        return aColors == null ? 16777215 : (aColors[0] << 16) | (aColors[1] << 8) | aColors[2];
    }

    public static int getRGBaInt(short[] aColors) {
        return aColors == null ? 16777215 : (aColors[0]) << 16 | (aColors[1] << 8) | aColors[2] | (aColors[3] << 24);
    }

    public static String toHexString(short[] aColors) {
        return aColors == null ? "FFFFFF" : Integer.toHexString((aColors[0] << 16) | (aColors[1] << 8) | aColors[2]);
    }

    public static int getRGBInt(short aR, short aG, short aB) {
        return (aR << 16) | (aG << 8) | aB;
    }

    public static int getRGBaInt(short aR, short aG, short aB, short aA) {
        return (aR << 16) | (aG << 8) | aB | (aA << 24);
    }

    public static short[] getRGBaArray(int aColors) {
        return new short[] { (short) ((aColors >>> 16) & 255), (short) ((aColors >>> 8) & 255), (short) (aColors & 255),
            (short) ((aColors >>> 24) & 255) };
    }

    public static short getR(int aColors) {
        return (short) ((aColors >>> 16) & 255);
    }

    public static short getG(int aColors) {
        return (short) ((aColors >>> 8) & 255);
    }

    public static short getB(int aColors) {
        return (short) (aColors & 255);
    }

    public static short getA(int aColors) {
        return (short) ((aColors >>> 24) & 255);
    }

    public static boolean saveMultiblockInputConfiguration(GT_MetaTileEntity_MultiBlockBase controller,
        EntityPlayer player) {
        NBTTagCompound newTag = new NBTTagCompound();
        ItemStack dataOrb = player.getHeldItem();
        if (GT_Utility.isStackInvalid(dataOrb) || !ItemList.Tool_DataOrb.isStackEqual(dataOrb, false, true)) {
            return false;
        }
        if (!controller.saveOtherHatchConfiguration(player)) {
            return false;
        }
        newTag.setString("type", "MultiblockConfiguration");
        int count = 0;
        NBTTagList list = saveConfigurationToDataStick(player, controller.mInputBusses);
        if (list == null) return false;
        newTag.setTag("mInputBusses", list);
        count += list.tagCount();
        list = saveConfigurationToDataStick(player, controller.mInputHatches);
        if (list == null) return false;
        newTag.setTag("mInputHatches", list);
        count += list.tagCount();
        list = saveConfigurationToDataStick(player, controller.mOutputBusses);
        if (list == null) return false;
        newTag.setTag("mOutputBusses", list);
        count += list.tagCount();
        // Output hatch config currently cannot be copied, so we omit this part for now
        // TODO this doesn't work for now
        // newTag.setTag("mDualInputHatches", saveToDataStick(player, controller.mDualInputHatches));
        dataOrb.setTagCompound(newTag);
        Behaviour_DataOrb.setDataTitle(dataOrb, "Multiblock Hatch Configuration");
        Behaviour_DataOrb.setDataName(dataOrb, String.format("%s configuration saved", count));
        return true;
    }

    public static boolean hasMultiblockInputConfiguration(ItemStack dataOrb) {
        return !GT_Utility.isStackInvalid(dataOrb) && ItemList.Tool_DataOrb.isStackEqual(dataOrb, false, true)
            && dataOrb.getTagCompound() != null
            && "MultiblockConfiguration".equals(
                dataOrb.getTagCompound()
                    .getString("type"));
    }

    public static boolean loadMultiblockInputConfiguration(GT_MetaTileEntity_MultiBlockBase controller,
        EntityPlayer player) {
        ItemStack dataOrb = player.getHeldItem();
        if (!hasMultiblockInputConfiguration(dataOrb)) {
            return false;
        }
        if (!controller.loadOtherHatchConfiguration(player)) {
            return false;
        }
        NBTTagCompound tag = dataOrb.getTagCompound();
        if (!checkCanLoadConfigurationFromDataStick(
            tag.getTagList("mInputBusses", Constants.NBT.TAG_COMPOUND),
            player,
            controller.mInputBusses)
            || !checkCanLoadConfigurationFromDataStick(
                tag.getTagList("mInputHatches", Constants.NBT.TAG_COMPOUND),
                player,
                controller.mInputHatches)
            || !checkCanLoadConfigurationFromDataStick(
                tag.getTagList("mOutputBusses", Constants.NBT.TAG_COMPOUND),
                player,
                controller.mOutputBusses))
            return false;

        if (!loadConfigurationFromDataStick(
            tag.getTagList("mInputBusses", Constants.NBT.TAG_COMPOUND),
            player,
            controller.mInputBusses)) return false;
        if (!loadConfigurationFromDataStick(
            tag.getTagList("mInputHatches", Constants.NBT.TAG_COMPOUND),
            player,
            controller.mInputHatches)) return false;
        if (!loadConfigurationFromDataStick(
            tag.getTagList("mOutputBusses", Constants.NBT.TAG_COMPOUND),
            player,
            controller.mOutputBusses)) return false;
        return true;
    }

    private static NBTTagList saveConfigurationToDataStick(EntityPlayer player,
        List<? extends GT_MetaTileEntity_Hatch> hatches) {
        NBTTagList list = new NBTTagList();
        for (GT_MetaTileEntity_Hatch tHatch : filterValidMTEs(hatches)) {
            if (!(tHatch instanceof IDataCopyable copyable)) {
                list.appendTag(new NBTTagCompound());
                continue;
            }
            NBTTagCompound tag = copyable.getCopiedData(player);
            if (tag == null) return null;
            list.appendTag(tag);
        }
        return list;
    }

    private static boolean loadConfigurationFromDataStick(NBTTagList list, EntityPlayer player,
        List<? extends GT_MetaTileEntity_Hatch> hatches) {
        if (list == null || list.tagCount() == 0) return false;
        List<? extends GT_MetaTileEntity_Hatch> validMTEs = filterValidMTEs(hatches);
        int end = Math.min(validMTEs.size(), list.tagCount());
        for (int i = 0; i < end; i++) {
            GT_MetaTileEntity_Hatch tHatch = validMTEs.get(i);
            NBTTagCompound tag = list.getCompoundTagAt(i);
            if (!(tHatch instanceof IDataCopyable copyable)) {
                if (tag.hasNoTags()) continue;
                return false;
            }
            if (tag.hasNoTags()) return false;
            if (!copyable.pasteCopiedData(player, tag)) return false;
        }
        return true;
    }

    private static boolean checkCanLoadConfigurationFromDataStick(NBTTagList list, EntityPlayer player,
        List<? extends GT_MetaTileEntity_Hatch> hatches) {
        if (list == null || list.tagCount() == 0) return false;
        List<? extends GT_MetaTileEntity_Hatch> validMTEs = filterValidMTEs(hatches);
        int end = Math.min(validMTEs.size(), list.tagCount());
        for (int i = 0; i < end; i++) {
            GT_MetaTileEntity_Hatch tHatch = validMTEs.get(i);
            NBTTagCompound tag = list.getCompoundTagAt(i);
            if (tag.hasNoTags()) continue;
            if (!(tHatch instanceof IDataCopyable copyable) || !copyable.getCopiedDataIdentifier(player)
                .equals(tag.getString("type"))) return false;
        }
        return true;
    }
}
