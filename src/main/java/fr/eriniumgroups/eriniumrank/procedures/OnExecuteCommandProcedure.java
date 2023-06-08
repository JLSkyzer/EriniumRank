package fr.eriniumgroups.eriniumrank.procedures;

import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.CommandEvent;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.command.ICommandSource;
import net.minecraft.command.CommandSource;

import java.util.Map;
import java.util.HashMap;

import java.io.IOException;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;

import fr.eriniumgroups.eriniumrank.EriniumrankModVariables;
import fr.eriniumgroups.eriniumrank.EriniumrankMod;

import com.mojang.brigadier.context.CommandContext;

import com.google.gson.Gson;

public class OnExecuteCommandProcedure {
	@Mod.EventBusSubscriber
	private static class GlobalTrigger {
		@SubscribeEvent
		public static void onCommand(CommandEvent event) {
			Entity entity = event.getParseResults().getContext().getSource().getEntity();
			if (entity != null) {
				double i = entity.getPosX();
				double j = entity.getPosY();
				double k = entity.getPosZ();
				CommandContext<CommandSource> ctx = event.getParseResults().getContext().build(event.getParseResults().getReader().getString());
				Map<String, Object> dependencies = new HashMap<>();
				dependencies.put("x", i);
				dependencies.put("y", j);
				dependencies.put("z", k);
				dependencies.put("world", entity.world);
				dependencies.put("entity", entity);
				dependencies.put("command", event.getParseResults().getReader().getString());
				dependencies.put("arguments", ctx);
				dependencies.put("event", event);
				executeProcedure(dependencies);
			}
		}
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				EriniumrankMod.LOGGER.warn("Failed to load dependency world for procedure OnExecuteCommand!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				EriniumrankMod.LOGGER.warn("Failed to load dependency x for procedure OnExecuteCommand!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				EriniumrankMod.LOGGER.warn("Failed to load dependency y for procedure OnExecuteCommand!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				EriniumrankMod.LOGGER.warn("Failed to load dependency z for procedure OnExecuteCommand!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				EriniumrankMod.LOGGER.warn("Failed to load dependency entity for procedure OnExecuteCommand!");
			return;
		}
		if (dependencies.get("command") == null) {
			if (!dependencies.containsKey("command"))
				EriniumrankMod.LOGGER.warn("Failed to load dependency command for procedure OnExecuteCommand!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		Entity entity = (Entity) dependencies.get("entity");
		String command = (String) dependencies.get("command");
		File file = new File("");
		com.google.gson.JsonObject MainJSonObject = new com.google.gson.JsonObject();
		String TempText = "";
		if (entity instanceof PlayerEntity || entity instanceof ServerPlayerEntity) {
			TempText = (new Object() {
				String string = (command);
				String[] value = string.split(" ");
				String indexvalue = value[0];

				String getString() {
					String s = indexvalue;
					return s;
				}
			}.getString());
			if (!TempText.contains("//")) {
				TempText = (TempText.replace("/", ""));
				if (!(TempText).equals("setgroup")) {
					file = (File) new File((FMLPaths.GAMEDIR.get().toString() + "/config/eriniumRanks/"),
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
								MainJSonObject = new Gson().fromJson(jsonstringbuilder.toString(), com.google.gson.JsonObject.class);
								if (MainJSonObject.get(("command." + TempText)) != null) {
									if (MainJSonObject.get(("command." + TempText)).getAsBoolean()) {
										if (world instanceof ServerWorld) {
											((World) world).getServer().getCommandManager().handleCommand(
													new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world,
															4, "", new StringTextComponent(""), ((World) world).getServer(), null)
															.withFeedbackDisabled(),
													("execute as " + entity.getDisplayName().getString() + " run " + (command).replace("/", "")));
										}
									} else {
										if (MainJSonObject.get("command.*") != null) {
											if (MainJSonObject.get("command.*").getAsBoolean()) {
												if (world instanceof ServerWorld) {
													((World) world).getServer().getCommandManager().handleCommand(
															new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO,
																	(ServerWorld) world, 4, "", new StringTextComponent(""),
																	((World) world).getServer(), null).withFeedbackDisabled(),
															("execute as " + entity.getDisplayName().getString() + " run "
																	+ (command).replace("/", "")));
												}
											} else {
												if (dependencies.get("event") != null) {
													Object _obj = dependencies.get("event");
													if (_obj instanceof Event) {
														Event _evt = (Event) _obj;
														if (_evt.isCancelable())
															_evt.setCanceled(true);
													}
												}
												if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
													((PlayerEntity) entity).sendStatusMessage(
															new StringTextComponent("\u00A7cHey ! You can't execute this command !"), (false));
												}
											}
										} else {
											if (dependencies.get("event") != null) {
												Object _obj = dependencies.get("event");
												if (_obj instanceof Event) {
													Event _evt = (Event) _obj;
													if (_evt.isCancelable())
														_evt.setCanceled(true);
												}
											}
											if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
												((PlayerEntity) entity).sendStatusMessage(
														new StringTextComponent("\u00A7cHey ! You can't execute this command !"), (false));
											}
										}
									}
								} else {
									if (MainJSonObject.get("command.*") != null) {
										if (MainJSonObject.get("command.*").getAsBoolean()) {
											if (world instanceof ServerWorld) {
												((World) world).getServer().getCommandManager().handleCommand(
														new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO,
																(ServerWorld) world, 4, "", new StringTextComponent(""), ((World) world).getServer(),
																null).withFeedbackDisabled(),
														("execute as " + entity.getDisplayName().getString() + " run " + (command).replace("/", "")));
											}
										} else {
											if (dependencies.get("event") != null) {
												Object _obj = dependencies.get("event");
												if (_obj instanceof Event) {
													Event _evt = (Event) _obj;
													if (_evt.isCancelable())
														_evt.setCanceled(true);
												}
											}
											if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
												((PlayerEntity) entity).sendStatusMessage(
														new StringTextComponent("\u00A7cHey ! You can't execute this command !"), (false));
											}
										}
									} else {
										if (dependencies.get("event") != null) {
											Object _obj = dependencies.get("event");
											if (_obj instanceof Event) {
												Event _evt = (Event) _obj;
												if (_evt.isCancelable())
													_evt.setCanceled(true);
											}
										}
										if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
											((PlayerEntity) entity).sendStatusMessage(
													new StringTextComponent("\u00A7cHey ! You can't execute this command !"), (false));
										}
									}
								}

							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				} else {
					file = (File) new File((FMLPaths.GAMEDIR.get().toString() + "/config/eriniumRanks/players/"),
							File.separator + (entity.getDisplayName().getString() + ".json"));
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
								MainJSonObject = new Gson().fromJson(jsonstringbuilder.toString(), com.google.gson.JsonObject.class);
								if (MainJSonObject.get("setgroup.command") != null) {
									if (!MainJSonObject.get("setgroup.command").getAsBoolean()) {
										if (dependencies.get("event") != null) {
											Object _obj = dependencies.get("event");
											if (_obj instanceof Event) {
												Event _evt = (Event) _obj;
												if (_evt.isCancelable())
													_evt.setCanceled(true);
											}
										}
										if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
											((PlayerEntity) entity).sendStatusMessage(
													new StringTextComponent("\u00A7cVous n'avez pas la permission d'utiliser cette commande !"),
													(false));
										}
									}
								}

							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					} else {
						if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
							((PlayerEntity) entity).sendStatusMessage(
									new StringTextComponent("\u00A7cFichier inexistant, d\u00E9connectez vous puis revenez !"), (false));
						}
					}
				}
			}
		}
	}
}
