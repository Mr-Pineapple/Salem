package club.mcmodding.salem.blocks.spell_cauldron;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;
import java.util.stream.Stream;

public class SpellCauldron extends BlockWithEntity implements BlockEntityProvider {

    private final VoxelShape SHAPE = Stream.of(
            Block.createCuboidShape(3, 10, 3, 13, 11, 13),
            Block.createCuboidShape(2, 1, 1, 14, 11, 2),
            Block.createCuboidShape(14, 1, 2, 15, 11, 14),
            Block.createCuboidShape(2, 1, 14, 14, 11, 15),
            Block.createCuboidShape(1, 1, 2, 2, 11, 14),
            Block.createCuboidShape(2, 0, 2, 14, 1, 14),
            Block.createCuboidShape(2, 11, 3, 3, 12, 13),
            Block.createCuboidShape(3, 11, 2, 13, 12, 3),
            Block.createCuboidShape(13, 11, 3, 14, 12, 13),
            Block.createCuboidShape(3, 11, 13, 13, 12, 14),
            Block.createCuboidShape(13, 11.95, 2, 14, 12, 3),
            Block.createCuboidShape(13, 11.95, 13, 14, 12, 14),
            Block.createCuboidShape(2, 11.95, 13, 3, 12, 14),
            Block.createCuboidShape(2, 11.95, 2, 3, 12, 3),
            Block.createCuboidShape(2, 12, 1, 14, 13, 2),
            Block.createCuboidShape(14, 12, 2, 15, 13, 14),
            Block.createCuboidShape(2, 12, 14, 14, 13, 15),
            Block.createCuboidShape(1, 12, 2, 2, 13, 14),
            Block.createCuboidShape(13, 10.95, 2, 14, 11, 3),
            Block.createCuboidShape(13, 10.95, 13, 14, 11, 14),
            Block.createCuboidShape(2, 10.95, 13, 3, 11, 14),
            Block.createCuboidShape(2, 10.95, 2, 3, 11, 3)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR);}).get();

    public SpellCauldron() {
        super(FabricBlockSettings.of(Material.METAL, MaterialColor.STONE).requiresTool().strength(2f).nonOpaque());
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new SpellCauldronBlockEntity();
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
        return ActionResult.SUCCESS;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

}