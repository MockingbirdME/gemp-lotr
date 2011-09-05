package com.gempukku.lotro.cards.set1.site;

import com.gempukku.lotro.cards.AbstractSite;
import com.gempukku.lotro.cards.PlayConditions;
import com.gempukku.lotro.cards.effects.ChooseAndDiscardCardFromHandEffect;
import com.gempukku.lotro.common.CardType;
import com.gempukku.lotro.common.Keyword;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.DefaultCostToEffectAction;
import com.gempukku.lotro.logic.timing.Action;
import com.gempukku.lotro.logic.timing.EffectResult;

import java.util.Collections;
import java.util.List;

/**
 * Set: The Fellowship of the Ring
 * Twilight Cost: 1
 * Type: Site
 * Site: 2
 * Game Text: Plains. Each time a minion is played, the Free Peoples player discards a card from hand.
 */
public class Card1_333 extends AbstractSite {
    public Card1_333() {
        super("Midgewater Moors", 2, 1, Direction.LEFT);
        addKeyword(Keyword.PLAINS);
    }

    @Override
    public List<? extends Action> getRequiredAfterTriggers(LotroGame game, EffectResult effectResult, PhysicalCard self) {
        if (PlayConditions.played(game.getGameState(), game.getModifiersQuerying(), effectResult, Filters.type(CardType.MINION))) {
            DefaultCostToEffectAction action = new DefaultCostToEffectAction(self, null, "Free Peoples player discards a card from hand");
            action.addEffect(
                    new ChooseAndDiscardCardFromHandEffect(action, game.getGameState().getCurrentPlayerId(), false));
            return Collections.singletonList(action);
        }
        return null;
    }
}
