package fr.eriniumgroups.eriniumrank.procedures;

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
import net.minecraft.network.chat.Component;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

import javax.annotation.Nullable;

import java.io.IOException;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;

import fr.eriniumgroups.eriniumrank.EriniumrankMod;

import com.google.gson.JsonObject;
import com.google.gson.Gson;

@Mod.EventBusSubscriber
public class OnExecuteCommandProcedure {
	@SubscribeEvent
	public static void onCommand(CommandEvent event) {
		Entity entity = event.getParseResults().getContext().getSource().getEntity();
		if (entity != null) {
			execute(event, entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity, event.getParseResults().getReader().getString());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, String command) {
		execute(null, world, x, y, z, entity, command);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity, String command) {
		if (entity == null || command == null)
			return;
		File file = new File("");
		String TempText = "";
		com.google.gson.JsonObject MainJSonObject = new com.google.gson.JsonObject();
		com.google.gson.JsonObject RankPerm = new com.google.gson.JsonObject();
		com.google.gson.JsonObject PlayerPerm = new com.google.gson.JsonObject();
		if (entity instanceof Player || entity instanceof ServerPlayer) {
			TempText = (command).split(" ")[0];
			if (!TempText.contains("//")) {
				TempText = TempText.replace("/", "");
				if (!(TempText).equals("setgroup")) {
					RankPerm = new Object() {
						public JsonObject parse(String rawJson) {
							try {
								return new Gson().fromJson(rawJson, com.google.gson.JsonObject.class);
							} catch (Exception e) {
								EriniumrankMod.LOGGER.error(e);
								return new Gson().fromJson("{}", com.google.gson.JsonObject.class);
							}
						}
					}.parse(ReturnRankPermProcedure.execute(entity));
					PlayerPerm = new Object() {
						public JsonObject parse(String rawJson) {
							try {
								return new Gson().fromJson(rawJson, com.google.gson.JsonObject.class);
							} catch (Exception e) {
								EriniumrankMod.LOGGER.error(e);
								return new Gson().fromJson("{}", com.google.gson.JsonObject.class);
							}
						}
					}.parse(ReturnPlayerPermProcedure.execute(entity));
					if (RankPerm.has(("command." + TempText))) {
						if (RankPerm.get(("command." + TempText)).getAsBoolean()) {
							if (event != null && event.isCancelable()) {
								event.setCanceled(true);
							}
							if (world instanceof ServerLevel _level)
								_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
										("execute as " + entity.getDisplayName().getString() + " run " + command.replace("/", "")));
						} else {
							if (RankPerm.has("command.*")) {
								if (RankPerm.get("command.*").getAsBoolean()) {
									if (event != null && event.isCancelable()) {
										event.setCanceled(true);
									}
									if (world instanceof ServerLevel _level)
										_level.getServer().getCommands().performPrefixedCommand(
												new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
												("execute as " + entity.getDisplayName().getString() + " run " + command.replace("/", "")));
								} else {
									if (event != null && event.isCancelable()) {
										event.setCanceled(true);
									}
									if (entity instanceof Player _player && !_player.level().isClientSide())
										_player.displayClientMessage(Component.literal("\u00A7cHey ! You can't execute this command !"), false);
								}
							} else {
								if (event != null && event.isCancelable()) {
									event.setCanceled(true);
								}
								if (entity instanceof Player _player && !_player.level().isClientSide())
									_player.displayClientMessage(Component.literal("\u00A7cHey ! You can't execute this command !"), false);
							}
						}
					} else if (PlayerPerm.has(("command." + TempText))) {
						if (PlayerPerm.get(("command." + TempText)).getAsBoolean()) {
							if (event != null && event.isCancelable()) {
								event.setCanceled(true);
							}
							if (world instanceof ServerLevel _level)
								_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
										("execute as " + entity.getDisplayName().getString() + " run " + command.replace("/", "")));
						} else {
							if (PlayerPerm.has("command.*")) {
								if (PlayerPerm.get("command.*").getAsBoolean()) {
									if (event != null && event.isCancelable()) {
										event.setCanceled(true);
									}
									if (world instanceof ServerLevel _level)
										_level.getServer().getCommands().performPrefixedCommand(
												new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
												("execute as " + entity.getDisplayName().getString() + " run " + command.replace("/", "")));
								} else {
									if (event != null && event.isCancelable()) {
										event.setCanceled(true);
									}
									if (entity instanceof Player _player && !_player.level().isClientSide())
										_player.displayClientMessage(Component.literal("\u00A7cHey ! You can't execute this command !"), false);
								}
							} else {
								if (event != null && event.isCancelable()) {
									event.setCanceled(true);
								}
								if (entity instanceof Player _player && !_player.level().isClientSide())
									_player.displayClientMessage(Component.literal("\u00A7cHey ! You can't execute this command !"), false);
							}
						}
					}
				} else {
					file = new File((FMLPaths.GAMEDIR.get().toString() + "/config/eriniumRanks/players/"), File.separator + (entity.getDisplayName().getString() + ".json"));
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
								if (MainJSonObject.has("setgroup.command")) {
									if (!MainJSonObject.get("setgroup.command").getAsBoolean()) {
										if (event != null && event.isCancelable()) {
											event.setCanceled(true);
										}
										if (entity instanceof Player _player && !_player.level().isClientSide())
											_player.displayClientMessage(Component.literal("\u00A7cVous n'avez pas la permission d'utiliser cette commande !"), false);
									}
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					} else {
						if (entity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal("\u00A7cFichier inexistant, d\u00E9connectez vous puis revenez !"), false);
					}
				}
			}
		}
	}
}
