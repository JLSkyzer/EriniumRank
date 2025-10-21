package fr.eriniumgroups.eriniumrank.procedures;

import net.neoforged.fml.loading.FMLPaths;

import net.minecraft.world.entity.Entity;

import java.io.IOException;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;

import fr.eriniumgroups.eriniumrank.network.EriniumrankModVariables;

public class ReturnRankPermProcedure {
	public static String execute(Entity entity) {
		if (entity == null)
			return "";
		File File = new File("");
		com.google.gson.JsonObject JsonObject = new com.google.gson.JsonObject();
		String JsonStrings = "";
		File = new File((FMLPaths.GAMEDIR.get().toString() + "/config/eriniumRanks/"), File.separator + (entity.getData(EriniumrankModVariables.PLAYER_VARIABLES).rank + ".json"));
		{
			try {
				BufferedReader bufferedReader = new BufferedReader(new FileReader(File));
				StringBuilder jsonstringbuilder = new StringBuilder();
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					jsonstringbuilder.append(line);
				}
				bufferedReader.close();
				JsonObject = new com.google.gson.Gson().fromJson(jsonstringbuilder.toString(), com.google.gson.JsonObject.class);
				JsonStrings = JsonObject.toString();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return JsonStrings;
	}
}