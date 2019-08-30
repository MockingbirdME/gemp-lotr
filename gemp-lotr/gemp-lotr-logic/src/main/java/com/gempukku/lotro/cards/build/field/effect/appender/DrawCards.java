package com.gempukku.lotro.cards.build.field.effect.appender;

import com.gempukku.lotro.cards.build.*;
import com.gempukku.lotro.cards.build.field.FieldUtils;
import com.gempukku.lotro.cards.build.field.effect.EffectAppender;
import com.gempukku.lotro.cards.build.field.effect.EffectAppenderProducer;
import com.gempukku.lotro.cards.build.field.effect.appender.resolver.PlayerResolver;
import com.gempukku.lotro.cards.build.field.effect.appender.resolver.ValueResolver;
import com.gempukku.lotro.game.PhysicalCard;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.CostToEffectAction;
import com.gempukku.lotro.logic.effects.DrawCardsEffect;
import com.gempukku.lotro.logic.modifiers.evaluator.Evaluator;
import com.gempukku.lotro.logic.timing.Effect;
import com.gempukku.lotro.logic.timing.EffectResult;
import org.json.simple.JSONObject;

public class DrawCards implements EffectAppenderProducer {
    @Override
    public EffectAppender createEffectAppender(JSONObject effectObject, CardGenerationEnvironment environment) throws InvalidCardDefinitionException {
        FieldUtils.validateAllowedFields(effectObject, "count", "player");

        final String player = FieldUtils.getString(effectObject.get("player"), "player", "owner");

        final PlayerSource playerSource = PlayerResolver.resolvePlayer(player, environment);
        final ValueSource count = ValueResolver.resolveEvaluator(effectObject.get("count"), 1, environment);

        return new DelayedAppender() {
            @Override
            public boolean isPlayableInFull(ActionContext actionContext, String playerId, LotroGame game, PhysicalCard self, EffectResult effectResult, Effect effect) {
                final String drawPlayer = playerSource.getPlayer(actionContext, playerId, game, self, effectResult, effect);
                final Evaluator evaluator = count.getEvaluator(null, playerId, game, self, effectResult, effect);
                final int cardCount = evaluator.evaluateExpression(game, self);
                return game.getGameState().getDeck(drawPlayer).size() >= cardCount;
            }

            @Override
            protected Effect createEffect(CostToEffectAction action, String playerId, LotroGame game, PhysicalCard self, EffectResult effectResult, Effect effect) {
                final String drawPlayer = playerSource.getPlayer(action, playerId, game, self, effectResult, effect);
                final Evaluator evaluator = count.getEvaluator(action, playerId, game, self, effectResult, effect);
                final int cardsDrawn = evaluator.evaluateExpression(game, null);
                return new DrawCardsEffect(action, drawPlayer, cardsDrawn);
            }
        };
    }

}