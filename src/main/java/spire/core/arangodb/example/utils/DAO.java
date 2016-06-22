/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spire.core.arangodb.example.utils;

import com.arangodb.ArangoException;
import com.arangodb.entity.DocumentEntity;
import java.util.stream.Stream;
import spire.commons.logger.Key;
import spire.commons.logger.Logger;
import spire.commons.logger.LoggerFactory;
import spire.core.arangodb.example.bean.EdgeBean;
import spire.core.arangodb.example.bean.GraphDataBean;
import spire.core.arangodb.example.bean.SampleDocumentEntity;
import static spire.core.arangodb.example.utils.FileUtils.cleanString;

/**
 *
 * @author ranjeet
 */
public class DAO {

    private final static Logger logger = LoggerFactory.getLogger(DAO.class);

    public static void insertToDB(SampleGraph sampleGraph, final SampleDocumentEntityImpl documentEntityImpl, Stream<GraphDataBean> graphDataRows) {
        graphDataRows.parallel().forEach(graphDataRow -> {

            try {
                DocumentEntity<SampleDocumentEntity> mainEntity = documentEntityImpl.buildDocumentEntity(cleanString(graphDataRow.getFromVertex()));
                DocumentEntity<SampleDocumentEntity> subEntity = documentEntityImpl.buildDocumentEntity(cleanString(graphDataRow.getToVertex()));
                sampleGraph.createGraphEdge(mainEntity, subEntity,
                        new EdgeBean(graphDataRow.getEdgeName(), graphDataRow.getWeight()));

            } catch (ArangoException ex) {
                logger.error(Key.EXCEPTION, ex);
            }
        });

    }

}
