package com.food.giamat.block.entity;

import com.food.giamat.init.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.type.SuspiciousStewEffectsComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
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
            for (SuspiciousStewEffectsComponent.StewEffect e : stewEffects.effects()) {
                player.addStatusEffect(new StatusEffectInstance(e.effect(), e.duration(), 0));
            }
        }
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        if (stewEffects != null) {
            view.put("StewEffects", SuspiciousStewEffectsComponent.CODEC, stewEffects);
        }
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        view.read("StewEffects", SuspiciousStewEffectsComponent.CODEC)
                .ifPresent(e -> stewEffects = e);
    }
}
