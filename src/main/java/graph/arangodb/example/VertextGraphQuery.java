/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.arangodb.example;

import com.arangodb.ArangoDriver;
import com.arangodb.ArangoException;
import com.arangodb.EdgeCursor;
import com.arangodb.entity.BaseDocument;
import com.arangodb.entity.EdgeEntity;
import java.util.Iterator;
import graph.arangodb.example.bean.Constant;
import graph.arangodb.example.bean.SampleDocumentEntity;
import graph.arangodb.example.utils.ArangoDB;

/**
 * Returns all Edges of a given vertex.
 *
 * @author ranjeet
 */
public class VertextGraphQuery {

    public static void main(String[] args) throws ArangoException {

        ArangoDB arangoDB = new ArangoDB();
        ArangoDriver arangoDriver = arangoDB.getArangoDriver();
        String dbName = Constant.DB;
        String graphName = Constant.GRAPH;
        arangoDB.useDB(dbName, arangoDriver);

        //Returns all Edges of a given vertex.
        String vertexName = "java";
        EdgeCursor<BaseDocument> edgeCursor = arangoDriver.graphGetEdgeCursorByExample(graphName, BaseDocument.class, new SampleDocumentEntity(vertexName));

        Iterator<EdgeEntity<BaseDocument>> it = edgeCursor.iterator();

        while (it.hasNext()) {
            EdgeEntity<BaseDocument> d = it.next();
            System.out.println(d.getFromVertexHandle() + " " + d.getToVertexHandle() + " " + d.getDocumentHandle());
        }

    }

}
