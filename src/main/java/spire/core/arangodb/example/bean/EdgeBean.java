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
public class EdgeBean {

    private String name;
    private double Weight;

    public EdgeBean() {
    }

    public EdgeBean(String name, double Weight) {
        this.name = name;
        this.Weight = Weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return Weight;
    }

    public void setWeight(double Weight) {
        this.Weight = Weight;
    }

}
