package com.gempukku.lotro.logic.effects;

import com.gempukku.lotro.common.Zone;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.GameState;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.timing.UnrespondableEffect;

public class DiscardCardFromPlayEffect extends UnrespondableEffect {
    private PhysicalCard _card;

    public DiscardCardFromPlayEffect(PhysicalCard card) {
        _card = card;
    }

    @Override
    public boolean canPlayEffect(LotroGame game) {
        Zone zone = _card.getZone();
        return zone == Zone.FREE_CHARACTERS || zone == Zone.FREE_SUPPORT || zone == Zone.SHADOW_CHARACTERS || zone == Zone.SHADOW_SUPPORT;
    }

    @Override
    public void playEffect(LotroGame game) {
        GameState gameState = game.getGameState();
        gameState.stopAffecting(_card);
        gameState.removeCardFromZone(_card);
        gameState.addCardToZone(_card, Zone.DISCARD);
    }
}
