package com.imeetake.tlib.neoforge;

import com.imeetake.tlib.TLib;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(TLib.MOD_ID)
public class TLibNeoForge {
    public TLibNeoForge(IEventBus modBus) {
        TLib.init();
    }
}