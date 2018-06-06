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
		//config.yml�ǂݍ���
		FileConfiguration config = getConfig();
		//���݂��Ȃ��ꍇ��plugin.yml���쐬
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
		//config��ǂݍ���
		FileConfiguration config = getConfig();
		
		//"psoj"�R�}���h�����͂��ꂽ�ꍇ
		if(label.equalsIgnoreCase("psoj")) {
			if(args.length == 0) {
				//����݂̂������ꍇ�A�w���v��ʂ�\��
				sender.sendMessage("��6----------��l��f[PlaySoundOnJoin]��r��6----------");
				sender.sendMessage("��6/psoj sound [soundId]   ��f- change sound on join");
				sender.sendMessage("��6/psoj volume [volume]   ��f- change sound volume ");
				sender.sendMessage("��6/psoj pitch [pitch]   ��f- change sound pitch");
				sender.sendMessage("��6/psoj reload   ��f- reload config.yml");
				sender.sendMessage("��6/psoj help   ��f- see this page");
				sender.sendMessage("��6You can specify \"default\" as the last argument :)");
				sender.sendMessage("��6Development by ��cnuilpointer");
				sender.sendMessage("��6-------------------------------------");
			}
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("sound")) {
					sender.sendMessage("��6----------��l��f[PlaySoundOnJoin]��r��6----------");
					sender.sendMessage("��6/psoj sound [soundId]   ��f- change sound on join");
					sender.sendMessage("��6You can specify \"default\" as the last argument :)");
					sender.sendMessage("��6-------------------------------------");
				}
				if(args[0].equalsIgnoreCase("volume")) {
					sender.sendMessage("��6----------��l��f[PlaySoundOnJoin]��r��6----------");
					sender.sendMessage("��6/psoj volume [volume]   ��f- change sound volume");
					sender.sendMessage("��6You can specify \"default\" as the last argument :)");
					sender.sendMessage("��6-------------------------------------");
				}
				if(args[0].equalsIgnoreCase("pitch")) {
					
					//pitch�݂̂̃w���v��\��
					sender.sendMessage("��6----------��l��f[PlaySoundOnJoin]��r��6----------");
					sender.sendMessage("��6/psoj pitch [pitch]   ��f- change sound pitch");
					sender.sendMessage("��6You can specify \"default\" as the last argument :)");
					sender.sendMessage("��6-------------------------------------");
				}
				if(args[0].equalsIgnoreCase("reload")) {
					String err = "null";
					//�����[�h�����s
					try {
						reloadConfig();
					} catch(Throwable ex) {
						sender.sendMessage("��4Runtime Error! error:" + ex );
						sender.sendMessage("��4Reload Failed!");
						sender.sendMessage("��4�G���[���������܂����B�v���O�����͑��s����܂�������ɓ��삵�Ȃ��\���������ł��B");
					}
					sender.sendMessage("��2Reload Complete!");
				}
				if(args[0].equalsIgnoreCase("help")) {
					//�w���v�y�[�W��\��
					sender.sendMessage("��6----------��l��f[PlaySoundOnJoin]��r��6----------");
					sender.sendMessage("��6/psoj sound [soundId]   ��f- change sound on join");
					sender.sendMessage("��6/psoj volume [volume]   ��f- change sound volume ");
					sender.sendMessage("��6/psoj pitch [pitch]   ��f- change sound pitch");
					sender.sendMessage("��6/psoj reload   ��f- reload config.yml");
					sender.sendMessage("��6/psoj help   ��f- see this page");
					sender.sendMessage("��6You can specify \"default\" as the last argument :)");
					sender.sendMessage("��6Development by ��cnuilpointer");
					sender.sendMessage("��6-------------------------------------");
				}
			}
			if (args.length == 2) {
	            if (args[0].equalsIgnoreCase("sound")) {
	                if(args[1].equalsIgnoreCase("default")){
	                	config.set("Sound", "entity.player.levelup");
	                	//save�ŃG���[������������G���[���o��
	                    try {
							saveConfig();
						} catch(Throwable ex) {
							sender.sendMessage("��4Runtime Error! error:" + ex );
						    sender.sendMessage("��4�G���[���������܂����B�v���O�����͑��s����܂�������ɓ��삵�Ȃ��\���������ł��B");
						}
	                    //��������
	                	sender.sendMessage("�T�E���h��\"" + config.getString("Sound") + "\"�ɕύX����܂���!");
	                } else {
	                	//save�ŃG���[������������G���[���o��
	                	config.set("Sound", args[1]);
	                	try {
							saveConfig();
						} catch(Throwable ex) {
							sender.sendMessage("��4Runtime Error! error:" + ex );
						    sender.sendMessage("��4�G���[���������܂����B�v���O�����͑��s����܂�������ɓ��삵�Ȃ��\���������ł��B");
						}
	                	sender.sendMessage("�T�E���h��\"" + config.getString("Sound") + "\"�ɕύX����܂���!");
	                }
	            }
	            if (args[0].equalsIgnoreCase("volume")) {
	                if(args[1].equalsIgnoreCase("default")){
	                	config.set("Volume", "1");
	                	//save�ŃG���[������������G���[���o��
	                	try {
							saveConfig();
						} catch(Throwable ex) {
							sender.sendMessage("��4Runtime Error! error:" + ex );
						    sender.sendMessage("��4�G���[���������܂����B�v���O�����͑��s����܂�������ɓ��삵�Ȃ��\���������ł��B");
						}
	                	sender.sendMessage("�{�����[����\"" + config.getString("Volume") + "\"�ɕύX����܂���!");             
	                } else {
	                	//save�ŃG���[������������G���[���o��
	                	config.set("Volume", args[1]);
	                	try {
							saveConfig();
						} catch(Throwable ex) {
							sender.sendMessage("��4Runtime Error! error:" + ex );
						    sender.sendMessage("��4�G���[���������܂����B�v���O�����͑��s����܂�������ɓ��삵�Ȃ��\���������ł��B");
						}
	                	sender.sendMessage("�{�����[����\"" + config.getString("Volume") + "\"�ɕύX����܂���!");
	                }
	            }
	            if (args[0].equalsIgnoreCase("pitch")) {
	                if(args[1].equalsIgnoreCase("default")){
	                	config.set("Pitch", "1");
	                	try {
							saveConfig();
						} catch(Throwable ex) {
							sender.sendMessage("��4Runtime Error! error:" + ex );
						    sender.sendMessage("��4�G���[���������܂����B�v���O�����͑��s����܂�������ɓ��삵�Ȃ��\���������ł��B");
						}
	                	sender.sendMessage("�s�b�`��\"" + config.getString("Pitch") + "\"�ɕύX����܂���!");
	                } else {
	                	//save�ŃG���[������������G���[���o��
	                	config.set("Pitch", args[1]);
	                	try {
							saveConfig();
						} catch(Throwable ex) {
							sender.sendMessage("��4Runtime Error! error:" + ex );
						    sender.sendMessage("��4�G���[���������܂����B�v���O�����͑��s����܂�������ɓ��삵�Ȃ��\���������ł��B");
						}
	                	sender.sendMessage("�s�b�`��\"" + config.getString("Pitch") + "\"�ɕύX����܂���!");
	                }
	            }
			}
		}
	return true;
	}
	
		
}

