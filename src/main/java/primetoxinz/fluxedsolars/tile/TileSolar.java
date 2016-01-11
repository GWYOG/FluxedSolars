package primetoxinz.fluxedsolars.tile;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import primetoxinz.fluxedsolars.block.BlockSolar;
import primetoxinz.fluxedsolars.tile.TileGenerator;

public class TileSolar extends TileGenerator {
	public boolean running = true;

	public TileSolar() {
		super(1, 32000);
	}

	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		return true;
	}

	@Override
	public void update() {
		IBlockState state = worldObj.getBlockState(pos);
		SolarPanelType TIER = state.getValue(BlockSolar.TIER);
		setGeneration(TIER.energypertick);
		setCapacity(TIER.energystorage);
		int particles = 0;
		if(worldObj.isRemote) {
			particles = Minecraft.getMinecraft().gameSettings.particleSetting;
		}
		if (!worldObj.isRemote) {
			running = canGenerate(pos);
			worldObj.markBlockForUpdate(pos);
		}
		if (running) {
			if (particles < 2) {
				EnumParticleTypes type = EnumParticleTypes.REDSTONE;
				if (TIER == SolarPanelType.ENDER)
					type = EnumParticleTypes.PORTAL;
				else if (TIER == SolarPanelType.BLAZE)
					type = EnumParticleTypes.FLAME;
				spawnParticles(worldObj, pos, particles, type);
			}
			creatingPower();
		}
		sendPower();

	}

	private void spawnParticles(World worldIn, BlockPos pos, int particles, EnumParticleTypes type) {
		Random random = worldIn.rand;
		double d0 = 0.0625D;
		if (worldIn.getTotalWorldTime() % (particles == 1 ? 80 : 20) == 0) {
			for (int i = 0; i < 6; ++i) {
				double d1 = (double) ((float) pos.getX() + random.nextFloat());
				double d2 = (double) ((float) pos.getY() + random.nextFloat());
				double d3 = (double) ((float) pos.getZ() + random.nextFloat());

				if (i == 0 && !worldIn.getBlockState(pos.up()).getBlock().isOpaqueCube()) {
					d2 = (double) pos.getY() + d0 + 1.0D;
				}

				if (i == 1 && !worldIn.getBlockState(pos.down()).getBlock().isOpaqueCube()) {
					d2 = (double) pos.getY() - d0;
				}

				if (i == 2 && !worldIn.getBlockState(pos.south()).getBlock().isOpaqueCube()) {
					d3 = (double) pos.getZ() + d0 + 1.0D;
				}

				if (i == 3 && !worldIn.getBlockState(pos.north()).getBlock().isOpaqueCube()) {
					d3 = (double) pos.getZ() - d0;
				}

				if (i == 4 && !worldIn.getBlockState(pos.east()).getBlock().isOpaqueCube()) {
					d1 = (double) pos.getX() + d0 + 1.0D;
				}

				if (i == 5 && !worldIn.getBlockState(pos.west()).getBlock().isOpaqueCube()) {
					d1 = (double) pos.getX() - d0;
				}

				if (d1 < (double) pos.getX() || d1 > (double) (pos.getX() + 1) || d2 < 0.0D
						|| d2 > (double) (pos.getY() + 1) || d3 < (double) pos.getZ()
						|| d3 > (double) (pos.getZ() + 1)) {
					worldIn.spawnParticle(type, d1, pos.getY() + 15f / 16f, d3, 0.0D, 0.0D, 0.0D, new int[0]);
				}
			}
		}
	}

	@Override
	public void writeCustomNBT(NBTTagCompound nbt, boolean descPacket) {
		super.writeCustomNBT(nbt, descPacket);
		nbt.setBoolean("running", running);

	}

	@Override
	public void readCustomNBT(NBTTagCompound nbt, boolean descPacket) {
		super.readCustomNBT(nbt, descPacket);
		running = nbt.getBoolean("running");
	}

	public boolean canGenerate(BlockPos pos) {
		boolean sky = worldObj.canSeeSky(pos.up());
		boolean time = (worldObj.isDaytime());
		boolean weather = !worldObj.isRaining();
		boolean isBlaze = worldObj.getBlockState(pos).getValue(BlockSolar.TIER) == SolarPanelType.BLAZE;
		boolean isNether = worldObj.provider.getDimensionId() == -1;
		boolean isEnder = worldObj.getBlockState(pos).getValue(BlockSolar.TIER) == SolarPanelType.ENDER;
		boolean full = storage.getEnergyStored() == storage.getMaxEnergyStored();
		return !full && ((isEnder && !isNether) || (isNether && isBlaze) || (time && sky && weather));
	}
}
