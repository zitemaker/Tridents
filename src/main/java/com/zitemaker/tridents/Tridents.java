package com.zitemaker.tridents;

import com.zitemaker.tridents.listener.RiptideListener;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.java.JavaPlugin;

public class Tridents extends JavaPlugin {

    @Override
    public void onEnable() {
        printWadiyaFlag();
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new RiptideListener(this), this);
    }

    private void printWadiyaFlag() {
        TextColor green = TextColor.color(0x1faa49);
        TextColor orange = TextColor.color(0xc19c00);
        TextColor white = TextColor.color(0xFFFFFF);
        TextColor border = TextColor.color(0x55FF55);

        sendColoredLine("");
        sendFlagLine(border, " ╔════════════════════════════════════════════════════════════╗");

        for (int i = 0; i < 5; i++) {
            sendFlagRow(border, green, "████████████████████████████████████████████████████████████");
        }

        sendFlagRow(border, orange, "████████████████████████████████████████████████████████████");

        sendFlagRow(border, orange, "████████████████████████████████████████████████████████████");
        sendMsg(Component.text(" ║", border)
                .append(Component.text("██████████████████████████", orange))
                .append(Component.text("██", white))
                .append(Component.text("████", orange))
                .append(Component.text("██", white))
                .append(Component.text("██████████████████████████", orange))
                .append(Component.text("║", border)));

        sendMsg(Component.text(" ║", border)
                .append(Component.text("████████████████████████", orange))
                .append(Component.text("██", white))
                .append(Component.text("██", orange))
                .append(Component.text("████", white))
                .append(Component.text("██", orange))
                .append(Component.text("██", white))
                .append(Component.text("████████████████████████", orange))
                .append(Component.text("║", border)));

        sendMsg(Component.text(" ║", border)
                .append(Component.text("████████████████", orange))
                .append(Component.text("██", white))
                .append(Component.text("██", orange))
                .append(Component.text("██", white))
                .append(Component.text("██", orange))
                .append(Component.text("██", white))
                .append(Component.text("██", orange))
                .append(Component.text("████", white))
                .append(Component.text("██", orange))
                .append(Component.text("██", white))
                .append(Component.text("██", orange))
                .append(Component.text("██", white))
                .append(Component.text("██", orange))
                .append(Component.text("██", white))
                .append(Component.text("████████████████", orange))
                .append(Component.text("║", border)));

        sendMsg(Component.text(" ║", border)
                .append(Component.text("██████████████", orange))
                .append(Component.text("██", white))
                .append(Component.text("████", orange))
                .append(Component.text("██", white))
                .append(Component.text("██", orange))
                .append(Component.text("████████████", white))
                .append(Component.text("██", orange))
                .append(Component.text("██", white))
                .append(Component.text("████", orange))
                .append(Component.text("██", white))
                .append(Component.text("██████████████", orange))
                .append(Component.text("║", border)));

        sendMsg(Component.text(" ║", border)
                .append(Component.text("████████████████", orange))
                .append(Component.text("████████████████████████████", white))
                .append(Component.text("████████████████", orange))
                .append(Component.text("║", border)));
        sendFlagRow(border, orange, "████████████████████████████████████████████████████████████");
        sendFlagRow(border, orange, "████████████████████████████████████████████████████████████");

        for (int i = 0; i < 5; i++) {
            sendFlagRow(border, green, "████████████████████████████████████████████████████████████");
        }

        sendFlagLine(border, " ╠════════════════════════════════════════════════════════════╣");
        sendColoredLine("");
    }

    private void sendMsg(Component component) {
        getServer().getConsoleSender().sendMessage(component);
    }

    private void sendFlagLine(TextColor color, String text) {
        getServer().getConsoleSender().sendMessage(Component.text(text, color));
    }

    private void sendFlagRow(TextColor border, TextColor fill, String content) {
        getServer().getConsoleSender().sendMessage(
                Component.text(" ║", border)
                        .append(Component.text(content, fill))
                        .append(Component.text("║", border)));
    }

    private void sendColoredLine(String text) {
        getServer().getConsoleSender().sendMessage(Component.text(text));
    }
}
