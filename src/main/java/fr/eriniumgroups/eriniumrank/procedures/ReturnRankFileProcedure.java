package fr.eriniumgroups.eriniumrank.procedures;

import net.minecraftforge.fml.loading.FMLPaths;

import net.minecraft.world.entity.Entity;

import java.io.File;

import fr.eriniumgroups.eriniumrank.network.EriniumrankModVariables;

import com.google.gson.JsonObject;

public class ReturnRankFileProcedure {
	public static File execute(Entity entity) {
		if (entity == null)
			return new File("");
		com.google.gson.JsonObject JsonObject = new com.google.gson.JsonObject();
		File file = new File("");
		return new File((FMLPaths.GAMEDIR.get().toString() + "/config/eriniumRanks/"),
				File.separator + ((entity.getCapability(EriniumrankModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EriniumrankModVariables.PlayerVariables())).rank + ".json"));
	}
}
