package dev.maow.infdupe.api.capability;

import net.minecraft.nbt.FloatTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.INBTSerializable;

@AutoRegisterCapability
public interface DamageMultiplier extends INBTSerializable<FloatTag> {
    Capability<DamageMultiplier> CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});

    float getValue();

    void setValue(float value);

    void addValue(float value);

    default void copy(DamageMultiplier other) {
        setValue(other.getValue());
    }
}