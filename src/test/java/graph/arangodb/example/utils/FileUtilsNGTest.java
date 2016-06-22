/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.arangodb.example.utils;

import graph.arangodb.example.bean.GraphDataBean;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.testng.Assert.assertNotEquals;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.fail;
import org.testng.annotations.Test;

/**
 *
 * @author ranjeet
 */
public class FileUtilsNGTest {

    private final static Logger logger = LoggerFactory.getLogger(ArangoDBNGTest.class);
    private static ClassLoader classLoader;

    public FileUtilsNGTest() {
        classLoader = FileUtilsNGTest.class.getClassLoader();
    }

    /**
     * Test of cleanString method, of class FileUtils.
     */
    @Test
    public void testCleanString() {
        System.out.println("cleanString");
        String str = "\"java\"";
        String expResult = "java";
        String result = FileUtils.cleanString(str);
        assertEquals(result, expResult);
    }

    /**
     * Test of getVertices method, of class FileUtils.
     */
    @Test
    public void testGetVertices() {
        try {
            System.out.println("getVertices");
            String fileName = classLoader.getResource("testvertex.txt").getPath();
            List<String> expResult = null;
            List<String> result = FileUtils.getVertices(fileName);
            assertNotEquals(result.size(), 0);
        } catch (IOException ex) {
            logger.error("EXCEPTION", ex);
            fail(ex.getMessage());
        }
    }

    /**
     * Test of getGraphDatas method, of class FileUtils.
     */
    @Test
    public void testGetGraphDatas() {
        try {
            System.out.println("getGraphDatas");
            //load main resource file
            String resourceFileName = "graphData1.csv";
            Stream<GraphDataBean> result = FileUtils.getGraphDatas(resourceFileName);
            assertNotEquals(result.count(), 0);
        } catch (IOException ex) {
            logger.error("EXCEPTION", ex);
            fail(ex.getMessage());
        }
    }

    /**
     * Test of getResourceFile method, of class FileUtils.
     */
    @Test
    public void testGetResourceFile() {
        System.out.println("getResourceFile");
        String resourceFileName = "smalltest.txt"; //in main resource file
        String expResult = ""; //
        String result = FileUtils.getResourceFile(resourceFileName);
        //expected result should not be empty.
        assertNotEquals(result.isEmpty(), expResult.isEmpty());

    }

}
