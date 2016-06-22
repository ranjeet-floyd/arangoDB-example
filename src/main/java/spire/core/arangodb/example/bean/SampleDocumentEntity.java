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
public class SampleDocumentEntity {

    private String name;
    private String _key;

    public SampleDocumentEntity() {
    }

    public SampleDocumentEntity(String name) {
        this.name = name.toLowerCase();
        this._key = name.replaceAll("[^a-zA-Z0-9]", "-").toLowerCase();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return _key;
    }

    public void setKey(String _key) {
        this._key = _key;
    }

}
