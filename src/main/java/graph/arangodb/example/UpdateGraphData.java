/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.arangodb.example;

import com.arangodb.ArangoDriver;
import com.arangodb.ArangoException;
import graph.arangodb.example.bean.Constant;
import graph.arangodb.example.bean.GraphDataBean;
import graph.arangodb.example.utils.ArangoDB;
import graph.arangodb.example.utils.FileUtils;
import graph.arangodb.example.utils.SampleDocumentEntityImpl;
import graph.arangodb.example.utils.SampleGraph;
import java.io.IOException;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Update existing graph data.
 *
 * @see first fill data using FillGraphDataToArangoDB class.
 * @author ranjeet
 */
public class UpdateGraphData {

    private static final Logger logger = LoggerFactory.getLogger(UpdateGraphData.class);

    public static void main(String[] args) {
        //Add resource file data
        String graphDataFileName3 = "graphData3.csv";
        try {
            ArangoDB arangoDB = new ArangoDB();
            ArangoDriver arangoDriver = arangoDB.getArangoDriver();
            String dbName = Constant.DB;
            arangoDB.useDB(dbName, arangoDriver);
            String graphName = Constant.GRAPH;
            String fromEntityColl = Constant.VERTEX;

            SampleGraph existingSampleGraph = new SampleGraph(arangoDriver, graphName);
            final SampleDocumentEntityImpl spireVertex = new SampleDocumentEntityImpl(arangoDriver, graphName,
                    fromEntityColl);
            //Update with new graph data
            Stream<GraphDataBean> newGraphRows = FileUtils.getGraphDatas(FileUtils.getResourceFile(graphDataFileName3));
            newGraphRows.parallel().forEach(graphDataBean -> {
                try {
                    existingSampleGraph.addMoreGraphData(graphDataBean, spireVertex);
                } catch (ArangoException ex) {
                    logger.error("EXCEPTION", ex);
                }
            });

        } catch (IOException ex) {
            logger.error("EXCEPTION", ex);
        }
    }

}
