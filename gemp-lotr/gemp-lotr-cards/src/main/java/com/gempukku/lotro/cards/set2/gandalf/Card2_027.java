package com.gempukku.lotro.cards.set2.gandalf;

import com.gempukku.lotro.cards.AbstractEvent;
import com.gempukku.lotro.cards.actions.PlayEventAction;
import com.gempukku.lotro.cards.costs.ChooseAndDiscardCardsFromPlayCost;
import com.gempukku.lotro.cards.costs.ChooseAndExertCharactersCost;
import com.gempukku.lotro.cards.effects.ChooseAndDiscardCardsFromPlayEffect;
import com.gempukku.lotro.common.*;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;

import java.util.List;

/**
 * Set: Mines of Moria
 * Side: Free
 * Culture: Gandalf
 * Twilight Cost: 0
 * Type: Event
 * Game Text: Spell. Skirmish: Discard a staff Gandalf is bearing and then exert him twice to discard a minion he
 * is skirmishing.
 */
public class Card2_027 extends AbstractEvent {
    public Card2_027() {
        super(Side.FREE_PEOPLE, Culture.GANDALF, "Staff Asunder", Phase.SKIRMISH);
        addKeyword(Keyword.SPELL);
    }

    @Override
    public boolean checkPlayRequirements(String playerId, LotroGame game, PhysicalCard self, int twilightModifier) {
        if (!super.checkPlayRequirements(playerId, game, self, twilightModifier))
            return false;

        PhysicalCard gandalf = Filters.findFirstActive(game.getGameState(), game.getModifiersQuerying(), Filters.name("Gandalf"));
        List<PhysicalCard> attachedToGandalf = game.getGameState().getAttachedCards(gandalf);
        if (gandalf != null
                && game.getModifiersQuerying().getVitality(game.getGameState(), gandalf) > 2
                && Filters.filter(attachedToGandalf, game.getGameState(), game.getModifiersQuerying(), Filters.keyword(Keyword.STAFF)).size() > 0)
            return true;
        return false;
    }

    @Override
    public PlayEventAction getPlayCardAction(String playerId, LotroGame game, PhysicalCard self, int twilightModifier) {
        PlayEventAction action = new PlayEventAction(self);
        action.appendCost(
                new ChooseAndDiscardCardsFromPlayCost(action, playerId, 1, 1, Filters.keyword(Keyword.STAFF), Filters.attachedTo(Filters.name("Gandalf"))));
        action.appendCost(
                new ChooseAndExertCharactersCost(action, playerId, 1, 1, Filters.name("Gandalf")));
        action.appendCost(
                new ChooseAndExertCharactersCost(action, playerId, 1, 1, Filters.name("Gandalf")));

        if (Filters.canSpot(game.getGameState(), game.getModifiersQuerying(), Filters.name("Gandalf"), Filters.inSkirmish()))
            action.appendEffect(
                    new ChooseAndDiscardCardsFromPlayEffect(
                            action, playerId, 1, 1, Filters.type(CardType.MINION), Filters.inSkirmish()));
        return action;
    }

    @Override
    public int getTwilightCost() {
        return 0;
    }
}
