// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sub-license, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:

// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.

// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.

package me.HaloFloof.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import de.forcycode.playtimeplus.utils.PlaytimePlusAPI;

public class Main extends JavaPlugin {
	
	
	@Override
	public void onEnable() {
		
		
		this.getConfig();
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
		FileConfiguration config = this.getConfig();
		
		ConsoleCommandSender console = getServer().getConsoleSender();
		console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eSuccessfully &aenabled &ePTR by HaloFloof!"));
		
        (new BukkitRunnable() {
            
        	@Override
            public void run() {
            	
            	String group = config.getString("group");
        		PlaytimePlusAPI api = new PlaytimePlusAPI();
            	
            	for (Player all : Bukkit.getOnlinePlayers()) {
    				
            		String name = all.getName();
            		
    				int p = api.getHours(all);
    				int pt = config.getInt("playtime");
    				if (!all.hasPermission("floofnetwork.member")) {
    					
    					if (p >= pt) {
    						all.sendMessage(ChatColor.GREEN + "You have reached " + pt + " hours of playtime! You are now a member on the FloofNetwork!");
    						String command = "lp user " + name + " parent add " + group;
    						Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
    					} else {
    					
    						//Nothing
    						
    					}
    					
    					
    				} else {
    					
    				//Nothing
    				
    				}
    				
    			}
            	
            }
        	
        }).runTaskTimerAsynchronously(this, 0, 6000);
        
        
               
        
		}
	
	
	@Override
	public void onDisable() {
		
		ConsoleCommandSender console = getServer().getConsoleSender();
		console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eSuccessfully &4disabled &ePTR by HaloFloof!"));
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (label.equalsIgnoreCase("PTR")) {
			
			String version = this.getConfig().getString("version");
			
			if (sender instanceof Player) {
				
				Player player = (Player) sender;
				player.sendMessage(ChatColor.DARK_AQUA + "PlayTimeRankup Plugin Version " + version + " by HaloFloof");
				return true;
				
				
			} else {
				
				sender.sendMessage(ChatColor.DARK_AQUA + "PlayTimeRankup Plugin Version " + version + " by HaloFloof. Configure everything in config.yml.");
			
			}
		
		} else {	
		
		//Nothing
		
		}
		
		return false;
		
	}
	
}
