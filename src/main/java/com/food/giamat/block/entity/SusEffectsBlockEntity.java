package com.food.giamat.block.entity;

import com.food.giamat.init.ModBlockEntities;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.core.BlockPos;

public class SusEffectsBlockEntity extends BlockEntity {
    private SuspiciousStewEffects stewEffects;

    public SusEffectsBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SUS_EFFECTS, pos, state);
    }

    public void setEffects(SuspiciousStewEffects effects) {
        this.stewEffects = effects;
        setChanged();
    }

    public void applyEffects(Player player) {
        if (stewEffects != null) {
            for (SuspiciousStewEffects.Entry e : stewEffects.effects()) {
                player.addEffect(new MobEffectInstance(e.effect(), e.duration(), 0));
            }
        }
    }

    @Override
    protected void saveAdditional(ValueOutput view) {
        super.saveAdditional(view);
        if (stewEffects != null) {
            view.store("StewEffects", SuspiciousStewEffects.CODEC, stewEffects);
        }
    }

    @Override
    protected void loadAdditional(ValueInput view) {
        super.loadAdditional(view);
        view.read("StewEffects", SuspiciousStewEffects.CODEC)
                .ifPresent(e -> stewEffects = e);
    }
}
