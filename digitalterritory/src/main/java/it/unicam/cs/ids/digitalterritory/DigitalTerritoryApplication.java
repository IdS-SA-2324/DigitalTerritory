package it.unicam.cs.ids.digitalterritory;

import it.unicam.cs.ids.digitalterritory.model.Contributor;
import it.unicam.cs.ids.digitalterritory.services.OsmService;
import it.unicam.cs.ids.digitalterritory.utils.Coordinate;
import it.unicam.cs.ids.digitalterritory.utils.PointContained;
import org.hibernate.mapping.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class DigitalTerritoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigitalTerritoryApplication.class, args);

	}

}
