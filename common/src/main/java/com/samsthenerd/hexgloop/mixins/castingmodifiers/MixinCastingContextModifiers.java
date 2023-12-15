package com.samsthenerd.hexgloop.mixins.castingmodifiers;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.samsthenerd.hexgloop.casting.ContextModificationHandlers;
import com.samsthenerd.hexgloop.casting.ContextModificationHandlers.Modification;
import net.minecraft.data.client.BlockStateVariantMap.TriFunction;
import net.minecraft.util.Pair;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.BiFunction;

@Mixin(CastingEnvironment.class)
public class MixinCastingContextModifiers {
    @ModifyReturnValue(method="isVecInRange(Lnet/minecraft/util/math/Vec3d;)Z",
    at=@At("RETURN"))
    public boolean doAmbitModification(boolean original, Vec3d pos){
        boolean current = original;
        for(Pair<TriFunction<CastingEnvironment, Vec3d, Boolean, Modification>, Integer> pair : ContextModificationHandlers.AMBIT_MODIFIERS){
            TriFunction<CastingEnvironment, Vec3d, Boolean, Modification> modifier = pair.getLeft();
            Modification mod = modifier.apply((CastingEnvironment)(Object)this, pos, current);
            if(mod != Modification.NONE){
                current = (mod == Modification.ENABLE);
            }
        }
        return current;
    }

    @ModifyReturnValue(method="isEnlightened()Z", at=@At("RETURN"), remap = false)
    public boolean doEnlightenmentModification(boolean original){
        boolean current = original;
        for(Pair<BiFunction<CastingEnvironment, Boolean, Modification>, Integer> pair : ContextModificationHandlers.ENLIGHTENMENT_MODIFIERS){
            BiFunction<CastingEnvironment, Boolean, Modification> modifier = pair.getLeft();
            Modification mod = modifier.apply((CastingEnvironment)(Object)this, current);
            if(mod != Modification.NONE){
                current = (mod == Modification.ENABLE);
            }
        }
        return current;
    }

    @ModifyReturnValue(method="getCanOvercast()Z", at=@At("RETURN"), remap = false)
    public boolean doOvercastModification(boolean original){
        boolean current = original;
        for(Pair<BiFunction<CastingEnvironment, Boolean, Modification>, Integer> pair : ContextModificationHandlers.OVERCAST_MODIFIERS){
            BiFunction<CastingEnvironment, Boolean, Modification> modifier = pair.getLeft();
            Modification mod = modifier.apply((CastingEnvironment)(Object)this, current);
            if(mod != Modification.NONE){
                current = (mod == Modification.ENABLE);
            }
        }
        return current;
    }


}
