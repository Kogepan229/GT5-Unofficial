package gtPlusPlus.xmod.gregtech.registration.gregtech;

import gtPlusPlus.xmod.gregtech.api.enums.GregtechItemList;
import gtPlusPlus.xmod.gregtech.common.tileentities.machines.multi.processing.steam.MTESteamWaterPump;

import static gregtech.api.enums.MetaTileEntityIDs.WaterPump;

public class GregtechWaterPump {

    public static void run() {
        // Water Pump Multiblock
        GregtechItemList.WaterPump
            .set(new MTESteamWaterPump(WaterPump.ID, "waterpump.controller.tier.single", "Water Pump").getStackForm(1L));
    }
}
