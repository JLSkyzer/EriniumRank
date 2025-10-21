package fr.eriniumgroups.eriniumrank.procedures;

import net.neoforged.fml.loading.FMLPaths;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.CommandSourceStack;

import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;

import fr.eriniumgroups.eriniumrank.network.EriniumrankModVariables;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.StringArgumentType;

public class SetGroupCommandProcedure {
	public static void execute(CommandContext<CommandSourceStack> arguments, Entity entity) {
		if (entity == null)
			return;
		File file = new File("");
		com.google.gson.JsonObject JsonObject = new com.google.gson.JsonObject();
		if ((commandParameterEntity(arguments, "player")) instanceof Player || (commandParameterEntity(arguments, "player")) instanceof ServerPlayer) {
			if (!(StringArgumentType.getString(arguments, "group")).equals("")) {
				file = new File((FMLPaths.GAMEDIR.get().toString() + "/config/eriniumRanks/"), File.separator + (StringArgumentType.getString(arguments, "group") + ".json"));
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
							JsonObject = new com.google.gson.Gson().fromJson(jsonstringbuilder.toString(), com.google.gson.JsonObject.class);
							if (JsonObject.has("prefix")) {
								{
									EriniumrankModVariables.PlayerVariables _vars = (commandParameterEntity(arguments, "player")).getData(EriniumrankModVariables.PLAYER_VARIABLES);
									_vars.prefix = JsonObject.get("prefix").getAsString();
									_vars.syncPlayerVariables((commandParameterEntity(arguments, "player")));
								}
							} else {
								JsonObject.addProperty("prefix", "Need prefix here !");
								{
									com.google.gson.Gson mainGSONBuilderVariable = new com.google.gson.GsonBuilder().setPrettyPrinting().create();
									try {
										FileWriter fileWriter = new FileWriter(file);
										fileWriter.write(mainGSONBuilderVariable.toJson(JsonObject));
										fileWriter.close();
									} catch (IOException exception) {
										exception.printStackTrace();
									}
								}
								{
									EriniumrankModVariables.PlayerVariables _vars = (commandParameterEntity(arguments, "player")).getData(EriniumrankModVariables.PLAYER_VARIABLES);
									_vars.prefix = JsonObject.get("prefix").getAsString();
									_vars.syncPlayerVariables((commandParameterEntity(arguments, "player")));
								}
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					{
						EriniumrankModVariables.PlayerVariables _vars = (commandParameterEntity(arguments, "player")).getData(EriniumrankModVariables.PLAYER_VARIABLES);
						_vars.rank = StringArgumentType.getString(arguments, "group");
						_vars.syncPlayerVariables((commandParameterEntity(arguments, "player")));
					}
					if (entity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(Component.literal(("\u00A7e" + (commandParameterEntity(arguments, "player")).getDisplayName().getString() + " \u00A7cest devenu \u00A7e" + StringArgumentType.getString(arguments, "group"))),
								false);
					if ((commandParameterEntity(arguments, "player")) instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(Component.literal(("\u00A7eVous \u00EAtes devenu : \u00A7c" + StringArgumentType.getString(arguments, "group"))), false);
				} else {
					if (entity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(Component.literal("\u00A7cLe grade n'existe pas !"), false);
				}
			} else {
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("\u00A7cLe grade n'\u00E9xiste pas !"), false);
			}
		} else {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal("\u00A7cVeuillez choisir un joueur !"), false);
		}
	}

	private static Entity commandParameterEntity(CommandContext<CommandSourceStack> arguments, String parameter) {
		try {
			return EntityArgument.getEntity(arguments, parameter);
		} catch (CommandSyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}
}