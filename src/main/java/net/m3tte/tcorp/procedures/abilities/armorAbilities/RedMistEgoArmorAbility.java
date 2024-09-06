package net.m3tte.tcorp.procedures.abilities.armorAbilities;

import net.m3tte.tcorp.TCorpSounds;
import net.m3tte.tcorp.TcorpModVariables.PlayerVariables;
import net.m3tte.tcorp.gameasset.TCorpAnimations;
import net.m3tte.tcorp.particle.BlipeffectParticle;
import net.m3tte.tcorp.procedures.abilities.AbilityTier;
import net.m3tte.tcorp.procedures.abilities.AbilityUtils;
import net.m3tte.tcorp.procedures.abilities.ItemAbility;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import static net.m3tte.tcorp.procedures.abilities.AbilityUtils.applyBlipCooldown;

public class RedMistEgoArmorAbility extends ItemAbility {



    @Override
    public int getBlipCost(PlayerEntity player, PlayerVariables playerVars) {
        return player.getPersistentData().contains("onrushChain") ? 0 : 6;
    }

    @Override
    public ResourceLocation getIconLocation(PlayerEntity player, PlayerVariables vars) {
        return AbilityUtils.getAbilityIcon("onrush");
    }

    @Override
    public ResourceLocation getOverlay(PlayerEntity player, PlayerVariables playerVars) {
        return player.getPersistentData().contains("onrushChain") ? AbilityUtils.getOverlay("alpha_glow") : null;
    }

    @Override
    public AbilityTier getAbilityTier() {
        return AbilityTier.ALPHA;
    }

    @Override
    public String getName(PlayerEntity player, PlayerVariables playerVars) {
        return "Onrush";
    }

    @Override
    public float getAvailability(PlayerEntity player, PlayerVariables playerVars) {
        if (getBlipCost(player, playerVars) == 0)
            return 1;


        if (playerVars.blips < getBlipCost(player, playerVars)) {
            return (float) (playerVars.blips / getBlipCost(player, playerVars));
        }

        return 1.0f;
    }

    @Override
    public void trigger(PlayerEntity player, PlayerVariables playerVars) {

        if (playerVars.blips >= getBlipCost(player, playerVars)) {

            playerVars.blips-= getBlipCost(player, playerVars);
            World world = player.level;
            double x = player.getX();
            double y = player.getY();
            double z = player.getZ();

            if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(BlipeffectParticle.particle, x, (y + 1), z, (int) 4, 0.4, 0.6, 0.4, 0);
            }

            LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
            playerVars.globalcooldown = 20;

            entitypatch.playAnimationSynchronized(TCorpAnimations.KALI_ONRUSH, 0.1f);

            if (player.getPersistentData().contains("onrushChain")) {
                player.getPersistentData().remove("onrushChain");
                entitypatch.playSound(TCorpSounds.WOOSH, 1, 1);
            } else {
                entitypatch.playSound(TCorpSounds.DICE_ROLL, 1, 1);
            }

            applyBlipCooldown(20, playerVars);
            playerVars.syncPlayerVariables(player);


        }


    }


}
