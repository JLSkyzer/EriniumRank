package fr.eriniumgroups.eriniumrank.procedures;

import net.minecraftforge.fml.loading.FMLPaths;

import net.minecraft.world.entity.Entity;

import java.io.IOException;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;

import com.google.gson.JsonObject;
import com.google.gson.Gson;

public class ReturnPlayerPermProcedure {
	public static String execute(Entity entity) {
		if (entity == null)
			return "";
		File File = new File("");
		com.google.gson.JsonObject JsonObject = new com.google.gson.JsonObject();
		String JsonStrings = "";
		File = new File((FMLPaths.GAMEDIR.get().toString() + "/config/eriniumRanks/players/"), File.separator + (entity.getDisplayName().getString() + ".json"));
		{
			try {
				BufferedReader bufferedReader = new BufferedReader(new FileReader(File));
				StringBuilder jsonstringbuilder = new StringBuilder();
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					jsonstringbuilder.append(line);
				}
				bufferedReader.close();
				JsonObject = new Gson().fromJson(jsonstringbuilder.toString(), com.google.gson.JsonObject.class);
				JsonStrings = JsonObject.toString();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return JsonStrings;
	}
}
