package com.gempukku.lotro.cards.effects;

import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.timing.UnrespondableEffect;

public class RemoveCardFromDeckEffect extends UnrespondableEffect {
    private PhysicalCard _physicalCard;

    public RemoveCardFromDeckEffect(PhysicalCard physicalCard) {
        _physicalCard = physicalCard;
    }

    @Override
    public void playEffect(LotroGame game) {
        game.getGameState().removeCardFromZone(_physicalCard);
    }
}
