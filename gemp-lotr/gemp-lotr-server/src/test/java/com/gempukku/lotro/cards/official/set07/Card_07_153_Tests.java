package com.gempukku.lotro.cards.official.set07;

import com.gempukku.lotro.cards.GenericCardTestHelper;
import com.gempukku.lotro.common.*;
import com.gempukku.lotro.game.CardNotFoundException;
import com.gempukku.lotro.game.PhysicalCardImpl;
import com.gempukku.lotro.logic.decisions.DecisionResultInvalidException;
import com.gempukku.lotro.logic.modifiers.MoveLimitModifier;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class Card_07_153_Tests
{

	protected GenericCardTestHelper GetScenario() throws CardNotFoundException, DecisionResultInvalidException {
		return new GenericCardTestHelper(
				new HashMap<>()
				{{
					put("card", "7_153");
					// put other cards in here as needed for the test case
				}},
				GenericCardTestHelper.FellowshipSites,
				GenericCardTestHelper.FOTRFrodo,
				GenericCardTestHelper.RulingRing
		);
	}

	// Uncomment both @Test markers below once this is ready to be used

	//@Test
	public void MmakiloftheHaradStatsAndKeywordsAreCorrect() throws DecisionResultInvalidException, CardNotFoundException {

		/**
		* Set: 7
		* Title: Mûmakil of the Harad
		* Unique: False
		* Side: SHADOW
		* Culture: Raider
		* Twilight Cost: 1
		* Type: event
		* Subtype: Maneuver
		* Game Text: Spot a [raider] minion to add (2) for each threat.
		*/

		//Pre-game setup
		var scn = GetScenario();

		var card = scn.GetFreepsCard("card");

		assertFalse(card.getBlueprint().isUnique());
		assertEquals(Side.SHADOW, card.getBlueprint().getSide());
		assertEquals(Culture.RAIDER, card.getBlueprint().getCulture());
		assertEquals(CardType.EVENT, card.getBlueprint().getCardType());
		//assertEquals(Race.MANEUVER, card.getBlueprint().getRace());
		//assertTrue(card.getBlueprint().getPossessionClasses().contains(PossessionClass.MANEUVER));
		assertTrue(scn.HasKeyword(card, Keyword.SUPPORT_AREA));
		assertEquals(1, card.getBlueprint().getTwilightCost());
		//assertEquals(, card.getBlueprint().getStrength());
		//assertEquals(, card.getBlueprint().getVitality());
		//assertEquals(, card.getBlueprint().getResistance());
		//assertEquals(Signet., card.getBlueprint().getSignet()); 
		//assertEquals(, card.getBlueprint().getSiteNumber());
	}

	//@Test
	public void MmakiloftheHaradTest1() throws DecisionResultInvalidException, CardNotFoundException {
		//Pre-game setup
		var scn = GetScenario();

		var card = scn.GetFreepsCard("card");
		scn.FreepsMoveCardToHand(card);

		scn.StartGame();
		scn.FreepsPlayCard(card);

		assertEquals(1, scn.GetTwilight());
	}
}
