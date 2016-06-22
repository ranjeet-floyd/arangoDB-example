/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spire.core.arangodb.example.bean;

/**
 *
 * @author ranjeet
 */
public class GraphDataBean {

    private String fromVertex;
    private String toVertex;
    private String edgeName;
    private double weight;

    public GraphDataBean(String vertex1, String vertex2, String edgeName, double weight) {
        this.fromVertex = vertex1;
        this.toVertex = vertex2;
        this.edgeName = edgeName;
        this.weight = weight;
    }

    public String getFromVertex() {
        return fromVertex;
    }

    public void setFromVertex(String fromVertex) {
        this.fromVertex = fromVertex;
    }

    public String getToVertex() {
        return toVertex;
    }

    public void setToVertex(String toVertex) {
        this.toVertex = toVertex;
    }

    public String getEdgeName() {
        return edgeName;
    }

    public void setEdgeName(String edgeName) {
        this.edgeName = edgeName;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "\"{ \"fromVertex\" : " + fromVertex + "\"}";
    }

}
