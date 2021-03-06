package monkeysLockette;

import java.util.ArrayList;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.scheduler.BukkitRunnable;

public class DoorThread extends BukkitRunnable {
	public static ArrayList<DoorHolder> doors = new ArrayList<DoorHolder>();
	public static int doorindex(Block door)
	{
		for (int i = 0;i < doors.size();i++)
		{
			if (door.getLocation().equals(doors.get(i).Door.getLocation()))
			{
				return i;
			}
		}
		return -1;
	}
	public static void removedoor(Block door)
	{
		Block ddoor = door.getRelative(BlockFace.DOWN);
		if (ddoor.getType() != door.getType())
		{
			ddoor = door;
		}
		int ind = doorindex(ddoor);
		if (ind > -1)
		{
			doors.remove(ind);
		}
	}
	public static void toggledoor(Block door)
	{
		Block ddoor = door.getRelative(BlockFace.DOWN);
		if (ddoor.getType() != door.getType())
		{
			ddoor = door;
		}
		int ind = doorindex(ddoor);
		if (ind > -1)
		{
			doors.remove(ind);
			monkeysLockette.DebugInfo("Door remove!");
		}
		else
		{
			doors.add(new DoorHolder(ddoor, monkeysLockette.CloseDoorsAfter));
			monkeysLockette.DebugInfo("Door add!");
		}
	}
	@Override
	public void run()
	{
		int size = doors.size();
		for (int i = 0;i < size;i++)
		{
			if (!doors.get(i).dec())
			{
				doors.remove(i);
				size = doors.size();
				i--;
			}
		}
	}
}
