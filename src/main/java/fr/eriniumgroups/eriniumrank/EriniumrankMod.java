/*
 *    MCreator note:
 *
 *    If you lock base mod element files, you can edit this file and it won't get overwritten.
 *    If you change your modid or package, you need to apply these changes to this file MANUALLY.
 *
 *    Settings in @Mod annotation WON'T be changed in case of the base mod element
 *    files lock too, so you need to set them manually here in such case.
 *
 *    If you do not lock base mod element files in Workspace settings, this file
 *    will be REGENERATED on each build.
 *
 */
package fr.eriniumgroups.eriniumrank;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.IEventBus;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.Minecraft;

import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.BiConsumer;
import java.util.Map;
import java.util.HashMap;

import java.lang.reflect.Field;

import java.io.StringWriter;
import java.io.PrintWriter;

@Mod("eriniumrank")
public class EriniumrankMod {
	public static final Logger LOGGER = LogManager.getLogger(EriniumrankMod.class);
	public static final String MODID = "eriniumrank";
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(MODID, MODID), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	private static int messageID = 0;

	public EriniumrankMod() {

		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

	}

	public static <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
		PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
		messageID++;
	}

	public static class TextboxSetMessage {
		private final String textboxid;
		private final String data;

		public TextboxSetMessage(FriendlyByteBuf buffer) {
			this.textboxid = buffer.readUtf();
			this.data = buffer.readUtf();
		}

		public TextboxSetMessage(String textboxid, String data) {
			this.textboxid = textboxid;
			this.data = data;
		}

		public static void buffer(TextboxSetMessage message, FriendlyByteBuf buffer) {
			buffer.writeUtf(message.textboxid);
			buffer.writeUtf(message.data);
		}

		public static void handler(TextboxSetMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> {
				if (!context.getDirection().getReceptionSide().isServer() && message.data != null) {
					Screen currentScreen = Minecraft.getInstance().screen;
					Map<String, EditBox> textFieldsMap = new HashMap<>();
					if (currentScreen != null) {
						Field[] fields = currentScreen.getClass().getDeclaredFields();
						for (Field field : fields) {
							if (EditBox.class.isAssignableFrom(field.getType())) {
								try {
									field.setAccessible(true);
									EditBox textField = (EditBox) field.get(currentScreen);
									if (textField != null) {
										textFieldsMap.put(field.getName(), textField);
									}
								} catch (IllegalAccessException ex) {
									StringWriter sw = new StringWriter();
									PrintWriter pw = new PrintWriter(sw);
									ex.printStackTrace(pw);
									String exceptionAsString = sw.toString();
									EriniumrankMod.LOGGER.error(exceptionAsString);
								}
							}
						}
					}
					if (textFieldsMap.get(message.textboxid) != null) {
						textFieldsMap.get(message.textboxid).setValue(message.data);
					}
				}
			});
			context.setPacketHandled(true);
		}
	}

	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class initer {
		@SubscribeEvent
		public static void init(FMLCommonSetupEvent event) {
			EriniumrankMod.addNetworkMessage(TextboxSetMessage.class, TextboxSetMessage::buffer, TextboxSetMessage::new, TextboxSetMessage::handler);
		}
	}
}
