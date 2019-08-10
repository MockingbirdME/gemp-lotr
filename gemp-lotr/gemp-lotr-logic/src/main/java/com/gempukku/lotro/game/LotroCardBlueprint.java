package com.gempukku.lotro.game;

import com.gempukku.lotro.common.*;
import com.gempukku.lotro.game.state.LotroGame;
import com.gempukku.lotro.logic.actions.CostToEffectAction;
import com.gempukku.lotro.logic.actions.OptionalTriggerAction;
import com.gempukku.lotro.logic.actions.RequiredTriggerAction;
import com.gempukku.lotro.logic.modifiers.AbstractExtraPlayCostModifier;
import com.gempukku.lotro.logic.modifiers.Modifier;
import com.gempukku.lotro.logic.timing.Action;
import com.gempukku.lotro.logic.timing.Effect;
import com.gempukku.lotro.logic.timing.EffectResult;

import java.util.List;
import java.util.Set;

public interface LotroCardBlueprint {
    public Side getSide();

    public CardType getCardType();

    public Culture getCulture();

    public Race getRace();

    public boolean isUnique();

    public String getName();

    public String getSubtitle();

    public Signet getSignet();

    public boolean hasKeyword(Keyword keyword);

    public int getKeywordCount(Keyword keyword);

    public Filterable getValidTargetFilter(String playerId, LotroGame game, PhysicalCard self);

    public int getTwilightCost();

    public int getTwilightCostModifier(LotroGame game, PhysicalCard self);

    public int getStrength();

    public int getVitality();

    public int getResistance();

    public int[] getAllyHomeSiteNumbers();

    public SitesBlock getAllyHomeSiteBlock();

    public boolean isAllyAtHome(int siteNumber, SitesBlock siteBlock);

    public List<? extends Modifier> getInPlayModifiers(LotroGame game, PhysicalCard self);

    public List<? extends Modifier> getStackedOnModifiers(LotroGame game, PhysicalCard self);

    public List<? extends Modifier> getInDiscardModifiers(LotroGame game, PhysicalCard self);

    public List<? extends Modifier> getControlledSiteModifiers(LotroGame game, PhysicalCard self);

    List<? extends AbstractExtraPlayCostModifier> getExtraCostToPlayModifiers(LotroGame game, PhysicalCard self);

    public boolean checkPlayRequirements(String playerId, LotroGame game, PhysicalCard self, int withTwilightRemoved, int twilightModifier, boolean ignoreRoamingPenalty, boolean ignoreCheckingDeadPile);

    public CostToEffectAction getPlayCardAction(String playerId, LotroGame game, PhysicalCard self, int twilightModifier, boolean ignoreRoamingPenalty);

    public List<? extends Action> getPhaseActions(String playerId, LotroGame game, PhysicalCard self);

    public List<? extends Action> getPhaseActionsFromStacked(String playerId, LotroGame game, PhysicalCard self);

    public List<? extends Action> getPhaseActionsFromDiscard(String playerId, LotroGame game, PhysicalCard self);

    public List<RequiredTriggerAction> getRequiredBeforeTriggers(LotroGame game, Effect effect, PhysicalCard self);

    public List<OptionalTriggerAction> getOptionalBeforeTriggers(String playerId, LotroGame game, Effect effect, PhysicalCard self);

    public List<? extends Action> getOptionalBeforeActions(String playerId, LotroGame game, Effect effect, PhysicalCard self);

    public List<RequiredTriggerAction> getRequiredAfterTriggers(LotroGame game, EffectResult effectResult, PhysicalCard self);

    public List<OptionalTriggerAction> getOptionalAfterTriggers(String playerId, LotroGame game, EffectResult effectResult, PhysicalCard self);

    public List<OptionalTriggerAction> getOptionalAfterTriggersFromHand(String playerId, LotroGame game, EffectResult effectResult, PhysicalCard self);

    public List<? extends Action> getOptionalAfterActions(String playerId, LotroGame game, EffectResult effectResult, PhysicalCard self);

    public RequiredTriggerAction getDiscardedFromPlayRequiredTrigger(LotroGame game, PhysicalCard self);

    public OptionalTriggerAction getDiscardedFromPlayOptionalTrigger(String playerId, LotroGame game, PhysicalCard self);

    public OptionalTriggerAction getKilledOptionalTrigger(String playerId, LotroGame game, PhysicalCard self);

    public SitesBlock getSiteBlock();

    public int getSiteNumber();

    public Set<PossessionClass> getPossessionClasses();

    public Direction getSiteDirection();

    public String getDisplayableInformation(PhysicalCard self);

    int getPotentialDiscount(LotroGame game, String playerId, PhysicalCard self);

    void appendPotentialDiscountEffects(LotroGame game, CostToEffectAction action, String playerId, PhysicalCard self);

    public enum Direction {
        LEFT, RIGHT
    }
}
