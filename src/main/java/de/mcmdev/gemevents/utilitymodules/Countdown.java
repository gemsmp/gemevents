package de.mcmdev.gemevents.utilitymodules;

import me.lucko.helper.Events;
import me.lucko.helper.Schedulers;
import me.lucko.helper.scheduler.Task;
import me.lucko.helper.terminable.Terminable;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.composite.CompositeTerminable;
import me.lucko.helper.terminable.module.TerminableModule;
import me.lucko.helper.time.DurationFormatter;
import me.lucko.helper.utils.Players;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.Instant;

public class Countdown implements TerminableConsumer, Terminable {

    public static Countdown.Builder create() {
        return new Countdown.Builder();
    }

    private final Duration duration;
    private final Component displayComponent;
    private final BossBar bossBar;

    private final CompositeTerminable compositeTerminable;
    private Instant startTime;

    private Countdown(Countdown.Builder builder) {
        this.duration = builder.duration;
        this.displayComponent = builder.title;
        this.compositeTerminable = CompositeTerminable.create();

        this.bossBar = BossBar.bossBar(displayComponent, 1f, builder.color, builder.overlay);

        init();
    }

    private void init() {
        Players.forEach(bossBar::addViewer);

        Events.subscribe(PlayerJoinEvent.class)
                .handler(playerJoinEvent -> {
                    bossBar.addViewer(playerJoinEvent.getPlayer());
                }).bindWith(compositeTerminable);
        Events.subscribe(PlayerQuitEvent.class)
                .handler(playerQuitEvent -> {
                    bossBar.removeViewer(playerQuitEvent.getPlayer());
                }).bindWith(compositeTerminable);

        Schedulers.sync()
                .runRepeating(this::tick, 0, 20)
                .bindWith(compositeTerminable);

        startTime = Instant.now();
    }

    private void tick(Task task) {
        if(Duration.between(startTime, Instant.now()).compareTo(duration) >= 0) {
            closeAndReportException();
        }

        Duration elapsed = Duration.between(startTime, Instant.now());
        float progress = 1.0f - (elapsed.toMillis() / (float) duration.toMillis());
        bossBar.progress(Math.max(0.0f, Math.min(1.0f, progress)));
        bossBar.name(formatDisplayComponent(elapsed));
    }

    private Component formatDisplayComponent(Duration elapsed) {
        return Component.text()
                .append(displayComponent)
                .append(Component.text(" - ", NamedTextColor.GRAY))
                .append(Component.text(DurationFormatter.format(duration.minus(elapsed), true)))
                .build();
    }

    @Override
    public void close() throws Exception {
        compositeTerminable.closeAndReportException();
        Players.forEach(bossBar::removeViewer);
    }

    @Override
    public @NotNull <T extends AutoCloseable> T bind(@NotNull T terminable) {
        return compositeTerminable.bind(terminable);
    }

    public static class Builder {

        private Duration duration;
        private Component title;
        private BossBar.Color color;
        private BossBar.Overlay overlay;

        private Builder() {
            this.duration = Duration.ofSeconds(60);
            this.title = Component.empty();
            this.color = BossBar.Color.RED;
            this.overlay = BossBar.Overlay.NOTCHED_6;
        }

        public Builder duration(Duration duration) {
            this.duration = duration;
            return this;
        }

        public Builder title(Component title) {
            this.title = title;
            return this;
        }

        public Builder color(BossBar.Color color) {
            this.color = color;
            return this;
        }

        public Builder overlay(BossBar.Overlay overlay) {
            this.overlay = overlay;
            return this;
        }

        public Countdown build() {
            return new Countdown(this);
        }
    }

}
