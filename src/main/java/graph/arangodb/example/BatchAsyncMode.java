/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.arangodb.example;

import com.arangodb.ArangoDriver;
import com.arangodb.ArangoException;
import com.arangodb.entity.CursorEntity;
import com.arangodb.entity.JobsEntity.JobState;
import com.arangodb.util.MapBuilder;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import graph.arangodb.example.bean.Constant;
import graph.arangodb.example.bean.ResponseBean;
import graph.arangodb.example.utils.ArangoDB;
import graph.arangodb.example.utils.FileUtils;

/**
 *
 * @author ranjeet
 */
public class BatchAsyncMode {

    public static void main(String[] args) {

        long instantStart = 0;
        try {

            ArangoDB arangoDB = new ArangoDB();
            ArangoDriver arangoDriver = arangoDB.getArangoDriver();

            //start sync mode
            arangoDriver.startAsyncMode(false);
            String dbName = Constant.DB;
            arangoDB.useDB(dbName, arangoDriver);
            String graphName = Constant.GRAPH;

            instantStart = System.nanoTime();
            List<String> skillClouds = FileUtils.getVertices("/home/ranjeet/Downloads/accenture-usMustSkill_Common.txt");
            List<String> skillCloudsSub = skillClouds.subList(0, 1);
            skillCloudsSub.parallelStream().forEach(skill -> {
                try {
                    Map<String, Object> exampleVertexMap = new MapBuilder()
                            .put("graphName", graphName)
                            .put("vertex", "it-skill/" + skill.replaceAll("[^a-zA-Z0-9]", "-").toLowerCase()).get();

                    String query = "FOR v,e,p IN 1..2 OUTBOUND @vertex GRAPH "
                            + " @graphName FILTER (p.edges[0].name !='r' "
                            + "and p.edges[1].name !='r') FOR v1,e1,p1 IN 1..1 "
                            + "OUTBOUND v._id GRAPH 'tagcloud' FILTER (p1.edges[0].name =='r') "
                            + "RETURN {\"m\": p1.vertices[0].name,\"s\": v1.name,\"r\": e1.name,\"w\": e1.Weight}";

                    CursorEntity<ResponseBean> result = arangoDriver.executeCursorEntityQuery(query, exampleVertexMap, false, 1000, false, ResponseBean.class);

                } catch (ArangoException ex) {
                    Logger.getLogger(GraphQueryUsingArangoDriverApi.class.getName()).log(Level.SEVERE, null, ex);
                }

            });
            long instantEnd = System.nanoTime();
            System.out.println("****************");
            System.out.println("Diff :" + (instantEnd - instantStart) / 1000000);
            System.out.println("****************");
            //Stop sync mode
            arangoDriver.stopAsyncMode();

            //Check if all jobs finished then only proceed.
            Boolean allJobsFinished = false;
            while (!allJobsFinished) {
                if (arangoDriver.getJobs(JobState.PENDING).isEmpty()) {
                    allJobsFinished = true;
                }
            }
            List<String> jobIds = arangoDriver.getJobIds();
            jobIds.forEach(jobId -> {
                try {
                    CursorEntity<ResponseBean> responseBeans = arangoDriver.getJobResult(jobId);
                    System.out.println("Total result count : " + responseBeans.size() + "");
                } catch (ArangoException ex) {
                    Logger.getLogger(BatchAsyncMode.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

        } catch (IOException | ArangoException ex) {
            Logger.getLogger(BatchAsyncMode.class.getName()).log(Level.SEVERE, null, ex);
        }
        long instantEnd = System.nanoTime();
        System.out.println("****************");
        System.out.println(
                "Diff :" + (instantEnd - instantStart) / 1000000);
        System.out.println("****************");
    }

}
