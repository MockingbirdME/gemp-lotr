package com.gempukku.lotro.logic.actions;

import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.logic.effects.KillEffect;

public class KillAction extends CostToEffectAction {
    public KillAction(PhysicalCard card) {
        super(null, "Kill the character");

        addEffect(new KillEffect(card));
    }
}
