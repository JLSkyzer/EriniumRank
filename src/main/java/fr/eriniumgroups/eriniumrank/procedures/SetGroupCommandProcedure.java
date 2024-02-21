package fr.eriniumgroups.eriniumrank.procedures;

import net.minecraftforge.fml.loading.FMLPaths;

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

import com.google.gson.JsonObject;
import com.google.gson.GsonBuilder;
import com.google.gson.Gson;

public class SetGroupCommandProcedure {
	public static void execute(CommandContext<CommandSourceStack> arguments, Entity entity) {
		if (entity == null)
			return;
		File file = new File("");
		com.google.gson.JsonObject JsonObject = new com.google.gson.JsonObject();
		if ((new Object() {
			public Entity getEntity() {
				try {
					return EntityArgument.getEntity(arguments, "player");
				} catch (CommandSyntaxException e) {
					e.printStackTrace();
					return null;
				}
			}
		}.getEntity()) instanceof Player || (new Object() {
			public Entity getEntity() {
				try {
					return EntityArgument.getEntity(arguments, "player");
				} catch (CommandSyntaxException e) {
					e.printStackTrace();
					return null;
				}
			}
		}.getEntity()) instanceof ServerPlayer) {
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
							JsonObject = new Gson().fromJson(jsonstringbuilder.toString(), com.google.gson.JsonObject.class);
							if (JsonObject.has("prefix")) {
								{
									String _setval = JsonObject.get("prefix").getAsString();
									(new Object() {
										public Entity getEntity() {
											try {
												return EntityArgument.getEntity(arguments, "player");
											} catch (CommandSyntaxException e) {
												e.printStackTrace();
												return null;
											}
										}
									}.getEntity()).getCapability(EriniumrankModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
										capability.prefix = _setval;
										capability.syncPlayerVariables((new Object() {
											public Entity getEntity() {
												try {
													return EntityArgument.getEntity(arguments, "player");
												} catch (CommandSyntaxException e) {
													e.printStackTrace();
													return null;
												}
											}
										}.getEntity()));
									});
								}
							} else {
								JsonObject.addProperty("prefix", "Need prefix here !");
								{
									Gson mainGSONBuilderVariable = new GsonBuilder().setPrettyPrinting().create();
									try {
										FileWriter fileWriter = new FileWriter(file);
										fileWriter.write(mainGSONBuilderVariable.toJson(JsonObject));
										fileWriter.close();
									} catch (IOException exception) {
										exception.printStackTrace();
									}
								}
								{
									String _setval = JsonObject.get("prefix").getAsString();
									(new Object() {
										public Entity getEntity() {
											try {
												return EntityArgument.getEntity(arguments, "player");
											} catch (CommandSyntaxException e) {
												e.printStackTrace();
												return null;
											}
										}
									}.getEntity()).getCapability(EriniumrankModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
										capability.prefix = _setval;
										capability.syncPlayerVariables((new Object() {
											public Entity getEntity() {
												try {
													return EntityArgument.getEntity(arguments, "player");
												} catch (CommandSyntaxException e) {
													e.printStackTrace();
													return null;
												}
											}
										}.getEntity()));
									});
								}
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					{
						String _setval = StringArgumentType.getString(arguments, "group");
						(new Object() {
							public Entity getEntity() {
								try {
									return EntityArgument.getEntity(arguments, "player");
								} catch (CommandSyntaxException e) {
									e.printStackTrace();
									return null;
								}
							}
						}.getEntity()).getCapability(EriniumrankModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.rank = _setval;
							capability.syncPlayerVariables((new Object() {
								public Entity getEntity() {
									try {
										return EntityArgument.getEntity(arguments, "player");
									} catch (CommandSyntaxException e) {
										e.printStackTrace();
										return null;
									}
								}
							}.getEntity()));
						});
					}
					if (entity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(Component.literal(("\u00A7e" + (new Object() {
							public Entity getEntity() {
								try {
									return EntityArgument.getEntity(arguments, "player");
								} catch (CommandSyntaxException e) {
									e.printStackTrace();
									return null;
								}
							}
						}.getEntity()).getDisplayName().getString() + " \u00A7cest devenu \u00A7e" + StringArgumentType.getString(arguments, "group"))), false);
					if ((new Object() {
						public Entity getEntity() {
							try {
								return EntityArgument.getEntity(arguments, "player");
							} catch (CommandSyntaxException e) {
								e.printStackTrace();
								return null;
							}
						}
					}.getEntity()) instanceof Player _player && !_player.level().isClientSide())
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
}
