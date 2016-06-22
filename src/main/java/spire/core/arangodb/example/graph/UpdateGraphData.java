/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spire.core.arangodb.example.graph;

import com.arangodb.ArangoDriver;
import com.arangodb.ArangoException;
import java.io.IOException;
import java.util.stream.Stream;
import spire.commons.logger.Key;
import spire.commons.logger.Logger;
import spire.commons.logger.LoggerFactory;
import spire.core.arangodb.example.bean.Constant;
import spire.core.arangodb.example.bean.GraphDataBean;
import spire.core.arangodb.example.utils.ArangoDB;
import spire.core.arangodb.example.utils.FileUtils;
import spire.core.arangodb.example.utils.SampleDocumentEntityImpl;
import spire.core.arangodb.example.utils.SampleGraph;

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
                    logger.error(Key.EXCEPTION, ex);
                }
            });

        } catch (IOException ex) {
            logger.error(Key.EXCEPTION, ex);
        }
    }

}
