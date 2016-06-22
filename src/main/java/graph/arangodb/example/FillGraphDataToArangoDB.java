/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.arangodb.example;

import com.arangodb.ArangoDriver;
import com.arangodb.ArangoException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import graph.arangodb.example.bean.Constant;
import graph.arangodb.example.bean.GraphDataBean;
import graph.arangodb.example.utils.ArangoDB;
import graph.arangodb.example.utils.DAO;
import graph.arangodb.example.utils.FileUtils;
import graph.arangodb.example.utils.SampleDocumentEntityImpl;
import graph.arangodb.example.utils.SampleEdge;
import graph.arangodb.example.utils.SampleGraph;

/**
 * Insert graph data from resource files to arango DB. First run this class to
 * fill graph data in DB.
 *
 * @author ranjeet
 */
public class FillGraphDataToArangoDB {

    public static void main(String[] args) {
        String graphDataFileName1 = "graphData1.csv";
        String graphDataFileName2 = "graphData2.csv";

        try {
            ArangoDB arangoDB = new ArangoDB();
            ArangoDriver arangoDriver = arangoDB.getArangoDriver();

            //create a database
            String dbName = Constant.DB;
            //create db and set as default db
            arangoDB.createDB(dbName, arangoDriver);
            //Now setup edge and vertex
            String edgeName = Constant.EDGE;
            String fromEntityColl = Constant.VERTEX;
            String toEntityColl = Constant.VERTEX;
            String graphName = Constant.GRAPH;

            SampleEdge spireEdge = new SampleEdge(edgeName);
            //Now Create graph with edge
            SampleGraph sampleGraph = new SampleGraph(arangoDriver, graphName);

            //***************************************************************
            //Fill the graph with vertices…
            final SampleDocumentEntityImpl spireVertex = new SampleDocumentEntityImpl(arangoDriver, graphName,
                    fromEntityColl);
            sampleGraph.buildNewEdgeCollection(spireEdge, fromEntityColl, toEntityColl);
            Stream<GraphDataBean> synSkillClouds = FileUtils.getGraphDatas(FileUtils.getResourceFile(graphDataFileName1));
            //finally insert in db
            DAO.insertToDB(sampleGraph, spireVertex, synSkillClouds);

            Stream<GraphDataBean> graphData = FileUtils.getGraphDatas(FileUtils.getResourceFile(graphDataFileName2));
            DAO.insertToDB(sampleGraph, spireVertex, graphData);

        } catch (IOException | ArangoException ex) {
            Logger.getLogger(UpdateGraphData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
