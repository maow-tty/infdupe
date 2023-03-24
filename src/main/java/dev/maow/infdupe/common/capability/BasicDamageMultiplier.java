package dev.maow.infdupe.common.capability;

import dev.maow.infdupe.api.capability.DamageMultiplier;
import net.minecraft.nbt.FloatTag;

public class BasicDamageMultiplier implements DamageMultiplier {
    private float value;

    public BasicDamageMultiplier(float defaultValue) {
        this.value = defaultValue;
    }

    public BasicDamageMultiplier() {
        this(1.0F);
    }

    @Override
    public float getValue() {
        return value;
    }

    @Override
    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public void addValue(float value) {
        this.value += value;
    }

    @Override
    public FloatTag serializeNBT() {
        return FloatTag.valueOf(value);
    }

    @Override
    public void deserializeNBT(FloatTag nbt) {
        this.value = nbt.getAsFloat();
    }
}