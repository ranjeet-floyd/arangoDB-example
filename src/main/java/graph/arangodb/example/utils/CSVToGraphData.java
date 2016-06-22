/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.arangodb.example.utils;

import java.util.function.Function;
import graph.arangodb.example.bean.GraphDataBean;

/**
 *
 * @author ranjeet
 */
public class CSVToGraphData implements Function<String, GraphDataBean> {

    @Override
    public GraphDataBean apply(String graphColn) {
        String[] graphColns = graphColn.split(",");
        return new GraphDataBean(FileUtils.cleanString(graphColns[0]), FileUtils.cleanString(graphColns[1]), FileUtils.cleanString(graphColns[2]), Double.parseDouble(FileUtils.cleanString(graphColns[3])));
    }

}
