package com.jaydeekay.simplemachines.common.Blocks.advanced;

import com.jaydeekay.simplemachines.common.energy.CustomEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.jarjar.nio.util.Lazy;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.jaydeekay.simplemachines.common.Blocks.advanced.AdvancedBlockRegister.ENERGY_CUBE_ENTITY;

public class EnergyCubeEntity extends BlockEntity {

    final CompoundTag tag = new CompoundTag();

    private BlockCapabilityCache<Capabilities.EnergyStorage, @Nullable Direction> CapabilityCache;

    private final CustomEnergyStorage energy = new CustomEnergyStorage(10000, 100,100,0);
    private final Lazy<CustomEnergyStorage> energyOptional = Lazy.of(() -> energy);

    public EnergyCubeEntity(BlockPos pos, BlockState state) {
        super(ENERGY_CUBE_ENTITY.get(), pos, state);
    }

    public Lazy<CustomEnergyStorage> getEnergyOptional() {
        return this.energyOptional;
    }

    public CustomEnergyStorage getEnergy() {
        return this.energy;
    }


    @Override
    public void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("Energy", this.energy.serializeNBT());
    }

    @Override
    public void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);
        tag.getCompound("Energy").ifPresent(this.energy::deserializeNBT);
    }

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
            Capabilities.EnergyStorage.BLOCK,
            ENERGY_CUBE_ENTITY.get(),
            (EnergyCubeEntity be, @Nullable Direction side) -> be.getEnergy()
        );
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {return ClientboundBlockEntityDataPacket.create(this);}

}