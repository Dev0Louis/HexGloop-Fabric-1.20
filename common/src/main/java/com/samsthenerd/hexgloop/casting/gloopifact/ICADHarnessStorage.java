package com.samsthenerd.hexgloop.casting.gloopifact;

import at.petrak.hexcasting.api.casting.casting.CastingContext;
import at.petrak.hexcasting.api.casting.casting.CastingHarness;

// ok so maybe this was a little bit stupid since action already exposes ravenmind and i just missed that,, but it's fine, it's good to have this maybe
public interface ICADHarnessStorage {
    public void addHarness(CastingHarness harness);

    // get the harness that has this context
    public CastingHarness getHarness(CastingContext ctx);

    public void removeHarness(CastingHarness harness);
}
