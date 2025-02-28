package net.m3tte.ego_weapons.skill.sunshower;

import net.m3tte.ego_weapons.gameasset.EgoWeaponsSkills;
import net.m3tte.ego_weapons.skill.GenericSkill;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

public class SunshowerPassive extends Skill {
    public SunshowerPassive(Builder<? extends Skill> builder) {
        super(builder);
    }

   // private static final UUID EVENT_UUID = UUID.fromString("a395b692-fd97-11eb-9c13-02b2ac131203");


    public void onInitiate(SkillContainer container) {
        SkillContainer guard = container.getExecuter().getSkillCapability().skillContainers[SkillCategories.GUARD.universalOrdinal()];


        if (!guard.isEmpty()) {
            container.getExecuter().getSkillCapability().skillContainers[GenericSkill.TC_GUARD.universalOrdinal()].setSkill(EgoWeaponsSkills.SUNSHOWER_GUARD);
        }

        /*container.getExecuter().getEventListener().addEventListener(ATTACK_SPEED_MODIFY_EVENT, EVENT_UUID, (event) -> {
            WeaponCategory heldWeaponCategory = event.getItemCapability().getWeaponCategory();

            if (TCorpCategories.SUNSHOWER == heldWeaponCategory && event.getPlayerPatch().getOriginal().hasEffect(EthernalRestPotionEffect.get())) {
                float attackSpeed = event.getAttackSpeed();
                event.setAttackSpeed(attackSpeed * 1.4F);
            }
        });*/

    }



    public void onRemoved(SkillContainer container) {
        PlayerPatch<?> executer = container.getExecuter();
        SkillContainer bsguardskill = executer.getSkillCapability().skillContainers[GenericSkill.TC_GUARD.universalOrdinal()];

        SkillContainer guardskill = executer.getSkillCapability().skillContainers[SkillCategories.GUARD.universalOrdinal()];

        if (bsguardskill.hasSkill(EgoWeaponsSkills.SUNSHOWER_GUARD)) {
            bsguardskill.setSkill((Skill) null);
            guardskill.getSkill().onInitiate(bsguardskill);
        }

        //container.getExecuter().getEventListener().removeListener(ATTACK_SPEED_MODIFY_EVENT, EVENT_UUID);
    }


}
