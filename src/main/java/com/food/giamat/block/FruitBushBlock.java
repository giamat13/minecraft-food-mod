package com.food.giamat.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

/**
 * A wild bush that carries a single edible fruit (chili peppers, tomatoes, ...).
 *
 * <p>Breaking it normally drops the fruit; breaking it with shears or Silk Touch
 * drops a placeable bush (handled by the loot table). Right-clicking it with
 * shears snips just the fruit off, leaving an empty bush behind that grows its
 * fruit back after 20 real-world minutes.
 */
public abstract class FruitBushBlock extends PlantBlock {
    /** {@code false} = the bush carries ripe fruit, {@code true} = it was sheared and is empty. */
    public static final BooleanProperty HARVESTED = BooleanProperty.of("harvested");

    /** 20 minutes at 20 ticks per second. */
    private static final int REGROW_TICKS = 20 * 60 * 20;
    private static final VoxelShape SHAPE = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);

    private final boolean growsOnSand;

    protected FruitBushBlock(Settings settings, boolean growsOnSand) {
        super(settings);
        this.growsOnSand = growsOnSand;
        setDefaultState(getDefaultState().with(HARVESTED, false));
    }

    /** The fruit dropped when the bush is harvested. */
    protected abstract Item getFruit();

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HARVESTED);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return (growsOnSand && floor.isIn(BlockTags.SAND)) || super.canPlantOnTop(floor, world, pos);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    /**
     * Snip the fruit off without breaking the bush: drops the fruit, marks the
     * bush empty and schedules it to grow the fruit back in 20 minutes.
     *
     * @return {@code true} if there was fruit to harvest.
     */
    public boolean shearFruit(BlockState state, World world, BlockPos pos) {
        if (state.get(HARVESTED)) {
            return false;
        }
        if (!world.isClient()) {
            Block.dropStack(world, pos, new ItemStack(getFruit()));
            world.setBlockState(pos, state.with(HARVESTED, true), Block.NOTIFY_ALL);
            world.scheduleBlockTick(pos, this, REGROW_TICKS);
            world.playSound(null, pos, SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.BLOCKS, 1.0f, 1.0f);
        }
        return true;
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(HARVESTED)) {
            world.setBlockState(pos, state.with(HARVESTED, false), Block.NOTIFY_LISTENERS);
        }
    }
}
