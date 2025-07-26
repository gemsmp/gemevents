package de.mcmdev.gemevents;

import de.mcmdev.gemevents.assassination.AssassinationEvent;
import de.mcmdev.gemevents.utilitymodules.Countdown;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.time.Duration;

public class EventStarter implements TerminableModule {

    private final Logger logger;

    public EventStarter(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void setup(@NotNull TerminableConsumer consumer) {
        if(!GemEvents.EVENT_RUNNING.compareAndSet(false, true)) {
            logger.info("Event is already running, cancelling new event");
            return;
        }

        Countdown countdown = Countdown.create()
                .duration(Duration.ofSeconds(60))
                .color(BossBar.Color.YELLOW)
                .overlay(BossBar.Overlay.NOTCHED_6)
                .title(Component.text("Event"))
                .build();
        countdown.bind(() -> new AssassinationEvent().bindModuleWith(consumer));
        countdown.bindWith(consumer);
    }
}
