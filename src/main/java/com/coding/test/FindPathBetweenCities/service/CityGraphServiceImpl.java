package com.coding.test.FindPathBetweenCities.service;

import com.coding.test.FindPathBetweenCities.model.Graph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.coding.test.FindPathBetweenCities.Constants.RESULT_CONNECTED;
import static com.coding.test.FindPathBetweenCities.Constants.RESULT_NOT_CONNECTED;

@Service
public class CityGraphServiceImpl implements  CityGraphService {

    private static final Logger LOG = LoggerFactory.getLogger(CityGraphServiceImpl.class);

    @Value("${connected.cities.input.filename}")
    private String cityPathInputFile;

    private Graph cityGraph;

    @PostConstruct
    @Override
    public void initializeCityConnectionMap() {

        List<String> cityConnections = new ArrayList<>();

        try {
            Path filePath = ResourceUtils.getFile("classpath:" + cityPathInputFile).toPath();

            cityConnections = Files.readAllLines(filePath);
        } catch (IOException ex) {
            LOG.error("Unable to read the file with city connections. File name: " + this.cityPathInputFile, ex);
        }

        cityGraph = new Graph();

        for(String connection: cityConnections) {
            if (!connection.isEmpty()) {
                String sourceDestinationArr[] = connection.split(",");
                if (sourceDestinationArr.length != 2) {
                    LOG.warn("Invalid path present while parsing connections. " +
                            "Each line must contain only two cities separated by one comma.");
                    LOG.warn("Skipping the invalid connection found: "+ connection);
                }
                String source = sourceDestinationArr[0].trim();
                String neighbor = sourceDestinationArr[1].trim();

                if (source.isEmpty() || neighbor.isEmpty()) {
                    LOG.error("Connection has an empty string. Connection= " + connection);

                    // Can also throw exception and stop parsing right here
                    continue;
                }

                // converting all city names to lower case to handle improper capitalization
                // It's nice to use a nice third party library (or build one) that would give standardized city name
                boolean success = this.cityGraph.addConnection(source.toLowerCase(), neighbor.toLowerCase());

                if (!success) {
                    LOG.error("Unexpected error occurred while adding connection " +
                            "between '"+ source +"' and '"+ neighbor +"'");
                }

            } else {
                LOG.warn("Empty line present in the input file");
            }
        }
    }

    @Override
    public String checkPathBetweenCities(String source, String destination) {

        if (source == null || destination == null || source.isEmpty() || destination.isEmpty()) {
            throw new IllegalArgumentException("Source and Destination cannot be empty");
        }

        boolean isPathExists = this.cityGraph.isPathExists(source, destination);

        return isPathExists ? RESULT_CONNECTED : RESULT_NOT_CONNECTED;
    }
}
