package com.coding.test.FindPathBetweenCities.model;

import java.util.*;

public class Graph extends GraphBase {
    // Using HashMap to store city and its all neighbors in an undirected graph because path between cities is bidirectional
    // Using HashSet to store neighbors to avoid duplicate neighbors and acheive constant loopup time as well
    public Map<String, Set<String>> cityConnectionMap = new HashMap<>();

    /**
     *  Creates connection between source and destination.
     *  Returns true if the action is successful, otherwise returns false.
     *  Throws error for invalid input
     *
     * @param source
     * @param destination
     */
    @Override
    public boolean addConnection(String source, String destination) {

        try {
            Set<String> existingNeighborsOfSource = cityConnectionMap.getOrDefault(source, new HashSet<>());
            Set<String> existingNeighborsOfDestination = cityConnectionMap.getOrDefault(destination, new HashSet<>());

            if (!existingNeighborsOfSource.contains(destination)) {
                existingNeighborsOfSource.add(destination);
            }

            if (!existingNeighborsOfDestination.contains(source)) {
                existingNeighborsOfDestination.add(source);
            }

            this.cityConnectionMap.put(source, existingNeighborsOfSource);
            this.cityConnectionMap.put(destination, existingNeighborsOfDestination);

            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     *  Checks if there is a path between two cities.
     *  If atleast one of the cities are not in the given input file, it returns false.
     *
     * @param source
     * @param destination
     * @return
     */
    @Override
    public boolean isPathExists(String source, String destination) {

        if (!isValidCity(source) || !isValidCity(destination)) {
            return false;
        }

        // search for destination using Bread-first search.
        // Can also use Bidirectional search from source and desination to cut the runtime by half

        Queue<String> nextCitiesInQueue = new ArrayDeque<>();
        nextCitiesInQueue.add(source);
        Set<String> visitedCities = new HashSet<>();

        while(!nextCitiesInQueue.isEmpty()) {
            String currentCity = nextCitiesInQueue.remove();
            visitedCities.add(currentCity);
            Set<String> neighbors = this.cityConnectionMap.get(currentCity);

            for(String neighbor: neighbors) {
                if (!visitedCities.contains(neighbor)) {
                    if (neighbor.equals(destination)) {
                        return true;
                    }
                    nextCitiesInQueue.add(neighbor);
                }
            }
        }

        return false;
    }

    private boolean isValidCity(String city) {
        return this.cityConnectionMap.containsKey(city);
    }
}
