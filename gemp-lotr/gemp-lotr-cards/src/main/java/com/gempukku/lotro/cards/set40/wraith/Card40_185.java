package com.gempukku.lotro.cards.set40.wraith;

import com.gempukku.lotro.logic.cardtype.AbstractPermanent;
import com.gempukku.lotro.logic.timing.TriggerConditions;
import com.gempukku.lotro.logic.effects.AddBurdenEffect;
import com.gempukku.lotro.logic.effects.TransferPermanentEffect;
import com.gempukku.lotro.common.*;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.ActivateCardAction;
import com.gempukku.lotro.logic.actions.RequiredTriggerAction;
import com.gempukku.lotro.logic.effects.WoundCharactersEffect;
import com.gempukku.lotro.logic.timing.EffectResult;

import java.util.Collections;
import java.util.List;

/**
 * Title: Blade Tip
 * Set: Second Edition
 * Side: Shadow
 * Culture: Ringwraith
 * Twilight Cost: 0
 * Type: Condition - Support Area
 * Card Number: 1U185
 * Game Text: Response: If a Nazgul wins a skirmish, transfer this condition from your support area to the losing character. Limit 1 per character. Wound bearer at the start of each fellowship phase. (If bearer is the Ring-bearer, add a burden instead.)
 */
public class Card40_185 extends AbstractPermanent {
    public Card40_185() {
        super(Side.SHADOW, 0, CardType.CONDITION, Culture.WRAITH, "Blade Tip");
    }

    @Override
    public List<? extends ActivateCardAction> getOptionalInPlayAfterActions(String playerId, LotroGame game, EffectResult effectResult, final PhysicalCard self) {
        if (TriggerConditions.winsSkirmish(game, effectResult, Filters.and(Filters.owner(self.getOwner()), Race.NAZGUL))
                && self.getZone() == Zone.SUPPORT
                && game.getGameState().getSkirmish() != null && game.getGameState().getSkirmish().getFellowshipCharacter() != null
                && Filters.canSpot(game, Filters.sameCard(game.getGameState().getSkirmish().getFellowshipCharacter()), Filters.not(Filters.hasAttached(Filters.name(getTitle()))))) {
            ActivateCardAction action = new ActivateCardAction(self);
            action.appendEffect(
                    new TransferPermanentEffect(self, game.getGameState().getSkirmish().getFellowshipCharacter()));
            return Collections.singletonList(action);
        }
        return null;
    }

    @Override
    public List<RequiredTriggerAction> getRequiredAfterTriggers(LotroGame game, EffectResult effectResult, PhysicalCard self) {
        if (TriggerConditions.startOfPhase(game, effectResult, Phase.FELLOWSHIP)
                && self.getZone() == Zone.ATTACHED) {
            boolean ringBearer = game.getGameState().getRingBearer(game.getGameState().getCurrentPlayerId()) == self.getAttachedTo();
            RequiredTriggerAction action = new RequiredTriggerAction(self);
            if (ringBearer) {
                action.appendEffect(new AddBurdenEffect(self.getOwner(), self, 1));
            } else {
                action.appendEffect(new WoundCharactersEffect(self, self.getAttachedTo()));
            }
            return Collections.singletonList(action);
        }
        return null;
    }
}
