package it.unicam.cs.ids.digitalterritory;
import it.unicam.cs.ids.digitalterritory.services.OsmService;
import it.unicam.cs.ids.digitalterritory.utils.Coordinate;
import it.unicam.cs.ids.digitalterritory.utils.PointContained;
import jakarta.validation.constraints.AssertTrue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RayCastingTest {


    @Test
    public void should_be_contaned() throws Exception {
        OsmService service=new OsmService();
        PointContained pointContained= new PointContained(new Coordinate(12.390828,43.110717),service.getComuneByNomeRegione("Perugia","Umbria").getGeoJson().getCoordinates().get(0));
        assertTrue (pointContained.isPointContained());
    }
}