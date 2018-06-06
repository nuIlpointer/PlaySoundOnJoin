package io.github.nuIlpointer;

import java.io.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PlaySoundOnJoin_Main extends JavaPlugin implements Listener {
	
	@Override
	public void onEnable() {
		getLogger().info("---------------------------------------");
		getLogger().info("PlaySoundOnJoin is enabled.");
		getLogger().info("---------------------------------------");
		//registerListenter
		Bukkit.getPluginManager().registerEvents(this, this);
		//config.yml読み込み
		FileConfiguration config = getConfig();
		//存在しない場合はplugin.ymlを作成
		saveDefaultConfig();
	}
	
	@Override
	public void onDisable() {
		getLogger().info("----------------------------------------");
		getLogger().info("PlaySoundOnJoin is disabled.");
		getLogger().info("----------------------------------------");
	}
	
	@EventHandler 
	public void onPlayerJoin(PlayerJoinEvent event) {
	    Player player = event.getPlayer();
		getLogger().info(player + " joind");
	    for(Player players : Bukkit.getOnlinePlayers()){
	    	FileConfiguration config = getConfig();
	    	String sound = config.getString("Sound", "Sound.ENTITY_PLAYER_LEVELUP");
	    	float vol = Float.parseFloat(config.getString("Volume"));
	    	float pitch = Float.parseFloat(config.getString("Pitch"));
	    	getLogger().info("Play sound " +sound+ ", with volume " +vol+ ", pitch " +pitch );
	    	players.playSound(players.getLocation(), sound, vol, pitch);
	    }
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		//configを読み込み
		FileConfiguration config = getConfig();
		
		//"psoj"コマンドが入力された場合
		if(label.equalsIgnoreCase("psoj")) {
			if(args.length == 0) {
				//それのみだった場合、ヘルプ画面を表示
				sender.sendMessage("§6----------§l§f[PlaySoundOnJoin]§r§6----------");
				sender.sendMessage("§6/psoj sound [soundId]   §f- change sound on join");
				sender.sendMessage("§6/psoj volume [volume]   §f- change sound volume ");
				sender.sendMessage("§6/psoj pitch [pitch]   §f- change sound pitch");
				sender.sendMessage("§6/psoj reload   §f- reload config.yml");
				sender.sendMessage("§6/psoj help   §f- see this page");
				sender.sendMessage("§6You can specify \"default\" as the last argument :)");
				sender.sendMessage("§6Development by §cnuilpointer");
				sender.sendMessage("§6-------------------------------------");
			}
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("sound")) {
					sender.sendMessage("§6----------§l§f[PlaySoundOnJoin]§r§6----------");
					sender.sendMessage("§6/psoj sound [soundId]   §f- change sound on join");
					sender.sendMessage("§6You can specify \"default\" as the last argument :)");
					sender.sendMessage("§6-------------------------------------");
				}
				if(args[0].equalsIgnoreCase("volume")) {
					sender.sendMessage("§6----------§l§f[PlaySoundOnJoin]§r§6----------");
					sender.sendMessage("§6/psoj volume [volume]   §f- change sound volume");
					sender.sendMessage("§6You can specify \"default\" as the last argument :)");
					sender.sendMessage("§6-------------------------------------");
				}
				if(args[0].equalsIgnoreCase("pitch")) {
					
					//pitchのみのヘルプを表示
					sender.sendMessage("§6----------§l§f[PlaySoundOnJoin]§r§6----------");
					sender.sendMessage("§6/psoj pitch [pitch]   §f- change sound pitch");
					sender.sendMessage("§6You can specify \"default\" as the last argument :)");
					sender.sendMessage("§6-------------------------------------");
				}
				if(args[0].equalsIgnoreCase("reload")) {
					String err = "null";
					//リロードを実行
					try {
						reloadConfig();
					} catch(Throwable ex) {
						sender.sendMessage("§4Runtime Error! error:" + ex );
						sender.sendMessage("§4Reload Failed!");
						sender.sendMessage("§4エラーが発生しました。プログラムは続行されますが正常に動作しない可能性が高いです。");
					}
					sender.sendMessage("§2Reload Complete!");
				}
				if(args[0].equalsIgnoreCase("help")) {
					//ヘルプページを表示
					sender.sendMessage("§6----------§l§f[PlaySoundOnJoin]§r§6----------");
					sender.sendMessage("§6/psoj sound [soundId]   §f- change sound on join");
					sender.sendMessage("§6/psoj volume [volume]   §f- change sound volume ");
					sender.sendMessage("§6/psoj pitch [pitch]   §f- change sound pitch");
					sender.sendMessage("§6/psoj reload   §f- reload config.yml");
					sender.sendMessage("§6/psoj help   §f- see this page");
					sender.sendMessage("§6You can specify \"default\" as the last argument :)");
					sender.sendMessage("§6Development by §cnuilpointer");
					sender.sendMessage("§6-------------------------------------");
				}
			}
			if (args.length == 2) {
	            if (args[0].equalsIgnoreCase("sound")) {
	                if(args[1].equalsIgnoreCase("default")){
	                	config.set("Sound", "entity.player.levelup");
	                	//saveでエラーが発生したらエラーを出す
	                    try {
							saveConfig();
						} catch(Throwable ex) {
							sender.sendMessage("§4Runtime Error! error:" + ex );
						    sender.sendMessage("§4エラーが発生しました。プログラムは続行されますが正常に動作しない可能性が高いです。");
						}
	                    //完了処理
	                	sender.sendMessage("サウンドは\"" + config.getString("Sound") + "\"に変更されました!");
	                } else {
	                	//saveでエラーが発生したらエラーを出す
	                	config.set("Sound", args[1]);
	                	try {
							saveConfig();
						} catch(Throwable ex) {
							sender.sendMessage("§4Runtime Error! error:" + ex );
						    sender.sendMessage("§4エラーが発生しました。プログラムは続行されますが正常に動作しない可能性が高いです。");
						}
	                	sender.sendMessage("サウンドは\"" + config.getString("Sound") + "\"に変更されました!");
	                }
	            }
	            if (args[0].equalsIgnoreCase("volume")) {
	                if(args[1].equalsIgnoreCase("default")){
	                	config.set("Volume", "1");
	                	//saveでエラーが発生したらエラーを出す
	                	try {
							saveConfig();
						} catch(Throwable ex) {
							sender.sendMessage("§4Runtime Error! error:" + ex );
						    sender.sendMessage("§4エラーが発生しました。プログラムは続行されますが正常に動作しない可能性が高いです。");
						}
	                	sender.sendMessage("ボリュームは\"" + config.getString("Volume") + "\"に変更されました!");             
	                } else {
	                	//saveでエラーが発生したらエラーを出す
	                	config.set("Volume", args[1]);
	                	try {
							saveConfig();
						} catch(Throwable ex) {
							sender.sendMessage("§4Runtime Error! error:" + ex );
						    sender.sendMessage("§4エラーが発生しました。プログラムは続行されますが正常に動作しない可能性が高いです。");
						}
	                	sender.sendMessage("ボリュームは\"" + config.getString("Volume") + "\"に変更されました!");
	                }
	            }
	            if (args[0].equalsIgnoreCase("pitch")) {
	                if(args[1].equalsIgnoreCase("default")){
	                	config.set("Pitch", "1");
	                	try {
							saveConfig();
						} catch(Throwable ex) {
							sender.sendMessage("§4Runtime Error! error:" + ex );
						    sender.sendMessage("§4エラーが発生しました。プログラムは続行されますが正常に動作しない可能性が高いです。");
						}
	                	sender.sendMessage("ピッチは\"" + config.getString("Pitch") + "\"に変更されました!");
	                } else {
	                	//saveでエラーが発生したらエラーを出す
	                	config.set("Pitch", args[1]);
	                	try {
							saveConfig();
						} catch(Throwable ex) {
							sender.sendMessage("§4Runtime Error! error:" + ex );
						    sender.sendMessage("§4エラーが発生しました。プログラムは続行されますが正常に動作しない可能性が高いです。");
						}
	                	sender.sendMessage("ピッチは\"" + config.getString("Pitch") + "\"に変更されました!");
	                }
	            }
			}
		}
	return true;
	}
	
		
}

