package com.coding.test.FindPathBetweenCities.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GraphBase {

    //public Map<String, List<String>> cityConnectionMap = new HashMap<>();

    public abstract boolean addConnection(String source, String neighbor);

    public abstract boolean isPathExists(String source, String destination);

}
