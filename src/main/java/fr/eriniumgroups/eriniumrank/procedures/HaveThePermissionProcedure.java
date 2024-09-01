package fr.eriniumgroups.eriniumrank.procedures;

import net.minecraft.world.entity.Entity;

import java.io.IOException;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;

import com.google.gson.JsonObject;
import com.google.gson.Gson;

public class HaveThePermissionProcedure {
	public static boolean execute(Entity entity, String permission) {
		if (entity == null || permission == null)
			return false;
		File File = new File("");
		com.google.gson.JsonObject JsonObject = new com.google.gson.JsonObject();
		com.google.gson.JsonObject SecJsonObject = new com.google.gson.JsonObject();
		boolean result = false;
		File = ReturnRankFileProcedure.execute(entity);
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
				if (JsonObject.has(permission)) {
					result = JsonObject.get(permission).getAsBoolean();
				} else if (JsonObject.has("*")) {
					result = true;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (!result) {
			File = ReturnPlayerFileProcedure.execute(entity);
			{
				try {
					BufferedReader bufferedReader = new BufferedReader(new FileReader(File));
					StringBuilder jsonstringbuilder = new StringBuilder();
					String line;
					while ((line = bufferedReader.readLine()) != null) {
						jsonstringbuilder.append(line);
					}
					bufferedReader.close();
					SecJsonObject = new Gson().fromJson(jsonstringbuilder.toString(), com.google.gson.JsonObject.class);
					if (SecJsonObject.has(permission)) {
						result = SecJsonObject.get(permission).getAsBoolean();
					} else if (SecJsonObject.has("*")) {
						result = true;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}
