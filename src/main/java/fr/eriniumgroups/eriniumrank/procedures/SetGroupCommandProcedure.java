package fr.eriniumgroups.eriniumrank.procedures;

import net.minecraftforge.fml.loading.FMLPaths;

import net.minecraft.util.text.StringTextComponent;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.CommandSource;

import java.util.Map;

import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;

import fr.eriniumgroups.eriniumrank.EriniumrankModVariables;
import fr.eriniumgroups.eriniumrank.EriniumrankMod;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.StringArgumentType;

import com.google.gson.JsonObject;
import com.google.gson.GsonBuilder;
import com.google.gson.Gson;

public class SetGroupCommandProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("arguments") == null) {
			if (!dependencies.containsKey("arguments"))
				EriniumrankMod.LOGGER.warn("Failed to load dependency arguments for procedure SetGroupCommand!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				EriniumrankMod.LOGGER.warn("Failed to load dependency entity for procedure SetGroupCommand!");
			return;
		}
		CommandContext<CommandSource> arguments = (CommandContext<CommandSource>) dependencies.get("arguments");
		Entity entity = (Entity) dependencies.get("entity");
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
		}.getEntity()) instanceof PlayerEntity || (new Object() {
			public Entity getEntity() {
				try {
					return EntityArgument.getEntity(arguments, "player");
				} catch (CommandSyntaxException e) {
					e.printStackTrace();
					return null;
				}
			}
		}.getEntity()) instanceof ServerPlayerEntity) {
			if (!(StringArgumentType.getString(arguments, "group")).equals("")) {
				file = (File) new File((FMLPaths.GAMEDIR.get().toString() + "/config/eriniumRanks/"),
						File.separator + (StringArgumentType.getString(arguments, "group") + ".json"));
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
							if (JsonObject.get("prefix") != null) {
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
						String _setval = (StringArgumentType.getString(arguments, "group"));
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
					if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
						((PlayerEntity) entity).sendStatusMessage(new StringTextComponent(("\u00A7e" + (new Object() {
							public Entity getEntity() {
								try {
									return EntityArgument.getEntity(arguments, "player");
								} catch (CommandSyntaxException e) {
									e.printStackTrace();
									return null;
								}
							}
						}.getEntity()).getDisplayName().getString() + " \u00A7cest devenu \u00A7e"
								+ StringArgumentType.getString(arguments, "group"))), (false));
					}
					if ((new Object() {
						public Entity getEntity() {
							try {
								return EntityArgument.getEntity(arguments, "player");
							} catch (CommandSyntaxException e) {
								e.printStackTrace();
								return null;
							}
						}
					}.getEntity()) instanceof PlayerEntity && !(new Object() {
						public Entity getEntity() {
							try {
								return EntityArgument.getEntity(arguments, "player");
							} catch (CommandSyntaxException e) {
								e.printStackTrace();
								return null;
							}
						}
					}.getEntity()).world.isRemote()) {
						((PlayerEntity) (new Object() {
							public Entity getEntity() {
								try {
									return EntityArgument.getEntity(arguments, "player");
								} catch (CommandSyntaxException e) {
									e.printStackTrace();
									return null;
								}
							}
						}.getEntity())).sendStatusMessage(new StringTextComponent(
								("\u00A7eVous \u00EAtes devenu : \u00A7c" + StringArgumentType.getString(arguments, "group"))), (false));
					}
				} else {
					if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
						((PlayerEntity) entity).sendStatusMessage(new StringTextComponent("\u00A7cLe grade n'existe pas !"), (false));
					}
				}
			} else {
				if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
					((PlayerEntity) entity).sendStatusMessage(new StringTextComponent("\u00A7cLe grade n'\u00E9xiste pas !"), (false));
				}
			}
		} else {
			if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
				((PlayerEntity) entity).sendStatusMessage(new StringTextComponent("\u00A7cVeuillez choisir un joueur !"), (false));
			}
		}
	}
}
