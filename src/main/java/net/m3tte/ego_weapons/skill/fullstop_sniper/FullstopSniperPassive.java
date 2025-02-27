package net.m3tte.ego_weapons.skill.fullstop_sniper;

import net.m3tte.ego_weapons.gameasset.EgoWeaponsSkills;
import net.m3tte.ego_weapons.skill.GenericSkill;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.UUID;

import static yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType.ATTACK_SPEED_MODIFY_EVENT;

public class FullstopSniperPassive extends Skill {
    public FullstopSniperPassive(Builder<? extends Skill> builder) {
        super(builder);
    }

    private static final UUID EVENT_UUID = UUID.fromString("a395b692-fd97-12ab-9c13-02b2ac131203");


    public void onInitiate(SkillContainer container) {
        SkillContainer guard = container.getExecuter().getSkillCapability().skillContainers[SkillCategories.GUARD.universalOrdinal()];


        if (!guard.isEmpty()) {
            container.getExecuter().getSkillCapability().skillContainers[GenericSkill.TC_GUARD.universalOrdinal()].setSkill(EgoWeaponsSkills.FULLSTOP_SNIPER_GUARD);
        }
    }



    public void onRemoved(SkillContainer container) {
        PlayerPatch<?> executer = container.getExecuter();
        SkillContainer bsguardskill = executer.getSkillCapability().skillContainers[GenericSkill.TC_GUARD.universalOrdinal()];

        SkillContainer guardskill = executer.getSkillCapability().skillContainers[SkillCategories.GUARD.universalOrdinal()];

        if (bsguardskill.hasSkill(EgoWeaponsSkills.FULLSTOP_SNIPER_GUARD)) {
            bsguardskill.setSkill((Skill) null);
            guardskill.getSkill().onInitiate(bsguardskill);
        }

        container.getExecuter().getEventListener().removeListener(ATTACK_SPEED_MODIFY_EVENT, EVENT_UUID);
    }


}
