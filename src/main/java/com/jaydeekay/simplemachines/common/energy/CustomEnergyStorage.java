package com.jaydeekay.simplemachines.common.energy;

import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.energy.EnergyStorage;

public  class CustomEnergyStorage extends EnergyStorage {
    public CustomEnergyStorage(final int capacity) {
        super(capacity);
    }

    public CustomEnergyStorage(final int capacity, final int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public CustomEnergyStorage(final int capacity, final int maxReceive, final int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public CustomEnergyStorage(final int capacity, final int maxReceive, final int maxExtract, final int energy) {
        super(capacity, maxReceive, maxExtract, energy);
    }

    public void SetEnergy(int energy) {
        if (energy < 0)
            energy = 0;

        if (energy > this.capacity)
            energy = this.capacity;

        this.energy = energy;
    }

    public void AddEnergy(int energy) {
        SetEnergy(this.energy +  energy);
    }

    public void RemoveEnergy(int energy) {
        SetEnergy(this.energy - energy);
    }

    public int GetEnergy() {
        return this.energy;
    }

    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("Energy", this.GetEnergy());
        return tag;
    }

    public void deserializeNBT(CompoundTag nbt) {
        this.SetEnergy(nbt.getInt("Energy").orElse(0)); // fallback if missing
    }

}
