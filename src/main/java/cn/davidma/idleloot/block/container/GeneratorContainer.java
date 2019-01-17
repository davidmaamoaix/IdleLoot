package cn.davidma.idleloot.block.container;

import cn.davidma.idleloot.tileentity.GeneratorTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GeneratorContainer extends Container {

	private GeneratorTileEntity tileEntity;
	private int currProgress, totalProgress;
	
	public GeneratorContainer(InventoryPlayer player, GeneratorTileEntity tileEntity) {
		this.tileEntity = tileEntity;
		addSlotToContainer(new GeneratorSlot(tileEntity, 0, 80, 25));
		
		// Player hotbar slots.
		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(player, i, i*18 + 8, 142));
		}
		
		// PLayer inventory slots.
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 3; j++) {
				addSlotToContainer(new Slot(player, i + j*9 + 9, i*18 + 8, j*18 + 84));
			}
		}
	}
	
	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		listener.sendAllWindowProperties(this, tileEntity);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (IContainerListener i: listeners) {
			if (currProgress != tileEntity.getField(0)) i.sendWindowProperty(this, 0, tileEntity.getField(0));
			if (totalProgress != tileEntity.getField(1)) i.sendWindowProperty(this, 1, tileEntity.getField(1));
		}
		currProgress = tileEntity.getField(0);
		totalProgress = tileEntity.getField(1);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int id, int data) {
		tileEntity.setField(id, data);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tileEntity.isUsableByPlayer(player);
	}
}