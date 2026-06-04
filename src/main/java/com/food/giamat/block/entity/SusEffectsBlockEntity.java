package com.food.giamat.block.entity;

import com.food.giamat.init.ModBlockEntities;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.type.SuspiciousStewEffectsComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;

public class SusEffectsBlockEntity extends BlockEntity {
    private SuspiciousStewEffectsComponent stewEffects;

    public SusEffectsBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SUS_EFFECTS, pos, state);
    }

    public void setEffects(SuspiciousStewEffectsComponent effects) {
        this.stewEffects = effects;
        markDirty();
    }

    public void applyEffects(PlayerEntity player) {
        if (stewEffects != null) {
            stewEffects.forEachEffect(e ->
                    player.addStatusEffect(new StatusEffectInstance(e.effect(), e.duration(), 0)));
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        if (stewEffects != null) {
            SuspiciousStewEffectsComponent.CODEC
                    .encodeStart(registries.getOps(NbtOps.INSTANCE), stewEffects)
                    .result()
                    .ifPresent(el -> nbt.put("StewEffects", el));
        }
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        if (nbt.contains("StewEffects")) {
            SuspiciousStewEffectsComponent.CODEC
                    .decode(registries.getOps(NbtOps.INSTANCE), nbt.get("StewEffects"))
                    .result()
                    .map(Pair::getFirst)
                    .ifPresent(e -> stewEffects = e);
        }
    }
}
