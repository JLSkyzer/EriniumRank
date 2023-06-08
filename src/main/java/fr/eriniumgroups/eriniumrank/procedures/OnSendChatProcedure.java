package fr.eriniumgroups.eriniumrank.procedures;

import net.minecraftforge.server.ServerLifecycleHooks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.ServerChatEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.ChatType;
import net.minecraft.Util;

import javax.annotation.Nullable;

import fr.eriniumgroups.eriniumrank.network.EriniumrankModVariables;

@Mod.EventBusSubscriber
public class OnSendChatProcedure {
	@SubscribeEvent
	public static void onChat(ServerChatEvent event) {
		execute(event, event.getPlayer().level, event.getPlayer(), event.getMessage());
	}

	public static void execute(LevelAccessor world, Entity entity, String text) {
		execute(null, world, entity, text);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity, String text) {
		if (entity == null || text == null)
			return;
		if (event != null && event.isCancelable()) {
			event.setCanceled(true);
		}
		if (!world.isClientSide()) {
			MinecraftServer _mcserv = ServerLifecycleHooks.getCurrentServer();
			if (_mcserv != null)
				_mcserv.getPlayerList()
						.broadcastMessage(new TextComponent(("<"
								+ (entity.getCapability(EriniumrankModVariables.PLAYER_VARIABLES_CAPABILITY, null)
										.orElse(new EriniumrankModVariables.PlayerVariables())).prefix
								+ " " + entity.getDisplayName().getString() + "\u00A7r> " + text)), ChatType.SYSTEM, Util.NIL_UUID);
		}
	}
}
