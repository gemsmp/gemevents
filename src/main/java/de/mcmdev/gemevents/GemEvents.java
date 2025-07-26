package de.mcmdev.gemevents;

import me.lucko.helper.Commands;
import me.lucko.helper.plugin.ExtendedJavaPlugin;

import java.util.concurrent.atomic.AtomicBoolean;

public class GemEvents extends ExtendedJavaPlugin {

    public static final AtomicBoolean EVENT_RUNNING = new AtomicBoolean(false);

    @Override
    protected void enable() {
        Commands.create()
                .assertOp()
                .handler(c -> new EventStarter(getSLF4JLogger()).bindModuleWith(this))
                .registerAndBind(this, "startassassinationevent");

        bindModule(new EventScheduler(getSLF4JLogger()));
    }

    @Override
    protected void disable() {

    }
}
