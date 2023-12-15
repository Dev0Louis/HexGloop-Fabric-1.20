package com.samsthenerd.hexgloop.blocks;

import java.util.function.Supplier;

import com.samsthenerd.hexgloop.HexGloop;
import com.samsthenerd.hexgloop.blockentities.HexGloopBEs;
import com.samsthenerd.hexgloop.items.HexGloopItems;

import at.petrak.hexcasting.api.block.circle.BlockCircleComponent;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class HexGloopBlocks {
    public static DeferredRegister<Block> blocks = DeferredRegister.create(HexGloop.MOD_ID, RegistryKeys.BLOCK);

    public static final RegistrySupplier<BlockGloopEnergizer> GLOOP_ENERGIZER_BLOCK = block("gloop_energizer", 
        () -> new BlockGloopEnergizer(AbstractBlock.Settings.create().requiresTool().strength(25.0f, 1200.0f).sounds(BlockSoundGroup.NETHERITE)));

    public static final RegistrySupplier<BlockPedestal> PEDESTAL_BLOCK = block("pedestal", 
        () -> new BlockPedestal(AbstractBlock.Settings.create().requiresTool().strength(4f, 4f).sounds(BlockSoundGroup.DEEPSLATE_TILES), false));
    public static final RegistrySupplier<BlockPedestal> MIRROR_PEDESTAL_BLOCK = block("mirror_pedestal", 
        () -> new BlockPedestal(AbstractBlock.Settings.create().requiresTool().strength(4f, 4f).sounds(BlockSoundGroup.DEEPSLATE_TILES), true));
    public static final RegistrySupplier<BlockPedestal> MIND_PEDESTAL_BLOCK = block("mind_pedestal", 
        () -> new BlockPedestal(AbstractBlock.Settings.create().requiresTool().strength(4f, 4f).sounds(BlockSoundGroup.DEEPSLATE_TILES), false, true));

    
    public static final RegistrySupplier<BlockSlateChest> SLATE_CHEST_BLOCK = block("slate_chest", 
        () -> new BlockSlateChest(AbstractBlock.Settings.create().requiresTool().strength(4f, 4f).sounds(BlockSoundGroup.DEEPSLATE_TILES),
        () -> HexGloopBEs.SLATE_CHEST_BE.get(),
        false));
    public static final RegistrySupplier<BlockSlateChest> GLOOPY_SLATE_CHEST_BLOCK = block("gloopy_slate_chest", 
        () -> new BlockSlateChest(AbstractBlock.Settings.create().requiresTool().strength(4f, 4f).sounds(BlockSoundGroup.DEEPSLATE_TILES),
        () -> HexGloopBEs.SLATE_CHEST_BE.get(),
        true));

    public static final RegistrySupplier<BlockConjuredRedstone> CONJURED_REDSTONE_BLOCK = block("conjured_redstone", 
        () -> new BlockConjuredRedstone(AbstractBlock.Settings.create().strength(1.5f, 6.0f).sounds(BlockSoundGroup.AMETHYST_BLOCK)),
        new Item.Settings()); // don't put it in item group

    public static final RegistrySupplier<BlockAccelerator> ACCELERATOR_BLOCK = block("accelerator", 
        () -> new BlockAccelerator(AbstractBlock.Settings.create().requiresTool().strength(4f, 4f).sounds(BlockSoundGroup.COPPER).luminance(state ->{
            return state.get(BlockCircleComponent.ENERGIZED) ? 7 : 0;
        })));

    public static final RegistrySupplier<BlockWaveLocus> REDSTONE_WAVE_LOCUS_BLOCK = block("wave_locus", 
        () -> new BlockWaveLocus(AbstractBlock.Settings.create().requiresTool().strength(4f, 4f).sounds(BlockSoundGroup.DEEPSLATE_TILES)
        .luminance(state ->{
            return state.get(BlockCircleComponent.ENERGIZED) ? 7 : 0;
        })));

    public static final RegistrySupplier<BlockSyncetrix> SYNCETRIX_BLOCK = block("syncetrix",
        () -> new BlockSyncetrix(AbstractBlock.Settings.create().requiresTool().strength(4f, 4f).sounds(BlockSoundGroup.DEEPSLATE_TILES)
        .luminance(state ->{
            return state.get(BlockCircleComponent.ENERGIZED) ? 10 : 0;
        })));

    public static final RegistrySupplier<BlockSentinelBed> SENTINEL_BED_BLOCK = block("sentinel_bed", 
        () -> new BlockSentinelBed(AbstractBlock.Settings.create().requiresTool().strength(4f, 6.0f).sounds(BlockSoundGroup.DEEPSLATE_TILES)));

    public static final RegistrySupplier<Block> HEXXED_GLASS_BLOCK = block("hexxed_glass", 
        () -> new BlockHexxedGlass(AbstractBlock.Settings.create().strength(0.3f, 0.3f).sounds(BlockSoundGroup.GLASS).nonOpaque()));

    public static final RegistrySupplier<Block> CHARGED_AMETHYST_BLOCK = block("charged_amethyst_block", 
        () -> new Block(AbstractBlock.Settings.create().requiresTool().strength(5.0f, 6.0f).sounds(BlockSoundGroup.AMETHYST_BLOCK)));

    public static final RegistrySupplier<Block> THINKING_CARPET_BLOCK = block("thinking_carpet", 
        () -> new BlockThinkingCarpet(AbstractBlock.Settings.create().strength(0.1f).sounds(BlockSoundGroup.WOOL)));

    public static final RegistrySupplier<BlockSlateLamp> SLATE_LAMP_BLOCK = block("slate_lamp", 
        () -> new BlockSlateLamp(AbstractBlock.Settings.create().requiresTool().strength(4f, 4f).sounds(BlockSoundGroup.AMETHYST_BLOCK)
        .luminance(state ->{
            return state.get(BlockSlateLamp.LIT) || state.get(BlockCircleComponent.ENERGIZED) ? 15 : 0;
        })));

    public static final RegistrySupplier<Block> ENLIGHTENMENT_GATE_BLOCK = block("enlightenment_gate", 
        () -> new BlockEnlightenedCollision(AbstractBlock.Settings.create().strength(0.3f, 6.0f).sounds(BlockSoundGroup.AMETHYST_BLOCK).nonOpaque(), false));
    public static final RegistrySupplier<Block> ENLIGHTENMENT_BRIDGE_BLOCK = block("enlightenment_bridge", 
        () -> new BlockEnlightenedCollision(AbstractBlock.Settings.create().strength(0.3f, 6.0f).sounds(BlockSoundGroup.AMETHYST_BLOCK).nonOpaque(), true));

    public static Block.Settings defaultSettings(){
        return Block.Settings.create().hardness((float)1.3);
    }

    public static <T extends Block> RegistrySupplier<T> block(String name, Supplier<T> block) {
        return block(name, block, HexGloopItems.defaultSettings());
	}

    public static <T extends Block> RegistrySupplier<T> block(String name, Supplier<T> block, Item.Settings settings) {
        RegistrySupplier<T> blockRegistered = blockNoItem(name, block);
        HexGloopItems.item(name, () -> new BlockItem(blockRegistered.get(), settings));
		return blockRegistered;
	}

    public static <T extends Block> RegistrySupplier<T> blockNoItem(String name, Supplier<T> block) {
		return blocks.register(new Identifier(HexGloop.MOD_ID, name), block);
	}

    public static void register(){
        blocks.register();
    }
}
