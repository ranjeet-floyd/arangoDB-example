/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.arangodb.example.utils;

import graph.arangodb.example.bean.GraphDataBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.testng.AssertJUnit.assertEquals;
import org.testng.annotations.Test;

/**
 *
 * @author ranjeet
 */
public class CSVToGraphDataNGTest {

    private final static Logger logger = LoggerFactory.getLogger(ArangoDBNGTest.class);

    public CSVToGraphDataNGTest() {
    }

    /**
     * Test of apply method, of class CSVToGraphData.
     */
    @Test
    public void testApply() {
        System.out.println("apply");
        String graphColn = "\"Gathering\",\"Software\",\"R\",\"0.40\"";
        CSVToGraphData instance = new CSVToGraphData();
        GraphDataBean expResult = new GraphDataBean("gathering", "software", "r", 0.4);
        GraphDataBean result = instance.apply(graphColn);
        assertEquals(result.getWeight(), expResult.getWeight());
        assertEquals(result.getFromVertex(), expResult.getFromVertex());
        assertEquals(result.getToVertex(), expResult.getToVertex());
        assertEquals(result.getEdgeName(), expResult.getEdgeName());

    }

}
