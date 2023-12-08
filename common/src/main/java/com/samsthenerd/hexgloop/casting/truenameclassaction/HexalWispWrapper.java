package com.samsthenerd.hexgloop.casting.truenameclassaction;

import at.petrak.hexcasting.api.casting.casting.CastingContext;
import ram.talia.hexal.api.casting.casting.IMixinCastingContext;

public class HexalWispWrapper {
    public static boolean isWisp(CastingContext ctx){
        // has error, it's probably fine and vscode just doesn't understand mixins or something
        if(((Object)ctx) instanceof IMixinCastingContext mCtx){
            return mCtx.hasWisp();
        }
        return false;
    }
}
