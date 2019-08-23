package com.gempukku.lotro.cards.set1.site;

import com.gempukku.lotro.common.Phase;
import com.gempukku.lotro.common.Race;
import com.gempukku.lotro.common.SitesBlock;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.ActivateCardAction;
import com.gempukku.lotro.logic.cardtype.AbstractSite;
import com.gempukku.lotro.logic.effects.LookAtOpponentsHandEffect;
import com.gempukku.lotro.logic.effects.choose.ChooseAndExertCharactersEffect;
import com.gempukku.lotro.logic.effects.choose.ChooseOpponentEffect;
import com.gempukku.lotro.logic.timing.PlayConditions;

import java.util.Collections;
import java.util.List;

/**
 * Set: The Fellowship of the Ring
 * Twilight Cost: 3
 * Type: Site
 * Site: 6
 * Game Text: Sanctuary. Fellowship: Exert an Elf to look at an opponent's hand.
 */
public class Card1_351 extends AbstractSite {
    public Card1_351() {
        super("Galadriel's Glade", SitesBlock.FELLOWSHIP, 6, 3, Direction.LEFT);

    }

    @Override
    public List<? extends ActivateCardAction> getPhaseActionsInPlay(final String playerId, final LotroGame game, PhysicalCard self) {
        if (PlayConditions.canUseSiteDuringPhase(game, Phase.FELLOWSHIP, self)
                && PlayConditions.canExert(self, game, Race.ELF)) {
            final ActivateCardAction action = new ActivateCardAction(self);
            action.appendCost(
                    new ChooseAndExertCharactersEffect(action, playerId, 1, 1, Race.ELF));

            action.appendEffect(
                    new ChooseOpponentEffect(playerId) {
                        @Override
                        protected void opponentChosen(String opponentId) {
                            action.appendEffect(
                                    new LookAtOpponentsHandEffect(playerId, opponentId));
                        }
                    });
            return Collections.singletonList(action);
        }
        return null;
    }
}
