package com.gempukku.lotro.cards.set5.isengard;

import com.gempukku.lotro.cards.AbstractAttachable;
import com.gempukku.lotro.cards.PlayConditions;
import com.gempukku.lotro.cards.effects.CancelActivatedEffect;
import com.gempukku.lotro.cards.effects.choose.ChooseAndExertCharactersEffect;
import com.gempukku.lotro.common.*;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.ActivateCardAction;
import com.gempukku.lotro.logic.effects.ActivateCardEffect;
import com.gempukku.lotro.logic.timing.Action;
import com.gempukku.lotro.logic.timing.Effect;
import com.gempukku.lotro.logic.timing.EffectResult;

import java.util.Collections;
import java.util.List;

/**
 * Set: Battle of Helm's Deep
 * Side: Shadow
 * Culture: Isengard
 * Twilight Cost: 4
 * Type: Possession • Mount
 * Strength: +4
 * Vitality: +2
 * Game Text: Bearer must a warg-rider. Response: If a skirmish special abiliy is used in a skirmish involving bearer,
 * exert bearer to cancel that action.
 */
public class Card5_064 extends AbstractAttachable {
    public Card5_064() {
        super(Side.SHADOW, CardType.POSSESSION, 4, Culture.ISENGARD, PossessionClass.MOUNT, "War-warg");
    }

    @Override
    protected Filterable getValidTargetFilter(String playerId, LotroGame game, PhysicalCard self) {
        return Keyword.WARG_RIDER;
    }

    @Override
    public List<? extends Action> getOptionalInPlayBeforeActions(String playerId, LotroGame game, Effect effect, PhysicalCard self) {
        if (effect.getType() == EffectResult.Type.ACTIVATE) {
            ActivateCardEffect activateEffect = (ActivateCardEffect) effect;
            if (Filters.inSkirmish.accepts(game.getGameState(), game.getModifiersQuerying(), self.getAttachedTo())
                    && activateEffect.getActionTimeword() == Phase.SKIRMISH
                    && !activateEffect.isCancelled()
                    && PlayConditions.canExert(self, game, Filters.hasAttached(self))) {
                ActivateCardAction action = new ActivateCardAction(self);
                action.appendCost(
                        new ChooseAndExertCharactersEffect(action, playerId, 1, 1, Filters.hasAttached(self)));
                action.appendEffect(
                        new CancelActivatedEffect(self, activateEffect));
                return Collections.singletonList(action);
            }
        }
        return null;
    }
}
