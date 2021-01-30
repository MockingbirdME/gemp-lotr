package com.gempukku.lotro.cards.unofficial.pc.errata;

import com.gempukku.lotro.cards.GenericCardTest;
import com.gempukku.lotro.common.Phase;
import com.gempukku.lotro.game.CardNotFoundException;
import com.gempukku.lotro.game.PhysicalCardImpl;
import com.gempukku.lotro.logic.decisions.DecisionResultInvalidException;
import static org.junit.Assert.*;
import org.junit.Test;

import java.util.HashMap;

public class Sam_SoHErrataTest
{
    protected GenericCardTest GetSimpleScenario() throws CardNotFoundException, DecisionResultInvalidException {
        return new GenericCardTest(
                new HashMap<String, String>()
                {{
                    put("sam", "21_10311");

                    put("orc", "1_272");
                }}
        );
    }

    @Test
    public void FellowshipActionExertsTwiceToRemoveABurden() throws DecisionResultInvalidException, CardNotFoundException {
        //Pre-game setup
        GenericCardTest scn = GetSimpleScenario();

        PhysicalCardImpl frodo = scn.GetRingBearer();
        PhysicalCardImpl sam = scn.GetFreepsCard("sam");
        scn.FreepsMoveCharToTable(sam);

        scn.StartGame();

        assertEquals(Phase.FELLOWSHIP, scn.GetCurrentPhase());
        assertTrue(scn.FreepsActionAvailable("Use Sam"));

        assertEquals(0, scn.GetWoundsOn(sam));
        assertEquals(1, scn.GetBurdens());

        scn.FreepsUseAction("Use Sam");

        assertEquals(2, scn.GetWoundsOn(sam));
        assertEquals(0, scn.GetBurdens());
    }


    @Test
    public void RBDeathMakesSamTheRB() throws DecisionResultInvalidException, CardNotFoundException {
        //Pre-game setup
        GenericCardTest scn = GetSimpleScenario();

        PhysicalCardImpl frodo = scn.GetRingBearer();
        PhysicalCardImpl sam = scn.GetFreepsCard("sam");
        PhysicalCardImpl orc = scn.GetShadowCard("orc");
        scn.FreepsMoveCharToTable(sam);

        scn.StartGame();

        assertNotSame(scn.GetRingBearer(), sam);
        scn.AddWoundsToChar(frodo, 4);

        scn.SkipCurrentPhaseActions();

        assertTrue(scn.FreepsActionAvailable("Optional Trigger"));
    }
}
