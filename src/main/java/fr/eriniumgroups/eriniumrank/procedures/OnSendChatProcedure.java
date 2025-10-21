package fr.eriniumgroups.eriniumrank.procedures;

import net.neoforged.neoforge.event.ServerChatEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.ModList;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ClickEvent;

import javax.annotation.Nullable;

import fr.eriniumgroups.eriniumrank.network.EriniumrankModVariables;

@EventBusSubscriber
public class OnSendChatProcedure {
	@SubscribeEvent
	public static void onChat(ServerChatEvent event) {
		execute(event, event.getPlayer().level(), event.getPlayer(), event.getRawText());
	}

	public static void execute(LevelAccessor world, Entity entity, String text) {
		execute(null, world, entity, text);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity, String text) {
		if (entity == null || text == null)
			return;
		MutableComponent mutableComponent = Component.empty();
		if (event instanceof ICancellableEvent _cancellable) {
			_cancellable.setCanceled(true);
		}
		if (ModList.get().isLoaded("erinium_faction")) {
			if (!(ReturnFactionIDProcedure.execute(entity)).equals("wilderness")) {
				mutableComponent.append(Component.literal("<<\u00A7a"));
				mutableComponent.append(new Object() {
					MutableComponent hoverEvent(MutableComponent comp, MutableComponent value) {
						return comp.withStyle(_style -> _style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, value)));
					}
				}.hoverEvent(new Object() {
					MutableComponent clickEvent(MutableComponent comp, ClickEvent.Action type, String value) {
						return comp.withStyle(_style -> _style.withClickEvent(new ClickEvent(type, value)));
					}
				}.clickEvent(Component.literal(ReturnFactionDisplayNameProcedure.execute(entity)), ClickEvent.Action.RUN_COMMAND, ("/f f " + ReturnFactionIDProcedure.execute(entity))),
						Component.literal(("\u00A7aOpen faction info of \u00A7e" + ReturnFactionIDProcedure.execute(entity)))));
				mutableComponent.append(Component.literal("\u00A7r> "));
				mutableComponent.append(Component.literal(entity.getData(EriniumrankModVariables.PLAYER_VARIABLES).prefix));
				mutableComponent.append(Component.literal(" "));
				mutableComponent.append(Component.literal((entity.getDisplayName().getString())));
				mutableComponent.append(Component.literal("\u00A7r> "));
				mutableComponent.append(Component.literal(text));
				if (!world.isClientSide() && world.getServer() != null) {
					world.getServer().getPlayerList().broadcastSystemMessage(mutableComponent, false);
				}
			} else {
				mutableComponent.append(Component.literal("<"));
				mutableComponent.append(Component.literal(entity.getData(EriniumrankModVariables.PLAYER_VARIABLES).prefix));
				mutableComponent.append(Component.literal(" "));
				mutableComponent.append(Component.literal((entity.getDisplayName().getString())));
				mutableComponent.append(Component.literal("\u00A7r> "));
				mutableComponent.append(Component.literal(text));
				if (!world.isClientSide() && world.getServer() != null) {
					world.getServer().getPlayerList().broadcastSystemMessage(mutableComponent, false);
				}
			}
		} else {
			mutableComponent.append(Component.literal("<"));
			mutableComponent.append(Component.literal(entity.getData(EriniumrankModVariables.PLAYER_VARIABLES).prefix));
			mutableComponent.append(Component.literal(" "));
			mutableComponent.append(Component.literal((entity.getDisplayName().getString())));
			mutableComponent.append(Component.literal("\u00A7r> "));
			mutableComponent.append(Component.literal(text));
			if (!world.isClientSide() && world.getServer() != null) {
				world.getServer().getPlayerList().broadcastSystemMessage(mutableComponent, false);
			}
		}
	}
}