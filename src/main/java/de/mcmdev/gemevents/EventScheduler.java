package de.mcmdev.gemevents;

import me.lucko.helper.Schedulers;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import me.lucko.helper.utils.Players;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class EventScheduler implements TerminableModule {

    private final Logger logger;

    public EventScheduler(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void setup(@NotNull TerminableConsumer consumer) {
        Schedulers.async()
                .runRepeating(() -> {
                    logger.debug("Checking for events");

                    if (Players.all().size() < 4) {
                        logger.debug("Too few players to start an event");
                        return;
                    }

                    if (new Random().nextDouble() > 0.05) {
                        logger.debug("Event chance miss");
                        return;
                    }
                    logger.debug("Event chance hit, selecting random event");
                    new EventStarter(logger).bindModuleWith(consumer);
                }, 5, TimeUnit.MINUTES, 5, TimeUnit.MINUTES)
                .bindWith(consumer);
    }
}
