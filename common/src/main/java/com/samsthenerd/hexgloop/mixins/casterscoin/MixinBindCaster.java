package com.samsthenerd.hexgloop.mixins.casterscoin;

import java.util.List;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.common.casting.actions.rw.OpWrite;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.samsthenerd.hexgloop.HexGloop;
import com.samsthenerd.hexgloop.items.ItemCastersCoin;

import at.petrak.hexcasting.api.addldata.ADIotaHolder;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.xplat.IXplatAbstractions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

@Mixin(OpWrite.class)
public class MixinBindCaster {
    @WrapOperation(method ="execute",
    // at = @At(value="INVOKE", target="at/petrak/hexcasting/api/addldata/ADIotaHolder.readIota (Lnet/minecraft/server/world/ServerWorld;)Lat/petrak/hexcasting/api/spell/iota/Iota;", ordinal=2))
    at = @At(value="INVOKE", target="at/petrak/hexcasting/xplat/IXplatAbstractions.findDataHolder (Lnet/minecraft/item/ItemStack;)Lat/petrak/hexcasting/api/addldata/ADIotaHolder;", ordinal=0))
    private ADIotaHolder bindCoinToCaster(IXplatAbstractions instance, ItemStack stack, Operation<ADIotaHolder> original, @NotNull List<? extends Iota> args, @NotNull CastingEnvironment env){
        ADIotaHolder holder = original.call(instance, stack);
        HexGloop.logPrint("in MixinBindCaster");
        if(stack.getItem() instanceof ItemCastersCoin readOnlyItem && !stack.isEmpty()){
            // the rest is absolutely none of my business.
            // there shouldn't be anything wrong with a blank coin having a caster in it ? just check that it's not blank before doing anything with it
            HexGloop.logPrint("stack is a coin");
            PlayerEntity caster = env.getCaster();
            if(caster != null){
                readOnlyItem.setBoundCaster(stack, caster);
                HexGloop.logPrint("bound caster");
            }
        }
        return holder;
    }
}
