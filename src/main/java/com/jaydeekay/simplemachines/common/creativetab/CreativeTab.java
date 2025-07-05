package com.jaydeekay.simplemachines.common.creativetab;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.jaydeekay.simplemachines.SimpleMachines.MODID;
import static com.jaydeekay.simplemachines.common.Blocks.BasicBlocks.TEST_ENERGY_CUBE_BLOCK_ITEM;
import static com.jaydeekay.simplemachines.common.items.Items.*;
import static com.jaydeekay.simplemachines.common.Blocks.BasicBlocks.*;

public class CreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab>
        EXAMPLE_TAB = CREATIVE_TABS.register("simple_machines", () -> CreativeModeTab.builder()
        .title(Component.translatable("itemGroup.simplemachines"))
        .withTabsBefore(CreativeModeTabs.COMBAT)
        .icon(() -> TEST_ITEM.get().getDefaultInstance())
        .displayItems((parameters, output) -> {
            output.accept(TEST_ITEM.get());
            output.accept(TEST_BLOCK_ITEM.get());
            output.accept(TEST_ENERGY_CUBE_BLOCK_ITEM.get());
        }).build());

}
