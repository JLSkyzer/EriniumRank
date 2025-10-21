package fr.eriniumgroups.eriniumrank.procedures;

import net.neoforged.fml.loading.FMLPaths;

import net.minecraft.world.entity.Entity;

import java.io.IOException;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;

public class ReturnFactionIDProcedure {
	public static String execute(Entity entity) {
		if (entity == null)
			return "";
		File File = new File("");
		com.google.gson.JsonObject JsonObject = new com.google.gson.JsonObject();
		String JsonStrings = "";
		String filename = "";
		filename = entity.getStringUUID() + ".json";
		File = new File((FMLPaths.GAMEDIR.get().toString() + "/" + "erinium_faction" + "/" + "players/"), File.separator + filename);
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
				JsonStrings = JsonObject.get("faction").getAsString();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return JsonStrings;
	}
}