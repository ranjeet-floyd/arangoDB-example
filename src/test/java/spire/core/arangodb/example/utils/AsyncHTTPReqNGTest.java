/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spire.core.arangodb.example.utils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.testng.AssertJUnit;
import static org.testng.AssertJUnit.assertEquals;
import org.testng.annotations.Test;
import spire.commons.logger.Key;
import spire.commons.logger.Logger;
import spire.commons.logger.LoggerFactory;

/**
 *
 * @author ranjeet
 */
public class AsyncHTTPReqNGTest {

    private final static Logger logger = LoggerFactory.getLogger(ArangoDBNGTest.class);

    public AsyncHTTPReqNGTest() {
    }

    /**
     * Test of getAsync method, of class AsyncHTTPReq.
     */
    @Test
    public void testGetAsync() {
        try {
            logger.info(Key.MESSAGE, "getAsync");
            String postUrl = "http://localhost:8529/_db/tagcloud/_api/cursor";
            String vertex = "j2ee";
            String bodyJson = "{\"query\":\"\\n FOR v1,e1,p1 IN 1..2 OUTBOUND @vertices GRAPH  @graphName \\nFILTER (p1.edges[0].name !='r' and p1.edges[1].name != 'r') \\nFOR v2,e2,p2 IN 1..1 OUTBOUND v1._id GRAPH @graphName \\nFILTER (p2.edges[0].name == 'r') \\nRETURN {\\\"m\\\": p2.vertices[0].name,\\\"s\\\": v2.name,\\\"r\\\": e2.name,\\\"w\\\": e2.Weight}\\n\",\"batchSize\":1000,\"id\":\"currentFrontendQuery\",\"bindVars\":{\"graphName\":\"tagcloud\",\"vertices\":\"it-skill/" + vertex + "\"}}";
            //Fire all three request async..no thread waiting
            Future<HttpResponse<JsonNode>> result1 = AsyncHTTPReq.getAsync(postUrl, bodyJson);
            Future<HttpResponse<JsonNode>> result2 = AsyncHTTPReq.getAsync(postUrl, bodyJson);
            Future<HttpResponse<JsonNode>> result3 = AsyncHTTPReq.getAsync(postUrl, bodyJson);

            //put thread alive for 2 sec, so we ll get result for reqs.
            Thread.sleep(2000);
            int getStatus1 = result1.get().getStatus();
            assertEquals(getStatus1, 201);
            int getStatus2 = result2.get().getStatus();
            assertEquals(getStatus2, 201);
            int getStatus3 = result3.get().getStatus();
            assertEquals(getStatus3, 201);
        } catch (InterruptedException | ExecutionException ex) {
            logger.error(Key.EXCEPTION, ex);
            AssertJUnit.fail(ex.getMessage());
        }

    }

}
