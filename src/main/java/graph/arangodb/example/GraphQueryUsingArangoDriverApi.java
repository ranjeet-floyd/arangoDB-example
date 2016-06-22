/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.arangodb.example;

import com.arangodb.ArangoDriver;
import com.arangodb.ArangoException;
import com.arangodb.entity.CursorEntity;
import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import graph.arangodb.example.bean.Constant;
import graph.arangodb.example.bean.ResponseBean;
import graph.arangodb.example.bean.SampleDocumentEntity;
import graph.arangodb.example.utils.ArangoDB;

/**
 *
 * @author ranjeet
 */
public class GraphQueryUsingArangoDriverApi {

    public static void main(String[] args) throws MalformedURLException, ExecutionException, InterruptedException {
        String sampleVertex = "j2ee";
        long instantStart = System.nanoTime();
        ArangoDB arangoDB = new ArangoDB();
        ArangoDriver arangoDriver = arangoDB.getArangoDriver();
        String dbName = Constant.DB;
        String graphName = Constant.GRAPH;
        arangoDB.useDB(dbName, arangoDriver);
        instantStart = System.nanoTime();
        String vertex = new SampleDocumentEntity(sampleVertex).getKey();
        //Using arango java driver api.
        try {
            String query = "FOR v,e,p IN 1..2 OUTBOUND 'it-skill/" + vertex + "' GRAPH "
                    + "'" + graphName + "' FILTER (p.edges[0].name != 'r' "
                    + "and p.edges[1].name != 'r') FOR v1,e1,p1 IN 1..1 "
                    + "OUTBOUND v._id GRAPH 'tagcloud' FILTER (p1.edges[0].name == 'r') "
                    + "RETURN {\"m\": p1.vertices[0].name,\"s\": v1.name,\"r\": e1.name,\"w\": e1.Weight}";

            CursorEntity<ResponseBean> result = arangoDriver.executeCursorEntityQuery(query, null, false, 100, true, ResponseBean.class);
            System.out.println("Total result count : " + result.size() + "");
        } catch (ArangoException ex) {
            Logger.getLogger(GraphQueryUsingArangoDriverApi.class.getName()).log(Level.SEVERE, null, ex);
        }
        long instantEnd = System.nanoTime();
        System.out.println("****************");
        System.out.println(
                "Diff :" + (instantEnd - instantStart) / 1000000);
        System.out.println("****************");
    }

}
