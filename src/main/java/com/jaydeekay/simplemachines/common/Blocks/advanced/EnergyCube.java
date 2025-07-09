package com.jaydeekay.simplemachines.common.Blocks.advanced;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class EnergyCube extends Block implements EntityBlock {

    public EnergyCube(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(final @NotNull BlockPos pos, final @NotNull BlockState state) {
        return new EnergyCubeEntity(pos, state);
    }

    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos,
                                          @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (!level.isClientSide()) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof MenuProvider provider) {
                player.openMenu(provider);
            }
        }
        return InteractionResult.SUCCESS;
    }

}
