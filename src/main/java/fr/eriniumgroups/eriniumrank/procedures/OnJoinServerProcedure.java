package fr.eriniumgroups.eriniumrank.procedures;

import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.player.PlayerEvent;

import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;

import fr.eriniumgroups.eriniumrank.network.EriniumrankModVariables;

import com.google.gson.GsonBuilder;
import com.google.gson.Gson;

@Mod.EventBusSubscriber
public class OnJoinServerProcedure {
	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		execute(event, event.getPlayer());
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
		if (((entity.getCapability(EriniumrankModVariables.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new EriniumrankModVariables.PlayerVariables())).rank).equals("")
				|| ((entity.getCapability(EriniumrankModVariables.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new EriniumrankModVariables.PlayerVariables())).rank).equals("\"\"")) {
			{
				String _setval = "default";
				entity.getCapability(EriniumrankModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.rank = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		}
		file = new File((FMLPaths.GAMEDIR.get().toString() + "/config/eriniumRanks/"),
				File.separator + ((entity.getCapability(EriniumrankModVariables.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new EriniumrankModVariables.PlayerVariables())).rank + ".json"));
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
					MainJsonObject = new Gson().fromJson(jsonstringbuilder.toString(), com.google.gson.JsonObject.class);
					if (MainJsonObject.get("prefix") != null) {
						{
							String _setval = MainJsonObject.get("prefix").getAsString();
							entity.getCapability(EriniumrankModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
								capability.prefix = _setval;
								capability.syncPlayerVariables(entity);
							});
						}
					} else {
						MainJsonObject.addProperty("prefix", "Need prefix here !");
						{
							Gson mainGSONBuilderVariable = new GsonBuilder().setPrettyPrinting().create();
							try {
								FileWriter fileWriter = new FileWriter(file);
								fileWriter.write(mainGSONBuilderVariable.toJson(MainJsonObject));
								fileWriter.close();
							} catch (IOException exception) {
								exception.printStackTrace();
							}
						}
						{
							String _setval = MainJsonObject.get("prefix").getAsString();
							entity.getCapability(EriniumrankModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
								capability.prefix = _setval;
								capability.syncPlayerVariables(entity);
							});
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		file = new File((FMLPaths.GAMEDIR.get().toString() + "/config/eriniumRanks/players/"),
				File.separator + (entity.getDisplayName().getString() + ".json"));
		if (!file.exists()) {
			try {
				file.getParentFile().mkdirs();
				file.createNewFile();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
			SecJsonObject.addProperty("setgroup.command", (false));
			{
				Gson mainGSONBuilderVariable = new GsonBuilder().setPrettyPrinting().create();
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
