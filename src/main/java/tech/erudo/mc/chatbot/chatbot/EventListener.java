package tech.erudo.mc.chatbot.chatbot;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import tech.erudo.mc.chatbot.chatbot.json.Chat;
import tech.erudo.mc.chatbot.chatbot.json.JsonParser;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class EventListener implements Listener {

    private ChatBot chat;

    public EventListener(ChatBot chat) {
        this.chat = chat;
    }

    @EventHandler
    public void onChat(AsyncChatEvent e) throws IOException, ExecutionException, InterruptedException {
        Player player = e.getPlayer();

        if(ChatBot.inChatPlayers.contains(player)) {
            e.setCancelled(true);
            String message = PlainTextComponentSerializer.plainText().serialize(e.message());
            player.sendMessage("> " + message);

            String response = new RequestManager(chat.getMyConfig().getAppKey())
                    .addParameter("text=" + message)
                    .send();

            JsonParser jsonParser = new JsonParser();
            Chat chat = jsonParser.parse(response);
            player.sendMessage(chat.getText());
        }
    }

}
