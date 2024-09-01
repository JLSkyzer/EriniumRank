package fr.eriniumgroups.eriniumrank.procedures;

import net.minecraftforge.fml.loading.FMLPaths;

import net.minecraft.world.entity.Entity;

import java.io.File;

import com.google.gson.JsonObject;

public class ReturnPlayerFileProcedure {
	public static File execute(Entity entity) {
		if (entity == null)
			return new File("");
		com.google.gson.JsonObject JsonObject = new com.google.gson.JsonObject();
		File file = new File("");
		return new File((FMLPaths.GAMEDIR.get().toString() + "/config/eriniumRanks/players/"), File.separator + (entity.getDisplayName().getString() + ".json"));
	}
}
