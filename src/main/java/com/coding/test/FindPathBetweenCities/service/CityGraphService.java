package com.coding.test.FindPathBetweenCities.service;

import com.coding.test.FindPathBetweenCities.model.Graph;
import org.springframework.beans.factory.annotation.Value;

public interface CityGraphService {

    public String checkPathBetweenCities(String source, String destination);

    public void initializeCityConnectionMap();
}
