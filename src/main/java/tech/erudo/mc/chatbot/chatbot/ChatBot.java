package tech.erudo.mc.chatbot.chatbot;

import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import tech.erudo.mc.chatbot.chatbot.json.Chat;
import tech.erudo.mc.chatbot.chatbot.json.JsonParser;

import java.io.IOException;

public final class ChatBot extends JavaPlugin implements CommandExecutor {

    @Getter
    private MyConfig myConfig;

    @Override
    public void onEnable() {
        myConfig = new MyConfig(this);
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
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {

            }
        }
        return false;
    }
}
