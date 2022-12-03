/*******************************************************************************
 * Copyright 2022, the Glitchfiend Team.
 * All rights reserved.
 ******************************************************************************/
package biomesoplenty.common.block;

import biomesoplenty.api.block.BOPBlocks;
import biomesoplenty.common.worldgen.feature.BOPCaveFeatures;
import biomesoplenty.common.worldgen.feature.BOPVegetationFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class MushroomBlockBOP extends MushroomBlock implements BonemealableBlock
{
    public MushroomBlockBOP(Block.Properties properties)
    {
        super(properties, null);
    }
    
    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random)
    {
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos)
    {
        Block ground = worldIn.getBlockState(pos.below()).getBlock();
        BlockState BlockState = worldIn.getBlockState(pos.below());

        return BlockState.canSustainPlant(worldIn, pos.below(), Direction.UP, this);
    }

    @Override
    public boolean growMushroom(ServerLevel world, BlockPos p_226940_2_, BlockState p_226940_3_, RandomSource p_226940_4_)
    {
        world.removeBlock(p_226940_2_, false);
        ConfiguredFeature<NoneFeatureConfiguration, ?> configuredfeature;
        if (this == BOPBlocks.GLOWSHROOM.get())
        {
            configuredfeature = BOPCaveFeatures.HUGE_GLOWSHROOM_CAVE.get();
        }
        else
        {
            if (this != BOPBlocks.TOADSTOOL.get())
            {
                world.setBlock(p_226940_2_, p_226940_3_, 3);
                return false;
            }

            configuredfeature = BOPVegetationFeatures.HUGE_TOADSTOOL.get();
        }

        if (configuredfeature.place(world, world.getChunkSource().getGenerator(), p_226940_4_, p_226940_2_))
        {
            return true;
        }
        else
        {
            world.setBlock(p_226940_2_, p_226940_3_, 3);
            return false;
        }
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level worldIn, RandomSource rand, BlockPos pos, BlockState state) {
        return (double)rand.nextFloat() < 0.4D;
    }

    @Override
    public void performBonemeal(ServerLevel p_225535_1_, RandomSource p_225535_2_, BlockPos p_225535_3_, BlockState p_225535_4_) {
        this.growMushroom(p_225535_1_, p_225535_3_, p_225535_4_, p_225535_2_);
    }
}
