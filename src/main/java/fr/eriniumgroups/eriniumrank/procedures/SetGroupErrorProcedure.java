package fr.eriniumgroups.eriniumrank.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

public class SetGroupErrorProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof Player _player && !_player.level.isClientSide())
			_player.displayClientMessage(Component.literal("\u00A7cMauvaise commande, faites /setgroup <player> <groupname>"), (false));
	}
}
