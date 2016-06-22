/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.arangodb.example.utils;

import com.arangodb.entity.EdgeDefinitionEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ranjeet
 */
public class SampleEdge {

    private final String edgeCollName;
    private EdgeDefinitionEntity edgeDefinitionEntity = null;

    public SampleEdge(String edgeCollName) {
        this.edgeCollName = edgeCollName;
    }

    /*
     Build  the vertex collection(s) where an edge starts...and  ends.
     */
    public EdgeDefinitionEntity buildEdgeDefinitionEntity(String fromVertex, String toVertex) {
        if (null == this.edgeDefinitionEntity) {
            edgeDefinitionEntity = this.defineEdgeCollection();
        }
        this.addFromEdge(fromVertex, edgeDefinitionEntity);
        this.addToEdge(toVertex, edgeDefinitionEntity);
        return edgeDefinitionEntity;
    }

    /*
     Define the edge collection...
     */
    private EdgeDefinitionEntity defineEdgeCollection() {
        // We start with one edge definition:
        this.edgeDefinitionEntity = new EdgeDefinitionEntity();
        // Define the edge collection...eg "HasWritten"
        edgeDefinitionEntity.setCollection(edgeCollName);
        return edgeDefinitionEntity;

    }

    private void addFromEdge(String fromVertex, EdgeDefinitionEntity edgeDefinitionEntity) {
        // ... and the vertex collection(s) where an edge starts...
        List<String> from = new ArrayList<>();
        //eg. "Person"
        from.add(fromVertex);
        edgeDefinitionEntity.setFrom(from);
    }

    private void addToEdge(String toVertex, EdgeDefinitionEntity edgeDefinitionEntity) {
        // ... and ends.
        List<String> to = new ArrayList<>();
        //"Publication"
        to.add(toVertex);
        edgeDefinitionEntity.setTo(to);
    }

}
