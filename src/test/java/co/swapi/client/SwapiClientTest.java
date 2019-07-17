package co.swapi.client;

import co.swapi.model.PlanetSearch;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.impl.client.HttpClients;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class SwapiClientTest {

    private SwapiClient swapiClient;
    @Before
    public void setup(){
        swapiClient = new SwapiClient(HttpClients.createMinimal(),new ObjectMapper());
    }

    @Test
    public void testSearch(){
        PlanetSearch planetSearch = swapiClient.executeSearch("Alderaan");
        assertEquals(1,planetSearch.getResults().size());
    }

}
