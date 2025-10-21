package fr.eriniumgroups.eriniumrank.procedures;

import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;

import fr.eriniumgroups.eriniumrank.network.EriniumrankModVariables;

@EventBusSubscriber
public class OnJoinServerProcedure {
	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		execute(event, event.getEntity());
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		File file = new File("");
		com.google.gson.JsonObject MainJsonObject = new com.google.gson.JsonObject();
		com.google.gson.JsonObject SecJsonObject = new com.google.gson.JsonObject();
		if ((entity.getData(EriniumrankModVariables.PLAYER_VARIABLES).rank).equals("") || (entity.getData(EriniumrankModVariables.PLAYER_VARIABLES).rank).equals("\"\"")) {
			{
				EriniumrankModVariables.PlayerVariables _vars = entity.getData(EriniumrankModVariables.PLAYER_VARIABLES);
				_vars.rank = "default";
				_vars.syncPlayerVariables(entity);
			}
		}
		file = new File((FMLPaths.GAMEDIR.get().toString() + "/config/eriniumRanks/"), File.separator + (entity.getData(EriniumrankModVariables.PLAYER_VARIABLES).rank + ".json"));
		if (file.exists()) {
			{
				try {
					BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
					StringBuilder jsonstringbuilder = new StringBuilder();
					String line;
					while ((line = bufferedReader.readLine()) != null) {
						jsonstringbuilder.append(line);
					}
					bufferedReader.close();
					MainJsonObject = new com.google.gson.Gson().fromJson(jsonstringbuilder.toString(), com.google.gson.JsonObject.class);
					if (MainJsonObject.has("prefix")) {
						{
							EriniumrankModVariables.PlayerVariables _vars = entity.getData(EriniumrankModVariables.PLAYER_VARIABLES);
							_vars.prefix = MainJsonObject.get("prefix").getAsString();
							_vars.syncPlayerVariables(entity);
						}
					} else {
						MainJsonObject.addProperty("prefix", "Need prefix here !");
						{
							com.google.gson.Gson mainGSONBuilderVariable = new com.google.gson.GsonBuilder().setPrettyPrinting().create();
							try {
								FileWriter fileWriter = new FileWriter(file);
								fileWriter.write(mainGSONBuilderVariable.toJson(MainJsonObject));
								fileWriter.close();
							} catch (IOException exception) {
								exception.printStackTrace();
							}
						}
						{
							EriniumrankModVariables.PlayerVariables _vars = entity.getData(EriniumrankModVariables.PLAYER_VARIABLES);
							_vars.prefix = MainJsonObject.get("prefix").getAsString();
							_vars.syncPlayerVariables(entity);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		file = new File((FMLPaths.GAMEDIR.get().toString() + "/config/eriniumRanks/players/"), File.separator + (entity.getDisplayName().getString() + ".json"));
		if (!file.exists()) {
			try {
				file.getParentFile().mkdirs();
				file.createNewFile();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
			SecJsonObject.addProperty("setgroup.command", false);
			{
				com.google.gson.Gson mainGSONBuilderVariable = new com.google.gson.GsonBuilder().setPrettyPrinting().create();
				try {
					FileWriter fileWriter = new FileWriter(file);
					fileWriter.write(mainGSONBuilderVariable.toJson(SecJsonObject));
					fileWriter.close();
				} catch (IOException exception) {
					exception.printStackTrace();
				}
			}
		}
	}
}