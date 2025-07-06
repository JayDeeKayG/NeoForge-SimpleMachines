package com.jaydeekay.simplemachines.common.Blocks;

import com.jaydeekay.simplemachines.common.Blocks.advanced.EnergyCube;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.jaydeekay.simplemachines.SimpleMachines.MODID;
import static com.jaydeekay.simplemachines.common.items.Items.ITEMS;

public final class BasicBlocks {

    public static final DeferredRegister.Blocks ADVANCED_BLOCKS = DeferredRegister.createBlocks(MODID);

    public static final DeferredBlock<Block> TEST_ENERGY_CUBE_BLOCK =
        ADVANCED_BLOCKS.register("test_energy_cube", () -> new EnergyCube(BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.parse("simplemachines:energy_cube")))
        ));

    public static final DeferredItem<BlockItem>
        TEST_ENERGY_CUBE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("test_energy_cube", TEST_ENERGY_CUBE_BLOCK);


    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);

    public static final DeferredBlock<Block> TEST_BLOCK = BLOCKS.registerSimpleBlock("test_block"
    , BlockBehaviour.Properties.of()
            .lightLevel(state -> 10));

    public static final DeferredItem<BlockItem>
        TEST_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("test_block_item", TEST_BLOCK);
}
