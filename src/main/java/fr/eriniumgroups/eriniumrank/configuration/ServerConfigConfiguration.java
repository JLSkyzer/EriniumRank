package fr.eriniumgroups.eriniumrank.configuration;

import net.minecraftforge.common.ForgeConfigSpec;

public class ServerConfigConfiguration {
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec SPEC;
	public static final ForgeConfigSpec.ConfigValue<Boolean> CUSTOMCHAT;
	static {
		BUILDER.push("chat");
		CUSTOMCHAT = BUILDER.comment("Enable custom chat to see prefix").define("Custom Chat", false);
		BUILDER.pop();

		SPEC = BUILDER.build();
	}

}
