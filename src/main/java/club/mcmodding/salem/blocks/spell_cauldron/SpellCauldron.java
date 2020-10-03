package club.mcmodding.salem.blocks.spell_cauldron;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class SpellCauldron extends BlockWithEntity implements BlockEntityProvider {

    public SpellCauldron() {
        super(FabricBlockSettings.of(Material.METAL, MaterialColor.STONE).requiresTool().strength(2f).nonOpaque());
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new SpellCauldronBlockEntity();
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return super.getOutlineShape(state, world, pos, context);
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
