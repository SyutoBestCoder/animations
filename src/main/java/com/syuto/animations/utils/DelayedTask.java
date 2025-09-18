package com.syuto.animations.utils;
// package xyz.awayxd.awaytils.utils;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public class DelayedTask {

    // from my mod,  AwayTils - away
    private final Runnable runnable;
    private int counter;

    public DelayedTask(Runnable task) {
        this.counter = 0;
        this.runnable = task;
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase == Phase.START) {
            if (this.counter <= 0) {
                MinecraftForge.EVENT_BUS.unregister(this);
                this.runnable.run();
            }
            this.counter--;
        }
    }

}
