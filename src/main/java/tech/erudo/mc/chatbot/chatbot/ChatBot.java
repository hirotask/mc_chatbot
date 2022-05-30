package tech.erudo.mc.chatbot.chatbot;

import lombok.Getter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;
import tech.erudo.mc.chatbot.chatbot.json.Chat;
import tech.erudo.mc.chatbot.chatbot.json.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public final class ChatBot extends JavaPlugin implements CommandExecutor {

    public static List<Player> inChatPlayers = new ArrayList<>();

    @Getter
    private MyConfig myConfig;

    @Override
    public void onEnable() {
        myConfig = new MyConfig(this);

        this.getServer().getPluginManager().registerEvents(new EventListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) {
           return true;
        }

        Player player = (Player) sender;

        if(command.getName().equalsIgnoreCase("chat")) {
            if(args.length == 1) {
                String text = args[0];

                try {
                    String response = new RequestManager(myConfig.getAppKey())
                            .addParameter("text=" + text)
                            .send();

                    JsonParser jsonParser = new JsonParser();
                    Chat chat = jsonParser.parse(response);
                    player.sendMessage(chat.getText());
                } catch (IOException | InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            } else {
                if(inChatPlayers.contains(player)) {
                    inChatPlayers.remove(player);
                    player.sendMessage("Chat mode is Disabled");
                } else {
                    inChatPlayers.add(player);
                    player.sendMessage("Chat mode is Enabled");
                }
            }
        }
        return false;
    }
}
