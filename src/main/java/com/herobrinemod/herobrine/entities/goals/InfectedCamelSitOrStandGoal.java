package com.herobrinemod.herobrine.entities.goals;

import com.herobrinemod.herobrine.entities.InfectedCamelEntity;
import net.minecraft.entity.ai.goal.Goal;

public class InfectedCamelSitOrStandGoal extends Goal {
    private final InfectedCamelEntity entity;
    private final int lastPoseTickDelta;

    public InfectedCamelSitOrStandGoal(InfectedCamelEntity entity, int lastPoseSecondsDelta) {
        this.entity = entity;
        this.lastPoseTickDelta = lastPoseSecondsDelta * 20;
    }

    @Override
    public void start() {
        if (entity.isSitting()) {
            entity.startStanding();
        } else if (!entity.isPanicking()) {
            entity.startSitting();
        }
    }

    @Override
    public boolean canStart() {
        return !entity.isTouchingWater() && entity.getTarget() == null && entity.getLastPoseTickDelta() >= (long) this.lastPoseTickDelta && !entity.isLeashed() && entity.isOnGround() && entity.canChangePose();
    }
}
