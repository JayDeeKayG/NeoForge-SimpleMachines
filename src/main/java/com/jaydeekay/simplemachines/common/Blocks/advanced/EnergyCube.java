package com.jaydeekay.simplemachines.common.Blocks.advanced;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import static com.jaydeekay.simplemachines.common.Blocks.advanced.AdvancedBlockRegister.ENERGY_CUBE_ENTITY;

public class EnergyCube extends Block implements EntityBlock {

    public EnergyCube(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(final @NotNull BlockPos pos, final @NotNull BlockState state) {
        return new EnergyCubeEntity(pos, state);
    }

    @Override
    protected @NotNull InteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, Level level, @NotNull BlockPos pos,
                                                   @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        if(level.getBlockEntity(pos) instanceof EnergyCubeEntity EnergyCubeEntity) {
            if(!level.isClientSide()) {
                player.openMenu(new SimpleMenuProvider(EnergyCubeEntity, Component.literal("EnergyCube")), pos);
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        // You can return different tickers here, depending on whatever factors you want. A common use case would be
        // to return different tickers on the client or server, only tick one side to begin with,
        // or only return a ticker for some blockstates (e.g. when using a "my machine is working" blockstate property).
        return type == ENERGY_CUBE_ENTITY.get() ? EnergyCubeEntity::tick : null;
    }

}
