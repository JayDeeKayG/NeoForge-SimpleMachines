package com.jaydeekay.simplemachines.common.Blocks.advanced;

import com.jaydeekay.simplemachines.common.energy.CustomEnergyStorage;
import com.jaydeekay.simplemachines.common.gui.custom.EnergyCubeMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.jarjar.nio.util.Lazy;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.jaydeekay.simplemachines.SimpleMachines.MODID;
import static com.jaydeekay.simplemachines.common.Blocks.advanced.AdvancedBlockRegister.ENERGY_CUBE_ENTITY;

@EventBusSubscriber(modid = MODID)
public class EnergyCubeEntity extends BlockEntity implements MenuProvider {


    public final ItemStackHandler inventory = new ItemStackHandler(2) {
        @Override
        protected int getStackLimit(int slot, @NotNull ItemStack stack) {
            return 1;
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return stack.getCapability(Capabilities.EnergyStorage.ITEM, null) != null;
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            assert level != null;
            if(!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    // need to use this
    // private BlockCapabilityCache<Capabilities.EnergyStorage, @Nullable Direction> CapabilityCache;

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

    public void clearContents() {
        inventory.setStackInSlot(0, ItemStack.EMPTY);
        inventory.setStackInSlot(1, ItemStack.EMPTY);
    }

    public void drops() {
        SimpleContainer inv = new SimpleContainer(inventory.getSlots());
        for(int i = 0; i < inventory.getSlots(); i++) {
            inv.setItem(i, inventory.getStackInSlot(i));
        }

        assert this.level != null;
        Containers.dropContents(this.level, this.worldPosition, inv);
    }

    @Override
    public void preRemoveSideEffects(@NotNull BlockPos pos, @NotNull BlockState state) {
        drops();
        super.preRemoveSideEffects(pos, state);
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

    @Override
    public void onLoad() {
    }

    @Override
    public void setChanged() {
        super.setChanged();
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.literal("EnergyCube");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, @NotNull Inventory inventory, @NotNull Player player) {
        return new EnergyCubeMenu(i, inventory, this);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {return ClientboundBlockEntityDataPacket.create(this);}

}