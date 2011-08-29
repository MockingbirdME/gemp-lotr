package com.gempukku.lotro.cards;

import com.gempukku.lotro.game.LotroCardBlueprint;

import java.util.HashMap;
import java.util.Map;

public class LotroCardBlueprintLibrary {
    private String[] _packageNames =
            new String[]{
                    "", ".dwarven", ".elven", ".isengard", ".shire", ".site"
            };
    private Map<String, LotroCardBlueprint> _blueprintMap = new HashMap<String, LotroCardBlueprint>();

    public LotroCardBlueprint getLotroCardBlueprint(String blueprintId) {
        if (_blueprintMap.containsKey(blueprintId))
            return _blueprintMap.get(blueprintId);

        LotroCardBlueprint blueprint = getBlueprint(blueprintId);
        _blueprintMap.put(blueprintId, blueprint);
        return blueprint;
    }

    private LotroCardBlueprint getBlueprint(String blueprintId) {
        String[] blueprintParts = blueprintId.split("_");

        String setNumber = blueprintParts[0];
        String cardNumber = blueprintParts[1];

        for (String packageName : _packageNames) {
            LotroCardBlueprint blueprint = null;
            try {
                blueprint = tryLoadingFromPackage(packageName, setNumber, cardNumber);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
            if (blueprint != null)
                return blueprint;
        }

        throw new IllegalArgumentException("Didn't find card with blueprintId: " + blueprintId);
    }

    private LotroCardBlueprint tryLoadingFromPackage(String packageName, String setNumber, String cardNumber) throws IllegalAccessException, InstantiationException {
        try {
            Class clazz = Class.forName("com.gempukku.lotro.cards.set" + setNumber + packageName + ".Card" + setNumber + "_" + normalizeId(cardNumber));
            return (LotroCardBlueprint) clazz.newInstance();
        } catch (ClassNotFoundException e) {
            // Ignore
            return null;
        }
    }

    private String normalizeId(String blueprintPart) {
        int id = Integer.parseInt(blueprintPart);
        if (id < 10)
            return "00" + id;
        else if (id < 100)
            return "0" + id;
        else
            return String.valueOf(id);
    }
}
