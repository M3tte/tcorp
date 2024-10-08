package net.m3tte.tcorp;

import net.m3tte.tcorp.item.SuitItem;
import net.m3tte.tcorp.item.ZweiSwordItem;
import net.m3tte.tcorp.item.blackSilence.BlackSilenceArmor;
import net.m3tte.tcorp.item.blackSilence.weapons.*;
import net.m3tte.tcorp.item.magic_bullet.MagicBullet;
import net.m3tte.tcorp.item.magic_bullet.MagicBulletArmor;
import net.m3tte.tcorp.item.mimicry.MimicryArmor;
import net.m3tte.tcorp.item.mimicry.MimicryItem;
import net.m3tte.tcorp.item.redmist.RedMistEGOSuit;
import net.m3tte.tcorp.item.redmist.RedMistJacket;
import net.m3tte.tcorp.item.solemn_lament.SolemnLament;
import net.m3tte.tcorp.item.solemn_lament.SolemnLamentArmor;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SwordItem;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class TCorpItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "tcorp");
    public static RegistryObject<Item>  ZWEI_ASSOCIATION_LONGSWORD = registerItem("zweilongsword", new ZweiSwordItem(ZweiSwordItem.zweiItemTier, 3, -2.7f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static RegistryObject<Item>  MIMICRY = registerItem("mimicry", new MimicryItem(3, -2.65f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static RegistryObject<Item>  MIMICRY_CHESTPLATE = registerItem("mimicry_chestplate", MimicryArmor.getArmorForSlot(EquipmentSlotType.CHEST));
    public static RegistryObject<Item>  MIMICRY_LEGGINGS = registerItem("mimicry_leggings", MimicryArmor.getArmorForSlot(EquipmentSlotType.LEGS));
    public static RegistryObject<Item>  SUIT_LEGGINGS = registerItem("suit_leggings", SuitItem.getArmorForSlot(EquipmentSlotType.LEGS));

    public static RegistryObject<Item> RED_MIST_EGO_CHESTPLATE = registerItem("red_mist_ego_chestplate", RedMistEGOSuit.getArmorForSlot(EquipmentSlotType.CHEST));
    public static RegistryObject<Item> RED_MIST_EGO_LEGGINGS = registerItem("red_mist_ego_leggings", RedMistEGOSuit.getArmorForSlot(EquipmentSlotType.LEGS));
    public static RegistryObject<Item> RED_MIST_EGO_HELMET = registerItem("red_mist_ego_helmet", RedMistEGOSuit.getArmorForSlot(EquipmentSlotType.HEAD));

    public static RegistryObject<Item> JACKET_OF_THE_RED_MIST = registerItem("jacket_of_the_red_mist", RedMistJacket.getArmorForSlot(EquipmentSlotType.CHEST));
    public static RegistryObject<Item> CIGARETTE = registerItem("cigarette", RedMistJacket.getArmorForSlot(EquipmentSlotType.HEAD));
    public static RegistryObject<Item> MAGIC_BULLET = registerItem("magic_bullet", new MagicBullet(6, -3f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));

    public static RegistryObject<Item> MAGIC_BULLET_CLOAK = registerItem("magic_bullet_cloak", MagicBulletArmor.getArmorForSlot(EquipmentSlotType.CHEST));
    public static RegistryObject<Item> MAGIC_BULLET_PIPE = registerItem("magic_bullet_pipe", MagicBulletArmor.getArmorForSlot(EquipmentSlotType.HEAD));

    public static RegistryObject<Item> SUIT_OF_THE_BLACK_SILENCE = registerItem("suit_of_the_black_silence", BlackSilenceArmor.getArmorForSlot(EquipmentSlotType.CHEST));

    public static RegistryObject<Item> PERCEPTION_BLOCKING_MASK = registerItem("perception_blocking_mask", BlackSilenceArmor.getArmorForSlot(EquipmentSlotType.HEAD));

    public static RegistryObject<Item> SOLEMN_LAMENT_CLOAK = registerItem("solemn_lament_cloak", SolemnLamentArmor.getArmorForSlot(EquipmentSlotType.CHEST));
    public static RegistryObject<Item> SOLEMN_LAMENT_BUTTERFLY = registerItem("solemn_lament_butterfly", SolemnLamentArmor.getArmorForSlot(EquipmentSlotType.HEAD));


    public static RegistryObject<Item> ALLAS_SPEAR = registerItem("allas_spear", new AllasSpearItem(3, -3.1f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static RegistryObject<Item> ATELIER_LOGIC_PISTOLS = registerItem("atelier_logic_pistols", new AtelierlogicpistolsItem(3, -3f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static RegistryObject<Item> ATELIER_LOGIC_SHOTGUN = registerItem("atelier_logic_shotgun", new AtelierlogicshotgunItem(3, -3f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static RegistryObject<Item> CRYSTAL_ATELIER = registerItem("crystal_atelier", new CrystalatelierItem(3, -2.3f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static RegistryObject<Item> DURANDAL = registerItem("durandal", new DurandalItem(3, -2.9f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static RegistryObject<Item> DURANDAL_SHEATH = registerItem("durandalsheath", new DurandalsheathItem());
    public static RegistryObject<Item> MOOK_SHEATH = registerItem("mook_sheath", new MookSheath());
    public static RegistryObject<Item> MOOK_WORKSHOP = registerItem("mook_workshop", new MookWorkshop(3, -2.2f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static RegistryObject<Item> OLD_BOYS_WORKSHOP = registerItem("old_boys_workshop", new OldBoysWorkshop( 1, -3f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static RegistryObject<Item> RANGA_CLAW = registerItem("ranga_claw", new RangaclawItem( 3, -2.3f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static RegistryObject<Item> RANGA_CLAW_L = registerItem("ranga_claw_l", new RangaclawItem( 3, -2.3f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static RegistryObject<Item> RANGA_DAGGER = registerItem("ranga_dagger", new RangaclawItem( 3, -2.3f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static RegistryObject<Item> WHEELS_INDUSTRY = registerItem("wheels_industry", new WheelsIndustry( 1, -3.1f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static RegistryObject<Item> ZELKOVA_AXE = registerItem("zelkova_axe", new ZelkovaaxeItem( 1, -2.9f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static RegistryObject<Item> ZELKOVA_MACE = registerItem("zelkova_mace", new ZelkovamaceItem( 1, -3f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));

    public static RegistryObject<Item> SOLEMN_LAMENT_WHITE = registerItem("solemn_lament_living", new SolemnLament( 4, -2.3f, new Item.Properties().tab(ItemGroup.TAB_COMBAT), false));
    public static RegistryObject<Item> SOLEMN_LAMENT_BLACK = registerItem("solemn_lament_departed", new SolemnLament( 5, -2.3f, new Item.Properties().tab(ItemGroup.TAB_COMBAT), true));




    private static RegistryObject<Item> registerItem(String registryName, Item i) {
        return ITEMS.register(registryName, () -> i);
    }
}
