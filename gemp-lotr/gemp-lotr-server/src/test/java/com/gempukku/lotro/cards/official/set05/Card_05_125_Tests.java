package com.gempukku.lotro.cards.official.set05;

import com.gempukku.lotro.cards.GenericCardTestHelper;
import com.gempukku.lotro.common.*;
import com.gempukku.lotro.game.CardNotFoundException;
import com.gempukku.lotro.game.PhysicalCardImpl;
import com.gempukku.lotro.logic.decisions.DecisionResultInvalidException;
import com.gempukku.lotro.logic.modifiers.MoveLimitModifier;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class Card_05_125_Tests
{

	protected GenericCardTestHelper GetScenario() throws CardNotFoundException, DecisionResultInvalidException {
		return new GenericCardTestHelper(
				new HashMap<>()
				{{
					put("card", "5_125");
					// put other cards in here as needed for the test case
				}},
				GenericCardTestHelper.FellowshipSites,
				GenericCardTestHelper.FOTRFrodo,
				GenericCardTestHelper.RulingRing
		);
	}

	// Uncomment both @Test markers below once this is ready to be used

	//@Test
	public void FoulHordeStatsAndKeywordsAreCorrect() throws DecisionResultInvalidException, CardNotFoundException {

		/**
		* Set: 5
		* Title: Foul Horde
		* Unique: True
		* Side: SHADOW
		* Culture: Isengard
		* Twilight Cost: 4
		* Type: minion
		* Subtype: Orc
		* Strength: 9
		* Vitality: 3
		* Site Number: 4
		* Game Text: <b>Warg-rider</b>.<br>While Foul Horde is not exhausted, it is <b>fierce</b>.<br>While Foul Horde has at least 3 vitality, it is <b>ambush</b> (1).<br>While Foul Horde has at least 4 vitality, it is <b>damage +1</b>.
		*/

		//Pre-game setup
		var scn = GetScenario();

		var card = scn.GetFreepsCard("card");

		assertTrue(card.getBlueprint().isUnique());
		assertEquals(Side.SHADOW, card.getBlueprint().getSide());
		assertEquals(Culture.ISENGARD, card.getBlueprint().getCulture());
		assertEquals(CardType.MINION, card.getBlueprint().getCardType());
		assertEquals(Race.ORC, card.getBlueprint().getRace());
		//assertTrue(card.getBlueprint().getPossessionClasses().contains(PossessionClass.ORC));
		assertTrue(scn.HasKeyword(card, Keyword.SUPPORT_AREA));
		assertEquals(4, card.getBlueprint().getTwilightCost());
		assertEquals(9, card.getBlueprint().getStrength());
		assertEquals(3, card.getBlueprint().getVitality());
		//assertEquals(, card.getBlueprint().getResistance());
		//assertEquals(Signet., card.getBlueprint().getSignet()); 
		assertEquals(4, card.getBlueprint().getSiteNumber());
	}

	//@Test
	public void FoulHordeTest1() throws DecisionResultInvalidException, CardNotFoundException {
		//Pre-game setup
		var scn = GetScenario();

		var card = scn.GetFreepsCard("card");
		scn.FreepsMoveCardToHand(card);

		scn.StartGame();
		scn.FreepsPlayCard(card);

		assertEquals(4, scn.GetTwilight());
	}
}
