package com.gempukku.lotro.logic.timing.rules;

import com.gempukku.lotro.common.CardType;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.AbstractActionProxy;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.game.state.actions.DefaultActionsEnvironment;
import com.gempukku.lotro.logic.actions.RequiredTriggerAction;
import com.gempukku.lotro.logic.effects.ChooseAndWoundCharactersEffect;
import com.gempukku.lotro.logic.effects.RemoveThreatsEffect;
import com.gempukku.lotro.logic.timing.Action;
import com.gempukku.lotro.logic.timing.EffectResult;
import com.gempukku.lotro.logic.timing.results.KillResult;

import java.util.Collections;
import java.util.List;

public class ThreatRule {
    private DefaultActionsEnvironment _actionsEnvironment;

    public ThreatRule(DefaultActionsEnvironment actionsEnvironment) {
        _actionsEnvironment = actionsEnvironment;
    }

    public void applyRule() {
        _actionsEnvironment.addAlwaysOnActionProxy(
                new AbstractActionProxy() {
                    @Override
                    public List<? extends Action> getRequiredAfterTriggers(LotroGame game, EffectResult effectResult) {
                        if (effectResult.getType() == EffectResult.Type.KILL) {
                            KillResult killResult = (KillResult) effectResult;
                            boolean threatsTrigger = Filters.filter(killResult.getKilledCards(), game.getGameState(), game.getModifiersQuerying(), Filters.or(CardType.COMPANION, CardType.ALLY)).size() > 0;
                            int threats = game.getGameState().getThreats();
                            if (threatsTrigger && threats > 0) {
                                RequiredTriggerAction action = new RequiredTriggerAction(null);
                                action.setText("Threat damage assignment");
                                action.appendEffect(
                                        new RemoveThreatsEffect(null, threats));
                                for (int i = 0; i < threats; i++)
                                    action.appendEffect(
                                            new ChooseAndWoundCharactersEffect(action, game.getGameState().getCurrentPlayerId(), 1, 1, CardType.COMPANION));
                                return Collections.singletonList(action);
                            }
                        }
                        return null;
                    }
                });
    }
}
