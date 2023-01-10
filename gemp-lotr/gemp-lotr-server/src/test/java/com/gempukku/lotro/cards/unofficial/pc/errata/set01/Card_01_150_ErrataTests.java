package com.gempukku.lotro.cards.unofficial.pc.errata.set01;

import com.gempukku.lotro.cards.GenericCardTestHelper;
import com.gempukku.lotro.common.*;
import com.gempukku.lotro.game.CardNotFoundException;
import com.gempukku.lotro.logic.decisions.DecisionResultInvalidException;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class Card_01_150_ErrataTests
{

	protected GenericCardTestHelper GetScenario() throws CardNotFoundException, DecisionResultInvalidException {
		return new GenericCardTestHelper(
				new HashMap<>()
				{{
					put("aragorn", "1_89");
					put("chaff1", "1_90");
					put("chaff2", "1_91");
					put("chaff3", "1_92");

					put("rager", "71_150");
				}},
				GenericCardTestHelper.FellowshipSites,
				GenericCardTestHelper.FOTRFrodo,
				GenericCardTestHelper.FOTRRing
		);
	}

	@Test
	public void UrukRagerStatsAndKeywordsAreCorrect() throws DecisionResultInvalidException, CardNotFoundException {

		/**
		* Set: 1
		* Title: Uruk Rager
		* Unique: False
		* Side: SHADOW
		* Culture: Isengard
		* Twilight Cost: 4
		* Type: minion
		* Subtype: Uruk-hai
		* Strength: 9
		* Vitality: 2
		* Site Number: 5
		* Game Text: <b>Damage +1</b>.
		 * Each time this minion wins a skirmish, the Free Peoples player must exert a companion or discard the top 3 cards of their draw deck.
		*/

		//Pre-game setup
		var scn = GetScenario();

		var rager = scn.GetFreepsCard("rager");

		assertFalse(rager.getBlueprint().isUnique());
		assertEquals(Side.SHADOW, rager.getBlueprint().getSide());
		assertEquals(Culture.ISENGARD, rager.getBlueprint().getCulture());
		assertEquals(CardType.MINION, rager.getBlueprint().getCardType());
		assertEquals(Race.URUK_HAI, rager.getBlueprint().getRace());
		assertTrue(scn.HasKeyword(rager, Keyword.DAMAGE));
		assertEquals(1, scn.GetKeywordCount(rager, Keyword.DAMAGE));
		assertEquals(4, rager.getBlueprint().getTwilightCost());
		assertEquals(9, rager.getBlueprint().getStrength());
		assertEquals(2, rager.getBlueprint().getVitality());
		assertEquals(5, rager.getBlueprint().getSiteNumber());
	}

	@Test
	public void UrukRagerSkirmishWinOffersExert() throws DecisionResultInvalidException, CardNotFoundException {
		//Pre-game setup
		var scn = GetScenario();

		var aragorn = scn.GetFreepsCard("aragorn");
		scn.FreepsMoveCharToTable(aragorn);

		var rager = scn.GetShadowCard("rager");
		scn.ShadowMoveCharToTable(rager);

		scn.StartGame();

		scn.SkipToAssignments();
		scn.FreepsAssignToMinions(aragorn, rager);
		scn.FreepsResolveSkirmish(aragorn);
		scn.PassCurrentPhaseActions();
		scn.FreepsResolveActionOrder("Rager");

		assertTrue(scn.FreepsAnyDecisionsAvailable());
		assertEquals(0, scn.GetWoundsOn(aragorn));

		scn.FreepsChooseMultipleChoiceOption("Exert");
		scn.FreepsChooseCard(aragorn);
		assertEquals(3, scn.GetWoundsOn(aragorn)); //loss, damage+1, exertion
	}

	@Test
	public void UrukRagerSkirmishWinOffers3TopDeckDiscard() throws DecisionResultInvalidException, CardNotFoundException {
		//Pre-game setup
		var scn = GetScenario();

		var aragorn = scn.GetFreepsCard("aragorn");
		scn.FreepsMoveCharToTable(aragorn);

		var rager = scn.GetShadowCard("rager");
		scn.ShadowMoveCharToTable(rager);

		scn.StartGame();

		scn.SkipToAssignments();
		scn.FreepsAssignToMinions(aragorn, rager);
		scn.FreepsResolveSkirmish(aragorn);
		scn.PassCurrentPhaseActions();
		scn.FreepsResolveActionOrder("Rager");

		assertTrue(scn.FreepsAnyDecisionsAvailable());
		assertEquals(4, scn.GetFreepsDeckCount());
		assertEquals(0, scn.GetFreepsDiscardCount());

		scn.FreepsChooseMultipleChoiceOption("Discard");
		assertEquals(1, scn.GetFreepsDeckCount());
		assertEquals(3, scn.GetFreepsDiscardCount());
	}
}
