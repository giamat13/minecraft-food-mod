package com.food.giamat.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

/**
 * A wild bush that carries a single edible fruit (chili peppers, tomatoes, ...).
 *
 * <p>Breaking it normally drops the fruit; breaking it with shears or Silk Touch
 * drops a placeable bush (handled by the loot table). Right-clicking it with
 * shears snips just the fruit off, leaving an empty bush behind that grows its
 * fruit back after 20 real-world minutes.
 */
public abstract class FruitBushBlock extends VegetationBlock {
    /** {@code false} = the bush carries ripe fruit, {@code true} = it was sheared and is empty. */
    public static final BooleanProperty HARVESTED = BooleanProperty.create("harvested");

    /** 20 minutes at 20 ticks per second. */
    private static final int REGROW_TICKS = 20 * 60 * 20;
    private static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);

    private final boolean growsOnSand;

    protected FruitBushBlock(Properties settings, boolean growsOnSand) {
        super(settings);
        this.growsOnSand = growsOnSand;
        registerDefaultState(defaultBlockState().setValue(HARVESTED, false));
    }

    /** The fruit dropped when the bush is harvested. */
    protected abstract Item getFruit();

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HARVESTED);
    }

    @Override
    protected boolean mayPlaceOn(BlockState floor, BlockGetter world, BlockPos pos) {
        return (growsOnSand && floor.is(BlockTags.SAND)) || super.mayPlaceOn(floor, world, pos);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    /**
     * Snip the fruit off without breaking the bush: drops the fruit, marks the
     * bush empty and schedules it to grow the fruit back in 20 minutes.
     *
     * @return {@code true} if there was fruit to harvest.
     */
    public boolean shearFruit(BlockState state, Level world, BlockPos pos) {
        if (state.getValue(HARVESTED)) {
            return false;
        }
        if (!world.isClientSide()) {
            Block.popResource(world, pos, new ItemStack(getFruit()));
            world.setBlock(pos, state.setValue(HARVESTED, true), Block.UPDATE_ALL);
            world.scheduleTick(pos, this, REGROW_TICKS);
            world.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0f, 1.0f);
        }
        return true;
    }

    @Override
    protected void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (state.getValue(HARVESTED)) {
            world.setBlock(pos, state.setValue(HARVESTED, false), Block.UPDATE_CLIENTS);
        }
    }
}
