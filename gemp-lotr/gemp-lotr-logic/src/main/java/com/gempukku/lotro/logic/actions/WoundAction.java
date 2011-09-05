package com.gempukku.lotro.logic.actions;

import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.logic.effects.WoundCharacterEffect;

public class WoundAction extends DefaultCostToEffectAction {
    public WoundAction(PhysicalCard card, int wounds) {
        super(null, null, "Wound character");
        for (int i = 0; i < wounds; i++)
            addEffect(new WoundCharacterEffect(card));
    }
}
