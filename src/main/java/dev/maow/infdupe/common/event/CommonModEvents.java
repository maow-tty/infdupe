package dev.maow.infdupe.common.event;

import dev.maow.infdupe.InfiniteDuplication;
import dev.maow.infdupe.api.capability.DamageMultiplier;
import dev.maow.infdupe.common.capability.BasicDamageMultiplier;
import net.minecraft.core.Direction;
import net.minecraft.nbt.FloatTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static dev.maow.infdupe.InfiniteDuplication.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Bus.FORGE)
public final class CommonModEvents {
    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            final var multiplier = new BasicDamageMultiplier();
            final var optional = LazyOptional.of(() -> multiplier);

            event.addCapability(
                new ResourceLocation(MODID, "damage_multiplier"),
                new ICapabilitySerializable<FloatTag>() {
                    @Override
                    public @NotNull <T> LazyOptional<T> getCapability(
                        @NotNull Capability<T> capability,
                        @Nullable Direction side
                    ) {
                        if (capability == DamageMultiplier.CAPABILITY) {
                            return optional.cast();
                        }
                        return LazyOptional.empty();
                    }

                    @Override
                    public FloatTag serializeNBT() {
                        return multiplier.serializeNBT();
                    }

                    @Override
                    public void deserializeNBT(FloatTag nbt) {
                        multiplier.deserializeNBT(nbt);
                    }
                }
            );
        }
    }

    @SubscribeEvent
    public static void playerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            final var player = event.getOriginal();
            player.reviveCaps();
            player
                .getCapability(DamageMultiplier.CAPABILITY)
                .ifPresent(multiplier -> event
                    .getEntity()
                    .getCapability(DamageMultiplier.CAPABILITY)
                    .ifPresent(newMultipler -> newMultipler.copy(multiplier))
                );
            player.invalidateCaps();
        }
    }

    @SubscribeEvent
    public static void playerDamage(LivingDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            player
                .getCapability(DamageMultiplier.CAPABILITY)
                .ifPresent(multiplier ->
                    event.setAmount(event.getAmount() * multiplier.getValue())
                );
        }
    }

    @SubscribeEvent
    public static void itemUse(PlayerInteractEvent.RightClickItem event) {
        final var player = event.getEntity();
        final var item = event.getItemStack();
        if (player != null && item.is(InfiniteDuplication.Items.BOOK_OF_RESISTANCE.get())) {
            final var inventory = player.getInventory();
            player
                .getCapability(DamageMultiplier.CAPABILITY)
                .ifPresent(multiplier -> {
                    inventory.removeItem(item);
                    multiplier.setValue(1.0F);
                });
        }
    }
}