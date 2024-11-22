package fr.eriniumgroups.eriniumrank.init;

import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import fr.eriniumgroups.eriniumrank.configuration.ServerConfigConfiguration;
import fr.eriniumgroups.eriniumrank.EriniumrankMod;

@Mod.EventBusSubscriber(modid = EriniumrankMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EriniumrankModConfigs {
	@SubscribeEvent
	public static void register(FMLConstructModEvent event) {
		event.enqueueWork(() -> {
			ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerConfigConfiguration.SPEC, "EriniumRank.toml");
		});
	}
}
