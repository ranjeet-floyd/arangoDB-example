/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.arangodb.example.utils;

import graph.arangodb.example.bean.GraphDataBean;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ranjeet
 */
public class FileUtils {

    private final static Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * Remove any escape character
     *
     * @param str
     * @return clean string
     */
    public static String cleanString(String str) {
        return str.toLowerCase().replaceAll("\"", "").trim();
    }

    /**
     * *
     *
     * @param fileName
     * @return String list
     * @throws IOException
     */
    public static List<String> getVertices(String fileName) throws IOException {
        List<String> lines = Files.readAllLines(new File(fileName).toPath());
        List<String> skills = new ArrayList<>();
        lines.stream().forEach((line) -> {
            skills.addAll(Arrays.asList(line.split(",")));
        });
        return skills;

    }

    public static Stream<GraphDataBean> getGraphDatas(String resourceFileName) throws IOException {
        logger.info("MESSAGE", "get graph data from resource file :" + resourceFileName);
        Stream<String> graphRows = FileUtils.getGraphData(FileUtils.getResourceFile(resourceFileName));
        //check data is in correct format or not.
        //correct format : "java","j2ee","R","0.6" . Last colum should be numeric string.
        return graphRows.filter(g -> {
            String[] gColns = g.split(",");
            return gColns.length == 4 && checkStringIsNumeric(cleanString(gColns[3]));
        }).map(new <String, GraphDataBean>CSVToGraphData());

    }

    private static Stream<String> getGraphData(String fileName) throws IOException {
        Stream<String> skills = Files.lines(new File(fileName).toPath());
        return skills;
    }

    public static String getResourceFile(String resourceFileName) {
        String path = FileUtils.class
                .getClassLoader().getResource(resourceFileName).getPath();
        return path;
    }

    public static boolean checkStringIsNumeric(String input) {
        if (null == input || input.trim().isEmpty()) {
            return false;
        }
        boolean isNumeric = input.chars().allMatch(Character::isDigit);
        return isNumeric;
    }

}
