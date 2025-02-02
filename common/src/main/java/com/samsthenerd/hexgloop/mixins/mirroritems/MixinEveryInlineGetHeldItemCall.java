package com.samsthenerd.hexgloop.mixins.mirroritems;

import java.util.List;

import at.petrak.hexcasting.common.casting.actions.spells.OpColorize;
import at.petrak.hexcasting.common.casting.actions.spells.OpMakeBattery;
import at.petrak.hexcasting.common.casting.actions.spells.OpMakePackagedSpell;
import at.petrak.hexcasting.common.casting.actions.spells.OpRecharge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.samsthenerd.hexgloop.casting.mirror.SyncedItemHandling;

import at.petrak.hexcasting.api.casting.iota.Iota;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;

@Pseudo
@Mixin(value={
    OpMakeBattery.class,
    OpMakePackagedSpell.class,
    OpRecharge.class,
    OpColorize.class
})
public class MixinEveryInlineGetHeldItemCall {
    @WrapOperation(method = {
        "execute",
        "executeWithUserdata"
    },
    at = @At(value = "INVOKE", target="net/minecraft/server/network/ServerPlayerEntity.getStackInHand (Lnet/minecraft/util/Hand;)Lnet/minecraft/item/ItemStack;"))
    public ItemStack getAlternateHandStack(ServerPlayerEntity player, Hand hand, Operation<ItemStack> original, List<Iota> args, CastingContext context){
        ItemStack altStack = SyncedItemHandling.getAlternateHandStack(player, hand, context);
        return altStack == null ? original.call(player, hand) : altStack;
    }
}
