package primetoxinz.fluxedsolars.block;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import primetoxinz.fluxedsolars.tile.SolarPanelType;
import primetoxinz.fluxedsolars.tile.TileSolar;

public class BlockSolar extends Block implements ITileEntityProvider{

	public static final PropertyEnum<SolarPanelType> TIER = PropertyEnum.create("tier", SolarPanelType.class);
	protected Class tileclass;
	protected String name;
	protected Class itemblock;
	
	public BlockSolar(String name, Class<? extends ItemBlock> itemblock,Class<? extends TileEntity> tileentity) {
		super(Material.iron);
		this.name = name;
		this.itemblock = itemblock;
		this.tileclass = tileentity;
		
		GameRegistry.registerBlock(this,itemblock, name);
		GameRegistry.registerTileEntity(tileclass, name);
		
		setUnlocalizedName(name);
		setCreativeTab(CreativeTabs.tabBlock);
		setDefaultState(this.blockState.getBaseState().withProperty(TIER, SolarPanelType.REDSTONE));
		setHardness(5);
		setResistance(10);
	}

	@Override
	protected BlockState createBlockState() {
	    return new BlockState(this, new IProperty[] {TIER});
	}
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(TIER, SolarPanelType.VALUES[meta]);
	}
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TIER).ordinal();
	}
	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
		for(int i =0;i < 4;i++) {
			list.add(new ItemStack(this, 1, i));
		}
	}
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		worldIn.setBlockState(pos, getStateFromMeta(stack.getItemDamage()));
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileSolar();
	}
	
	@Override
	public boolean onBlockEventReceived(World worldIn, BlockPos pos, IBlockState state, int eventID, int eventParam) {
	    super.onBlockEventReceived(worldIn, pos, state, eventID, eventParam);
	    worldIn.markBlockForUpdate(pos);
	    worldIn.markBlockRangeForRenderUpdate(pos,pos);
	    TileEntity tileentity = worldIn.getTileEntity(pos);
	    return tileentity == null ? false : tileentity.receiveClientEvent(eventID, eventParam);
	}
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		List<ItemStack> list = new ArrayList<ItemStack>();
		list.add(new ItemStack(this,1,getMetaFromState(state)));
		return list;
	}
    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        super.breakBlock(world, pos, state);
        world.removeTileEntity(pos);
    }
}
