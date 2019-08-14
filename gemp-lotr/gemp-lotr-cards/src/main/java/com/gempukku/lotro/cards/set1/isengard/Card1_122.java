package com.gempukku.lotro.cards.set1.isengard;

import com.gempukku.lotro.common.Culture;
import com.gempukku.lotro.common.Race;
import com.gempukku.lotro.common.Side;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.PlayUtils;
import com.gempukku.lotro.logic.actions.PlayEventAction;
import com.gempukku.lotro.logic.cardtype.AbstractResponseEvent;
import com.gempukku.lotro.logic.effects.PutCardFromDiscardOnBottomOfDeckEffect;
import com.gempukku.lotro.logic.timing.EffectResult;
import com.gempukku.lotro.logic.timing.TriggerConditions;
import com.gempukku.lotro.logic.timing.results.PlayCardResult;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Set: The Fellowship of the Ring
 * Side: Shadow
 * Culture: Isengard
 * Twilight Cost: 1
 * Type: Event
 * Game Text: Response: If you play an Uruk-hai, take all copies of that card in your discard pile and place them
 * beneath your draw deck.
 */
public class Card1_122 extends AbstractResponseEvent {
    public Card1_122() {
        super(Side.SHADOW, 1, Culture.ISENGARD, "Breeding Pit");
    }

    @Override
    public List<PlayEventAction> getOptionalInHandAfterActions(String playerId, LotroGame game, EffectResult effectResult, PhysicalCard self) {
        if (TriggerConditions.played(game, effectResult, Filters.and(Race.URUK_HAI, Filters.owner(playerId)))
                && PlayUtils.checkPlayRequirements(game, self, Filters.any, 0, 0, false, false)) {
            final PlayEventAction action = new PlayEventAction(self);
            String playedCardName = ((PlayCardResult) effectResult).getPlayedCard().getBlueprint().getTitle();
            Collection<PhysicalCard> cardsInDiscardWithSameName = Filters.filter(game.getGameState().getDiscard(playerId), game, Filters.name(playedCardName));
            for (PhysicalCard physicalCard : cardsInDiscardWithSameName)
                action.appendEffect(new PutCardFromDiscardOnBottomOfDeckEffect(physicalCard));

            return Collections.singletonList(action);
        }
        return null;
    }
}
