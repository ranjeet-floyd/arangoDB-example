/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.arangodb.example;

import com.arangodb.ArangoDriver;
import com.arangodb.CursorResult;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import graph.arangodb.example.bean.Constant;
import graph.arangodb.example.bean.ResponseBean;
import graph.arangodb.example.utils.ArangoDB;
import graph.arangodb.example.utils.FileUtils;
import graph.arangodb.example.utils.ResponseExample;
import graph.arangodb.example.utils.SampleTask;

/**
 * Query Arango Graph data using Executor Task. (java.util.concurrent.Executors)
 *
 * @see First Build Graph Data by running FillGraphDataToArangoDB class
 * @author ranjeet
 */
public class GraphQueryUsingExecutor {

    public static void main(String[] args) {

        String resourceTestFile = "test.txt";
        // Have one (or more) threads ready to do the async tasks. Do this during startup of your app.
        ExecutorService executor = Executors.newFixedThreadPool(100);

        long instantStart = System.nanoTime();
        try {

            ArangoDB arangoDB = new ArangoDB();
            ArangoDriver arangoDriver = arangoDB.getArangoDriver();

            String dbName = Constant.DB;
            String graphName = Constant.GRAPH;

            arangoDB.useDB(dbName, arangoDriver);

            List<String> vertices = FileUtils.getVertices(FileUtils.getResourceFile(resourceTestFile));
            instantStart = System.nanoTime();
            List<Future<ResponseExample>> futuresResponses = new ArrayList<>();
            //Make test data small
            List<String> smallVertices = vertices.subList(0, 100);
            smallVertices.parallelStream().forEach(skill -> {
                String query = "FOR v,e,p IN 1..2 OUTBOUND \'it-skill/" + skill.replaceAll("[^a-zA-Z0-9]", "-").toLowerCase() + "' GRAPH "
                        + "'" + graphName + "' FILTER (p.edges[0].name!='r' "
                        + "and p.edges[1].name!='r') FOR v1,e1,p1 IN 1..1 "
                        + "OUTBOUND v._id GRAPH 'tagcloud' FILTER (p1.edges[0].name=='r') "
                        + "RETURN {\"m\": p1.vertices[0].name,\"s\": v1.name,\"r\": e1.name,\"w\": e1.Weight}";

                //Create a sample task and run async
                SampleTask sampleTask = new SampleTask(query, arangoDriver);

                // Do your other tasks here (will be processed immediately, current thread won't block).
                Future<ResponseExample> response = executor.submit(sampleTask);
                futuresResponses.add(response);

            });

            for (Future<ResponseExample> futuresResponse : futuresResponses) {
                CursorResult<ResponseBean> result = futuresResponse.get().getResult();
                System.out.println(result.iterator());
            }
        } catch (IOException | InterruptedException | ExecutionException ex) {
            Logger.getLogger(GraphQueryUsingExecutor.class.getName()).log(Level.SEVERE, null, ex);
        }
        long instantEnd = System.nanoTime();
        System.out.println("****************");
        System.out.println(
                "Diff :" + (instantEnd - instantStart) / 1000000);
        System.out.println("****************");
        // Shutdown the threads during shutdown of your app.
        executor.shutdown();
    }

}
