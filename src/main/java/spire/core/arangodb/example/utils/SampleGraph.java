/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spire.core.arangodb.example.utils;

import com.arangodb.ArangoDriver;
import com.arangodb.ArangoException;
import com.arangodb.entity.DocumentEntity;
import com.arangodb.entity.EdgeDefinitionEntity;
import com.arangodb.entity.EdgeEntity;
import com.arangodb.entity.GraphEntity;
import java.util.ArrayList;
import java.util.List;
import spire.commons.logger.Key;
import spire.commons.logger.Logger;
import spire.commons.logger.LoggerFactory;
import spire.core.arangodb.example.bean.Constant;
import spire.core.arangodb.example.bean.EdgeBean;
import spire.core.arangodb.example.bean.GraphDataBean;
import spire.core.arangodb.example.bean.SampleDocumentEntity;
import static spire.core.arangodb.example.utils.FileUtils.cleanString;

/**
 *
 * @author ranjeet
 */
public class SampleGraph {

    private final Logger logger = LoggerFactory.getLogger(SampleGraph.class);
    private final ArangoDriver arangoDriver;
    private final String graphName;

    public SampleGraph(ArangoDriver arangoDriver, String graphName) {
        this.arangoDriver = arangoDriver;
        this.graphName = graphName;
    }

    private GraphEntity createGraph(List<EdgeDefinitionEntity> edgeDefinitions, List<String> orphanCollections) throws ArangoException {
        // Create the graph:("Academical"

        GraphEntity graphEntity = this.arangoDriver.createGraph(this.graphName,
                edgeDefinitions, orphanCollections, true);
        return graphEntity;
    }

    // add the new definition to the existing graph:
    public GraphEntity addNewEdgeDefToExistingGraph(EdgeDefinitionEntity edgeDefinitionEntity) throws ArangoException {
        return arangoDriver.graphCreateEdgeDefinition(this.graphName, edgeDefinitionEntity);
    }

    //Fill the graph with edge:
//    public EdgeEntity<EdgeBean> createGraphEdge(DocumentEntity<SampleDocumentEntity> sourceDocEntity, DocumentEntity<SampleDocumentEntity> destinationDocEntity, String relation) throws ArangoException {
//        EdgeEntity<EdgeBean> edge = arangoDriver.graphCreateEdge(graphName, Constant.EDGE, null, sourceDocEntity.getDocumentHandle(), destinationDocEntity.getDocumentHandle(),
//                new EdgeBean(relation, EdgeWeightConst.STRONG), null);
//        return edge;
//    }
    /**
     * 	 * Stores a new edge with the information contained within the body into
     * the given collection.
     *
     * @param sourceDocEntity from vertex
     * @param destinationDocEntity to vertex
     * @param edgeBean edge coll
     * @return EdgeEntity obj
     * @throws ArangoException
     */
    public EdgeEntity<EdgeBean> createGraphEdge(DocumentEntity<SampleDocumentEntity> sourceDocEntity, DocumentEntity<SampleDocumentEntity> destinationDocEntity, EdgeBean edgeBean) throws ArangoException {
        EdgeEntity<EdgeBean> edge = arangoDriver.graphCreateEdge(graphName, Constant.EDGE, null, sourceDocEntity.getDocumentHandle(), destinationDocEntity.getDocumentHandle(),
                edgeBean, null);
        return edge;
    }

    public void addMoreGraphData(final GraphDataBean graphDataBean, final SampleDocumentEntityImpl spireVertex) throws ArangoException {
        DocumentEntity<SampleDocumentEntity> mainEntity = spireVertex.buildDocumentEntity(cleanString(graphDataBean.getFromVertex()));
        DocumentEntity<SampleDocumentEntity> subEntity = spireVertex.buildDocumentEntity(cleanString(graphDataBean.getToVertex()));
        this.createGraphEdge(subEntity, mainEntity, new EdgeBean(graphDataBean.getEdgeName(), graphDataBean.getWeight()));
        logger.info(Key.MESSAGE, "Added to graph :" + graphDataBean.toString());
    }

    /*
     Create edge between vertex
     */
    public GraphEntity buildNewEdgeCollections(List<String> edgeCollection, String fromEntityColl, String toEntityColl) throws ArangoException {

        List<EdgeDefinitionEntity> edgeDefinitionEntitys = new ArrayList<>();
        edgeCollection.forEach(edge -> {
            SampleEdge spireEdge = new SampleEdge(edge);
            //Create a edge from Entity to Entity in
            EdgeDefinitionEntity edgeDefinitionEntity = spireEdge.buildEdgeDefinitionEntity(fromEntityColl, toEntityColl);
            edgeDefinitionEntitys.add(edgeDefinitionEntity);

        });
        GraphEntity graphEntity = this.createGraph(edgeDefinitionEntitys, null);
        return graphEntity;

    }

    /*
     Create edge between vertex
     */
    public GraphEntity buildNewEdgeCollection(SampleEdge spireEdge, String fromEntityColl, String toEntityColl) throws ArangoException {

        List<EdgeDefinitionEntity> edgeDefinitionEntitys = new ArrayList<>();

        //Create a edge from Entity to Entity
        EdgeDefinitionEntity edgeDefinitionEntity = spireEdge.buildEdgeDefinitionEntity(fromEntityColl, toEntityColl);
        edgeDefinitionEntitys.add(edgeDefinitionEntity);

        GraphEntity graphEntity = this.createGraph(edgeDefinitionEntitys, null);
        return graphEntity;

    }

    /*
     Create edge between vertex exsiting vetex.
     */
    public void buildExistingCollection(SampleEdge sampleEdge, String fromEntityColl, String toEntityColl) throws ArangoException {

        //Create a edge from Person to Publication in
        EdgeDefinitionEntity edgeDefinitionEntity = sampleEdge.buildEdgeDefinitionEntity(fromEntityColl, toEntityColl);
        this.addNewEdgeDefToExistingGraph(edgeDefinitionEntity);

    }

}
