/*
 *  Copyright (c) Abdullah Suleman Chaudhry <abdullahc@floofyhosting.com>
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package me.HaloFloof.geolocation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {
	
	private void getURL(String ip, Player player, Player target) {
	   String ipinfo = getHttp("http://ip-api.com/line/" + ip);
	   if (ipinfo == null || !ipinfo.startsWith("success")) {
	       player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lOh no!&7 An error occurred while trying to GeoLocate the IP, " + ChatColor.GREEN + ip + "."));
		   return;
	    }
	    String[] lines = ipinfo.split("\n");
	    String country = lines[1];
        String region = lines[4];
        String city = lines[5];
        String ISP = lines[11];
        String returnIP = lines[13];
        String targetName = target.getName();
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aResults for " + "&a&l" + targetName + "&a's IP:"));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bIP &b&l» " + ChatColor.GRAY + returnIP));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bCity &b&l» " + ChatColor.GRAY + city));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bRegion &b&l» " + ChatColor.GRAY + region));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bCountry &b&l» " + ChatColor.GRAY + country));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bISP &b&l» " + ChatColor.GRAY + ISP));
	}

	private static String getHttp(String url) {
	    try {
	        BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
	        String line;
	        StringBuilder sb = new StringBuilder();
	        while ((line = br.readLine()) != null) {
	            sb.append(line).append(System.lineSeparator());
	        }
	        br.close();
	        return sb.toString();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	public void onEnable() {
		getLogger().info("Enabling GeoLocation plugin by HaloFloof...");
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
		getLogger().info("Successfully enabled GeoLocation plugin by HaloFloof!");
		getLogger().info("You are running a beta version of the plugin! It is very likely that errors will occur!");
		getLogger().info("Known bugs:");
		getLogger().info("	Console unable to perform the command");
	}
	
	public void onDisable() {
		getLogger().info("Disabling GeoLocation plugin by HaloFloof...");
		getLogger().info("Successfully disabled GeoLocation plugin by HaloFloof!");
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (label.equalsIgnoreCase("geolocate")) {
			if (sender.hasPermission("geolocation.use")) {
				if (args.length == 0) {
					sender.sendMessage(ChatColor.RED + "Usage: /geolocate <player>");
					return true;
				} else {
						Player target = Bukkit.getPlayer(args[0]);
						String ip = target.getAddress().getAddress().getHostAddress();
				        Player player = (Player) sender;
				        getURL(ip, player, target);
				        return true;
					}
					
		        }
			        		
		} else {
				
			sender.sendMessage(ChatColor.RED + "No permission.");
			return true;
			
		}
		
		return false;
	
	}
}
