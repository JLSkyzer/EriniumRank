package fr.eriniumgroups.eriniumrank.procedures;

import net.minecraft.util.text.StringTextComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;

import java.util.Map;

import fr.eriniumgroups.eriniumrank.EriniumrankMod;

public class SetGroupErrorProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				EriniumrankMod.LOGGER.warn("Failed to load dependency entity for procedure SetGroupError!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
			((PlayerEntity) entity).sendStatusMessage(new StringTextComponent("\u00A7cMauvaise commande, faites /setgroup <player> <groupname>"),
					(false));
		}
	}
}
