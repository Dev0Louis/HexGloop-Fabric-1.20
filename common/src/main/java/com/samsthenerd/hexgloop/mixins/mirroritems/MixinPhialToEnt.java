package com.samsthenerd.hexgloop.mixins.mirroritems;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.samsthenerd.hexgloop.casting.mirror.SyncedItemHandling;

import at.petrak.hexcasting.api.casting.casting.CastingContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

import java.util.function.Predicate;

@Mixin(targets = "at/petrak/hexcasting/common/casting/actions/spells/OpMakeBattery$Spell")
public class MixinPhialToEnt {
    @WrapOperation(method = "cast(Lat/petrak/hexcasting/api/casting/eval/CastingEnvironment;)V",
    at=@At(value="INVOKE", target="Lat/petrak/hexcasting/api/casting/eval/CastingEnvironment;replaceItem(Ljava/util/function/Predicate;Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/Hand;)Z"))
    public boolean redirectSetHandStackPhial(CastingEnvironment env, Predicate<ItemStack> itemStackPredicate, ItemStack stack, @Nullable Hand hand, Operation<Boolean> original){
        ItemStack altStack = SyncedItemHandling.getAlternateHandStack(env.getCaster(), hand, env);
        if(altStack != null){
            ItemEntity ent = SyncedItemHandling.getAlternateEntity(env.getCaster(), hand, env);
            if(ent != null){
                // probably fine ?
                ent.setStack(stack);
                return false;
            }
        }
        return original.call(env, itemStackPredicate, stack, hand);
    }
}
