package com.samsthenerd.hexgloop.items;

import java.util.Map;
import java.util.Map.Entry;

import at.petrak.hexcasting.api.casting.eval.ResolvedPattern;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.common.items.storage.ItemSpellbook;
import at.petrak.hexcasting.fabric.cc.HexCardinalComponents;
import com.mojang.datafixers.util.Pair;
import com.samsthenerd.hexgloop.misc.wnboi.LabelMaker;

import at.petrak.hexcasting.api.HexAPI;
import at.petrak.hexcasting.api.casting.iota.PatternIota;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.ComponentProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;
import ram.talia.hexal.common.casting.Patterns;

public class ItemDyeableSpellbook extends ItemSpellbook implements DyeableItem{
    public ItemDyeableSpellbook(Settings properties) {
        super(properties);
    }
    
    // silly silly
    @Override
    public int getColor(ItemStack stack){
        return DyeableItem.super.getColor(stack);
    }

    //TODO: Hello future me.
    /*@Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        super.appendStacks(group, stacks);
        if(group == HexGloopItems.HEX_GLOOP_GROUP){
            ItemStack stack = new ItemStack(this);
            stack.getOrCreateNbt().putBoolean(GREAT_HOLDER_TAG, true);
            setColor(stack, 0x837b67);
            stack.setCustomName(Text.of("Ancient Spellbook (Right Click To Activate)"));
            stacks.add(stack);
        }
    }*/

    // gets the iota color -- ok guess this doesn't work either. just hard crashes. just use the one in utils
    // this will be staying as a monument to our failures though
    // public int getIotaColor(ItemStack stack){
    //     return super.getColor(stack);
    // }

    public static final String GREAT_HOLDER_TAG = "great_holder";

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if(!(stack.getItem() instanceof LabelyItem lItem)) return super.use(world, user, hand);
        if(world instanceof ServerWorld sWorld && stack.getNbt() != null 
        && stack.getNbt().contains(GREAT_HOLDER_TAG) && stack.getNbt().getBoolean(GREAT_HOLDER_TAG)){
            // want to update it
            var allGreatSpells = ((ComponentProvider)sWorld).getComponent(HexCardinalComponents.PATTERNS).getPatterns();

            for(ResolvedPattern resolvedPattern : allGreatSpells){
                HexPattern thisPattern = resolvedPattern.getPattern();
                PatternIota thisIotaPattern = new PatternIota(thisPattern);
                writeDatum(stack, thisIotaPattern);
                stack.setCustomName(Text.of(resolvedPattern.getType().name()));
                lItem.putLabel(stack, LabelMaker.fromIota(thisIotaPattern).toNbt());
                setSealed(stack, true);
                inventoryTick(stack, world, user, hand == Hand.MAIN_HAND ? user.getInventory().selectedSlot : PlayerInventory.OFF_HAND_SLOT, false);
                rotatePageIdx(stack, true);
            }
            for(int i = 0; i < allGreatSpells.size(); i++){
                rotatePageIdx(stack, false);
            }
            stack.getNbt().putBoolean(GREAT_HOLDER_TAG, false); // just do it once i guess idk
        }
        return super.use(world, user, hand);
    }
}
