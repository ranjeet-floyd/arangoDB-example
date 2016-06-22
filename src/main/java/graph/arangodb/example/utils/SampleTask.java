/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.arangodb.example.utils;

import com.arangodb.ArangoDriver;
import com.arangodb.util.AqlQueryOptions;
import java.util.concurrent.Callable;
import graph.arangodb.example.bean.ResponseBean;

/**
 *
 * @author ranjeet
 */
public class SampleTask implements Callable<ResponseExample> {

    private ArangoDriver arangoDriver;
    private String query;

    public SampleTask(final String query, final ArangoDriver arangoDriver) {

        this.arangoDriver = arangoDriver;
        this.query = query;
    }

    @Override
    public ResponseExample call() throws Exception {
        System.out.println("Got request");
        final AqlQueryOptions aqlQueryOptions = new AqlQueryOptions().setCount(true);
        return new ResponseExample(arangoDriver.executeAqlQuery(query, null, aqlQueryOptions, ResponseBean.class));
//        return new ResponseExample(arangoDriver.executeCursorEntityQuery(query, null, false, 500, true, ResponseBean.class));
    }

}
