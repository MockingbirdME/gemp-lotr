package com.gempukku.lotro.cards.build.field.effect.requirement;

import com.gempukku.lotro.cards.build.*;
import com.gempukku.lotro.cards.build.field.FieldUtils;
import com.gempukku.lotro.cards.build.field.effect.appender.resolver.PlayerResolver;
import com.gempukku.lotro.common.Filterable;
import com.gempukku.lotro.filters.Filters;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.GameUtils;
import org.json.simple.JSONObject;

public class HasCardInRemoved implements RequirementProducer {
    @Override
    public Requirement getPlayRequirement(JSONObject object, CardGenerationEnvironment environment) throws InvalidCardDefinitionException {
        FieldUtils.validateAllowedFields(object, "player", "count", "filter");

        final String player = FieldUtils.getString(object.get("player"), "player");
        final int count = FieldUtils.getInteger(object.get("count"), "count", 1);
        final String filter = FieldUtils.getString(object.get("filter"), "filter");

        final PlayerSource playerSource = player != null ? PlayerResolver.resolvePlayer(player, environment) : null;

        final FilterableSource filterableSource = environment.getFilterFactory().generateFilter(filter, environment);
        return (actionContext) -> {
            final Filterable filterable = filterableSource.getFilterable(actionContext);
            if (playerSource != null) {
                LotroGame game = actionContext.getGame();
                String playerId = playerSource.getPlayer(actionContext);
                return Filters.filter(game.getGameState().getRemoved(playerId), game, new Filterable[]{filterable}).size() >= count;
            } else {
                int totalCount = 0;
                for (String playerId : GameUtils.getAllPlayers(actionContext.getGame())) {
                    totalCount += Filters.filter(actionContext.getGame().getGameState().getRemoved(playerId), actionContext.getGame(), filterable).size();
                    if (totalCount >= count)
                        return true;
                }
                return false;
            }
        };
    }
}
