package fr.eriniumgroups.eriniumrank.procedures;

import org.checkerframework.checker.units.qual.s;

import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.CommandEvent;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

import javax.annotation.Nullable;

import java.io.IOException;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;

import fr.eriniumgroups.eriniumrank.network.EriniumrankModVariables;

import com.google.gson.Gson;

@Mod.EventBusSubscriber
public class OnExecuteCommandProcedure {
	@SubscribeEvent
	public static void onCommand(CommandEvent event) {
		Entity entity = event.getParseResults().getContext().getSource().getEntity();
		if (entity != null) {
			execute(event, entity.level, entity.getX(), entity.getY(), entity.getZ(), entity, event.getParseResults().getReader().getString());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, String command) {
		execute(null, world, x, y, z, entity, command);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity, String command) {
		if (entity == null || command == null)
			return;
		File file = new File("");
		com.google.gson.JsonObject MainJSonObject = new com.google.gson.JsonObject();
		String TempText = "";
		if (entity instanceof Player || entity instanceof ServerPlayer) {
			TempText = new Object() {
				String string = (command);
				String[] value = string.split(" ");
				String indexvalue = value[0];

				String getString() {
					String s = indexvalue;
					return s;
				}
			}.getString();
			if (!TempText.contains("//")) {
				TempText = TempText.replace("/", "");
				if (!(TempText).equals("setgroup")) {
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
								MainJSonObject = new Gson().fromJson(jsonstringbuilder.toString(), com.google.gson.JsonObject.class);
								if (MainJSonObject.get(("command." + TempText)) != null) {
									if (MainJSonObject.get(("command." + TempText)).getAsBoolean()) {
										if (world instanceof ServerLevel _level)
											_level.getServer().getCommands().performCommand(
													new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "",
															new TextComponent(""), _level.getServer(), null).withSuppressedOutput(),
													("execute as " + entity.getDisplayName().getString() + " run " + (command).replace("/", "")));
									} else {
										if (MainJSonObject.get("command.*") != null) {
											if (MainJSonObject.get("command.*").getAsBoolean()) {
												if (world instanceof ServerLevel _level)
													_level.getServer().getCommands().performCommand(
															new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "",
																	new TextComponent(""), _level.getServer(), null).withSuppressedOutput(),
															("execute as " + entity.getDisplayName().getString() + " run "
																	+ (command).replace("/", "")));
											} else {
												if (event != null && event.isCancelable()) {
													event.setCanceled(true);
												}
												if (entity instanceof Player _player && !_player.level.isClientSide())
													_player.displayClientMessage(new TextComponent("\u00A7cHey ! You can't execute this command !"),
															(false));
											}
										} else {
											if (event != null && event.isCancelable()) {
												event.setCanceled(true);
											}
											if (entity instanceof Player _player && !_player.level.isClientSide())
												_player.displayClientMessage(new TextComponent("\u00A7cHey ! You can't execute this command !"),
														(false));
										}
									}
								} else {
									if (MainJSonObject.get("command.*") != null) {
										if (MainJSonObject.get("command.*").getAsBoolean()) {
											if (world instanceof ServerLevel _level)
												_level.getServer().getCommands().performCommand(
														new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "",
																new TextComponent(""), _level.getServer(), null).withSuppressedOutput(),
														("execute as " + entity.getDisplayName().getString() + " run " + (command).replace("/", "")));
										} else {
											if (event != null && event.isCancelable()) {
												event.setCanceled(true);
											}
											if (entity instanceof Player _player && !_player.level.isClientSide())
												_player.displayClientMessage(new TextComponent("\u00A7cHey ! You can't execute this command !"),
														(false));
										}
									} else {
										if (event != null && event.isCancelable()) {
											event.setCanceled(true);
										}
										if (entity instanceof Player _player && !_player.level.isClientSide())
											_player.displayClientMessage(new TextComponent("\u00A7cHey ! You can't execute this command !"), (false));
									}
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				} else {
					file = new File((FMLPaths.GAMEDIR.get().toString() + "/config/eriniumRanks/players/"),
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
										if (event != null && event.isCancelable()) {
											event.setCanceled(true);
										}
										if (entity instanceof Player _player && !_player.level.isClientSide())
											_player.displayClientMessage(
													new TextComponent("\u00A7cVous n'avez pas la permission d'utiliser cette commande !"), (false));
									}
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					} else {
						if (entity instanceof Player _player && !_player.level.isClientSide())
							_player.displayClientMessage(new TextComponent("\u00A7cFichier inexistant, d\u00E9connectez vous puis revenez !"),
									(false));
					}
				}
			}
		}
	}
}
