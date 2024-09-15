package gtPlusPlus.xmod.gregtech.registration.gregtech;

import gtPlusPlus.api.objects.Logger;
import gtPlusPlus.xmod.gregtech.api.enums.GregtechItemList;
import gtPlusPlus.xmod.gregtech.common.tileentities.redstone.MTERedstoneLamp;

import static gregtech.api.enums.MetaTileEntityIDs.RedstoneLamp;

public class GregtechRedstoneLamp {

    public static void run() {
        Logger.INFO("Gregtech5u Content | Registering Redstone Lamp.");
        GregtechItemList.RedstoneLamp.set(new MTERedstoneLamp(RedstoneLamp.ID).getStackForm(1L));
    }
}
