/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.arangodb.example.utils;

import com.arangodb.ArangoConfigure;
import com.arangodb.ArangoDriver;
import com.arangodb.ArangoException;

/**
 *
 * @author ranjeet
 */
public class ArangoDB {
    /*
     Create arango driver
     */

    public ArangoDriver getArangoDriver() {
        // Initialize configure
        ArangoConfigure configure = new ArangoConfigure();
        configure.init();

        // Create Driver (this instance is thread-safe)
        ArangoDriver arangoDriver = new ArangoDriver(configure);
        return arangoDriver;
    }

    /*
     Create database and set as default
     */
    public void createDB(final String dbName, final ArangoDriver arangoDriver) throws ArangoException {
        // create database
        arangoDriver.createDatabase(dbName);
        // and set as default
        this.useDB(dbName, arangoDriver);
    }

    public void useDB(final String dbName, final ArangoDriver arangoDriver) {
        // and set as default
        arangoDriver.setDefaultDatabase(dbName);

    }

}
