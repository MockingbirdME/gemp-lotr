package com.gempukku.lotro.cards.set1.moria;

import com.gempukku.lotro.common.*;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.ActivateCardAction;
import com.gempukku.lotro.logic.cardtype.AbstractPermanent;
import com.gempukku.lotro.logic.effects.DiscardCardFromDeckEffect;
import com.gempukku.lotro.logic.effects.PutCardFromDeckIntoHandOrDiscardEffect;
import com.gempukku.lotro.logic.effects.RemoveTwilightEffect;
import com.gempukku.lotro.logic.effects.RevealCardEffect;
import com.gempukku.lotro.logic.timing.PlayConditions;
import com.gempukku.lotro.logic.timing.UnrespondableEffect;

import java.util.Collections;
import java.util.List;

/**
 * Set: The Fellowship of the Ring
 * Side: Shadow
 * Culture: Moria
 * Twilight Cost: 2
 * Type: Condition
 * Game Text: Plays to your support area. Shadow: Remove (3) to reveal the bottom card of your draw deck. If it is a
 * [MORIA] Orc, take it into hand. Otherwise, discard it.
 */
public class Card1_200 extends AbstractPermanent {
    public Card1_200() {
        super(Side.SHADOW, 2, CardType.CONDITION, Culture.MORIA, "The Underdeeps of Moria");
    }

    @Override
    public List<? extends ActivateCardAction> getPhaseActionsInPlay(final String playerId, final LotroGame game, final PhysicalCard self) {
        if (PlayConditions.canUseShadowCardDuringPhase(game, Phase.SHADOW, self, 3)) {
            final ActivateCardAction action = new ActivateCardAction(self);
            action.appendCost(new RemoveTwilightEffect(3));
            action.appendEffect(
                    new UnrespondableEffect() {
                        @Override
                        public void doPlayEffect(LotroGame game) {
                            List<? extends PhysicalCard> deck = game.getGameState().getDeck(playerId);
                            if (deck.size() > 0) {
                                PhysicalCard bottomCard = deck.get(deck.size() - 1);
                                action.appendEffect(
                                        new RevealCardEffect(self, bottomCard));
                                if (bottomCard.getBlueprint().getCulture() == Culture.MORIA
                                        && bottomCard.getBlueprint().getRace() == Race.ORC) {
                                    action.appendEffect(
                                            new PutCardFromDeckIntoHandOrDiscardEffect(bottomCard));
                                } else {
                                    action.appendEffect(
                                            new DiscardCardFromDeckEffect(bottomCard));
                                }
                            }
                        }
                    });
            return Collections.singletonList(action);
        }
        return null;
    }
}
