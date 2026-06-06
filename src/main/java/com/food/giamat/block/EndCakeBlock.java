package com.food.giamat.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

/**
 * A purple "End cake": eating a slice teleports the player. If they are looking
 * at a nearby block, they teleport to where they are looking; otherwise they are
 * sent to a random spot in a small radius (chorus-fruit style).
 */
public class EndCakeBlock extends CakeBlock {
    public static final MapCodec<EndCakeBlock> CODEC = simpleCodec(EndCakeBlock::new);

    private static final double LOOK_DISTANCE = 32.0;
    private static final int RANDOM_RADIUS = 8;

    public EndCakeBlock(Properties settings) {
        super(settings);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public MapCodec<CakeBlock> codec() {
        // CakeBlock pins the return type to MapCodec<CakeBlock>; our codec still
        // produces EndCakeBlock instances at runtime, so the raw cast is safe.
        return (MapCodec) CODEC;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        InteractionResult result = super.useWithoutItem(state, world, pos, player, hit);
        // A slice was actually eaten only when the use was accepted.
        if (result.consumesAction() && world instanceof ServerLevel serverWorld && player instanceof ServerPlayer serverPlayer) {
            teleport(serverWorld, serverPlayer);
        }
        return result;
    }

    private void teleport(ServerLevel world, ServerPlayer player) {
        // Prefer wherever the player is looking.
        HitResult look = player.pick(LOOK_DISTANCE, 1.0f, false);
        if (look.getType() == HitResult.Type.BLOCK) {
            Vec3 target = look.getLocation();
            if (player.randomTeleport(target.x, target.y, target.z, true)) {
                playTeleportSound(world, player);
                return;
            }
        }
        // Otherwise pick a random nearby landing spot (chorus-fruit style).
        double baseX = player.getX();
        double baseY = player.getY();
        double baseZ = player.getZ();
        for (int attempt = 0; attempt < 16; attempt++) {
            double x = baseX + (player.getRandom().nextDouble() - 0.5) * 2.0 * RANDOM_RADIUS;
            double y = Mth.clamp(
                    baseY + (player.getRandom().nextInt(2 * RANDOM_RADIUS + 1) - RANDOM_RADIUS),
                    world.getMinY(), world.getMinY() + world.getHeight() - 1);
            double z = baseZ + (player.getRandom().nextDouble() - 0.5) * 2.0 * RANDOM_RADIUS;
            if (player.randomTeleport(x, y, z, true)) {
                playTeleportSound(world, player);
                return;
            }
        }
    }

    private void playTeleportSound(ServerLevel world, ServerPlayer player) {
        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS, 1.0f, 1.0f);
    }
}
