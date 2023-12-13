package com.samsthenerd.hexgloop.mixins.truenameclassactionmixins;

import java.util.UUID;

import at.petrak.hexcasting.api.casting.circles.BlockEntityAbstractImpetus;
import at.petrak.hexcasting.common.blocks.circles.impetuses.BlockRedstoneImpetus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.samsthenerd.hexgloop.casting.truenameclassaction.ILockedIota;
import com.samsthenerd.hexgloop.casting.truenameclassaction.ISetImpetusKey;
import com.samsthenerd.hexgloop.items.ItemCastersCoin;

import at.petrak.hexcasting.api.addldata.ADIotaHolder;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.common.blocks.circles.impetuses.BlockStoredPlayerImpetus;
import at.petrak.hexcasting.xplat.IXplatAbstractions;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(BlockRedstoneImpetus.class)
public class MixinAttachImpetusKey {
    @Inject(method="onUse",
        at=@At(value="INVOKE", target="Lat/petrak/hexcasting/common/blocks/circles/impetuses/BlockEntityRedstoneImpetus;setPlayer(Lcom/mojang/authlib/GameProfile;Ljava/util/UUID;)V"),
        cancellable = true)
    public void AttachImpetusKey(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir){
        ItemStack usedStack = player.getStackInHand(hand);
        // while we're here, might as well block coins from being used too.
        if(usedStack.getItem() instanceof ItemCastersCoin){
            cir.setReturnValue(ActionResult.PASS);
            return;
        }
        ADIotaHolder datumContainer = IXplatAbstractions.INSTANCE.findDataHolder(usedStack);
        if(datumContainer == null) return;
        if(world instanceof ServerWorld sWorld){
            Iota iota = datumContainer.readIota(sWorld);
            if(iota instanceof ILockedIota lIota){
                UUID keyUuid = lIota.getUUIDKey();
                BlockEntity impetus = world.getBlockEntity(pos);
                if(((Object)impetus) instanceof ISetImpetusKey impetusMixin){
                    impetusMixin.setKeyUUID(keyUuid);
                }
            }
        }
    }
}
