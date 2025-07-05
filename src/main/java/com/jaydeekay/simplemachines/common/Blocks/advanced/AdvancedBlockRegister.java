package com.jaydeekay.simplemachines.common.Blocks.advanced;

import java.util.function.Supplier;

import com.jaydeekay.simplemachines.SimpleMachines;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.jaydeekay.simplemachines.common.Blocks.BasicBlocks.TEST_ENERGY_CUBE_BLOCK;


public class AdvancedBlockRegister {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
        DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, SimpleMachines.MODID);


    public static final Supplier<BlockEntityType<EnergyCubeEntity>> ENERGY_CUBE_ENTITY = BLOCK_ENTITY_TYPES.register(
        "energy_block_entity",
            () -> new BlockEntityType<>(
                EnergyCubeEntity::new,
                false,
                TEST_ENERGY_CUBE_BLOCK.get()
    ));


}
