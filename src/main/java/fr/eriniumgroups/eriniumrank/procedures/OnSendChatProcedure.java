package fr.eriniumgroups.eriniumrank.procedures;

import net.minecraftforge.fml.server.ServerLifecycleHooks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.ServerChatEvent;

import net.minecraft.world.IWorld;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.Util;
import net.minecraft.server.MinecraftServer;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.Entity;

import java.util.Map;
import java.util.HashMap;

import fr.eriniumgroups.eriniumrank.EriniumrankModVariables;
import fr.eriniumgroups.eriniumrank.EriniumrankMod;

public class OnSendChatProcedure {
	@Mod.EventBusSubscriber
	private static class GlobalTrigger {
		@SubscribeEvent
		public static void onChat(ServerChatEvent event) {
			ServerPlayerEntity entity = event.getPlayer();
			double i = entity.getPosX();
			double j = entity.getPosY();
			double k = entity.getPosZ();
			Map<String, Object> dependencies = new HashMap<>();
			dependencies.put("x", i);
			dependencies.put("y", j);
			dependencies.put("z", k);
			dependencies.put("world", entity.world);
			dependencies.put("entity", entity);
			dependencies.put("text", event.getMessage());
			dependencies.put("event", event);
			executeProcedure(dependencies);
		}
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				EriniumrankMod.LOGGER.warn("Failed to load dependency world for procedure OnSendChat!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				EriniumrankMod.LOGGER.warn("Failed to load dependency entity for procedure OnSendChat!");
			return;
		}
		if (dependencies.get("text") == null) {
			if (!dependencies.containsKey("text"))
				EriniumrankMod.LOGGER.warn("Failed to load dependency text for procedure OnSendChat!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		Entity entity = (Entity) dependencies.get("entity");
		String text = (String) dependencies.get("text");
		if (dependencies.get("event") != null) {
			Object _obj = dependencies.get("event");
			if (_obj instanceof Event) {
				Event _evt = (Event) _obj;
				if (_evt.isCancelable())
					_evt.setCanceled(true);
			}
		}
		if (!world.isRemote()) {
			MinecraftServer mcserv = ServerLifecycleHooks.getCurrentServer();
			if (mcserv != null)
				mcserv.getPlayerList()
						.func_232641_a_(new StringTextComponent(("<"
								+ (entity.getCapability(EriniumrankModVariables.PLAYER_VARIABLES_CAPABILITY, null)
										.orElse(new EriniumrankModVariables.PlayerVariables())).prefix
								+ " " + entity.getDisplayName().getString() + "\u00A7r> " + text)), ChatType.SYSTEM, Util.DUMMY_UUID);
		}
	}
}
