package com.food.giamat.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.block.CakeBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * A purple "End cake": eating a slice teleports the player. If they are looking
 * at a nearby block, they teleport to where they are looking; otherwise they are
 * sent to a random spot in a small radius (chorus-fruit style).
 */
public class EndCakeBlock extends CakeBlock {
    public static final MapCodec<EndCakeBlock> CODEC = createCodec(EndCakeBlock::new);

    private static final double LOOK_DISTANCE = 32.0;
    private static final int RANDOM_RADIUS = 8;

    public EndCakeBlock(Settings settings) {
        super(settings);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public MapCodec<CakeBlock> getCodec() {
        // CakeBlock pins the return type to MapCodec<CakeBlock>; our codec still
        // produces EndCakeBlock instances at runtime, so the raw cast is safe.
        return (MapCodec) CODEC;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        ActionResult result = super.onUse(state, world, pos, player, hit);
        // A slice was actually eaten only when the use was accepted.
        if (result.isAccepted() && world instanceof ServerWorld serverWorld && player instanceof ServerPlayerEntity serverPlayer) {
            teleport(serverWorld, serverPlayer);
        }
        return result;
    }

    private void teleport(ServerWorld world, ServerPlayerEntity player) {
        // Prefer wherever the player is looking.
        HitResult look = player.raycast(LOOK_DISTANCE, 1.0f, false);
        if (look.getType() == HitResult.Type.BLOCK) {
            Vec3d target = look.getPos();
            if (player.teleport(target.x, target.y, target.z, true)) {
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
            double y = MathHelper.clamp(
                    baseY + (player.getRandom().nextInt(2 * RANDOM_RADIUS + 1) - RANDOM_RADIUS),
                    world.getBottomY(), world.getBottomY() + world.getHeight() - 1);
            double z = baseZ + (player.getRandom().nextDouble() - 0.5) * 2.0 * RANDOM_RADIUS;
            if (player.teleport(x, y, z, true)) {
                playTeleportSound(world, player);
                return;
            }
        }
    }

    private void playTeleportSound(ServerWorld world, ServerPlayerEntity player) {
        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1.0f, 1.0f);
    }
}
