package primetoxinz.fluxedsolars.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyConnection;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public abstract class TileGenerator extends TileBase implements IEnergyProvider, ITickable {
	protected int generates;
	protected int capacity;
	protected EnergyStorage storage;

	public TileGenerator(int generates, int capacity) {
		this.generates = generates;
		this.capacity = capacity;
		storage = new EnergyStorage(capacity);
	}
	public void setGeneration(int generates) {
		this.generates = generates;
		storage.setMaxExtract(generates);
	}
	public void setCapacity(int capacity) {
		this.capacity=capacity;
		storage.setCapacity(capacity);
	}
	public void sendPower() {
		for (EnumFacing direction : EnumFacing.values()) {
			int extracted = getEnergyStored(direction);

			TileEntity tile = worldObj.getTileEntity(new BlockPos(getPos().getX() + direction.getFrontOffsetX(),
					getPos().getY() + direction.getFrontOffsetY(), getPos().getZ() + direction.getFrontOffsetZ()));
			if (isPoweredTile(tile, direction)) {
				if (canConnectEnergy(direction)) {
					if (tile instanceof IEnergyHandler) {
						System.out.println(tile.getBlockType().getUnlocalizedName());
						IEnergyHandler handler = (IEnergyHandler) tile;
						int neededRF = handler.receiveEnergy(direction.getOpposite(), extracted, false);

						extractEnergy(direction.getOpposite(), neededRF, false);
					} else if (tile instanceof IEnergyReceiver) {
						IEnergyReceiver handler = (IEnergyReceiver) tile;
						int neededRF = handler.receiveEnergy(direction.getOpposite(), extracted, false);

						extractEnergy(direction.getOpposite(), neededRF, false);
					}
				}

			}
		}
	}
	public boolean creatingPower() {
		if(storage.getEnergyStored() < storage.getMaxEnergyStored()) {
			storage.modifyEnergyStored(generates);
			return true;
		}
		else {
			return false;
		}
	}
	public boolean isPoweredTile(TileEntity tile, EnumFacing side) {
		if (tile == null) {
			return false;
		} else if (tile instanceof IEnergyHandler || tile instanceof IEnergyReceiver) {
			return ((IEnergyConnection) tile).canConnectEnergy(side.getOpposite());
		} else {
			return false;
		}
	}

	@Override
	public void readCustomNBT(NBTTagCompound nbt, boolean descPacket) {
		storage.readFromNBT(nbt);
	}

	@Override
	public void writeCustomNBT(NBTTagCompound nbt, boolean descPacket) {
		storage.writeToNBT(nbt);
	}

	@Override
	public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
		return storage.extractEnergy(maxExtract, simulate);
	}

	@Override
	public int getEnergyStored(EnumFacing from) {
		return storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		return storage.getMaxEnergyStored();
	}
}
