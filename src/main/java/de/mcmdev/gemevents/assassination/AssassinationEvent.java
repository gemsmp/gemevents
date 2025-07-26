package de.mcmdev.gemevents.assassination;

import de.mcmdev.gemevents.GemEvents;
import de.mcmdev.gemevents.utilitymodules.Countdown;
import me.lucko.helper.Events;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import me.lucko.helper.utils.Players;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.Objects;

public class AssassinationEvent implements TerminableModule {
    @Override
    public void setup(@NotNull TerminableConsumer consumer) {
        Countdown countdown = Countdown.create()
                .title(Component.text("Assassinenevent", NamedTextColor.RED, TextDecoration.BOLD))
                .color(BossBar.Color.RED)
                .overlay(BossBar.Overlay.NOTCHED_6)
                .duration(Duration.ofMinutes(60))
                .build();

        Events.subscribe(PlayerDeathEvent.class)
                .filter(playerDeathEvent -> Objects.nonNull(playerDeathEvent.getEntity().getKiller()))
                .expireAfter(1)
                .handler(playerDeathEvent -> {
                    broadcastWinner(playerDeathEvent.getEntity().getKiller());
                    countdown.closeAndReportException();
                })
                .bindWith(countdown);
        Events.subscribe(PlayerJoinEvent.class)
                .handler(playerJoinEvent -> {
                    sendExplanation(playerJoinEvent.getPlayer());
                }).bindWith(countdown);

        countdown.bind(() -> GemEvents.EVENT_RUNNING.set(false));
        countdown.bindWith(consumer);

        Players.forEach(this::sendExplanation);
    }

    private void broadcastWinner(Player winner) {
        Players.forEach(player -> {
            player.sendRichMessage("<red><bold><winner></bold> hat das Assassinenevent gewonnen!</red>", Placeholder.component("winner", winner.displayName()));
        });
    }

    private void sendExplanation(Player player) {
        int width = 80;
        String title = "ASSASSINEN-EVENT";
        int spacers = (width - title.length()) / 3;
        player.sendMessage(Component.text(" ".repeat(width), NamedTextColor.RED, TextDecoration.STRIKETHROUGH));
        player.sendMessage(Component.text(" ".repeat(spacers) + title, NamedTextColor.RED, TextDecoration.BOLD));
        player.sendMessage(Component.text("Wer zuerst in den nächsten 60 Minuten einen anderen Spieler besiegt, erhält eine Belohnung.", NamedTextColor.RED));
        player.sendMessage(Component.text(" ".repeat(width), NamedTextColor.RED, TextDecoration.STRIKETHROUGH));
    }
}
