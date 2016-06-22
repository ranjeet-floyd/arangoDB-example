/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spire.core.arangodb.example.utils;

import com.arangodb.ArangoDriver;
import com.arangodb.ArangoException;
import com.arangodb.entity.DocumentEntity;
import spire.commons.logger.Key;
import spire.commons.logger.Logger;
import spire.commons.logger.LoggerFactory;
import spire.core.arangodb.example.bean.SampleDocumentEntity;

/**
 *
 * @author ranjeet
 */
public class SampleDocumentEntityImpl {

    private static final Logger logger = LoggerFactory.getLogger(SampleDocumentEntityImpl.class);
    private final ArangoDriver arangoDriver;
    private final String graphName;
    private final String fromEntityColl;

    public SampleDocumentEntityImpl(ArangoDriver arangoDriver, String graphName, String fromEntityColl) {
        this.arangoDriver = arangoDriver;
        this.graphName = graphName;
        this.fromEntityColl = fromEntityColl;
    }

    /**
     * Stores a new vertex with the information contained within the document
     * into the given collection.
     *
     * @param vertex
     * @return <T> DocumentEntity<T> The resulting DocumentEntity containing the
     * vertex document.
     * @throws ArangoException
     */
    public DocumentEntity<SampleDocumentEntity> buildDocumentEntity(SampleDocumentEntity vertex) throws ArangoException {
        DocumentEntity<SampleDocumentEntity> documentEntity = arangoDriver.graphCreateVertex(graphName, fromEntityColl,
                vertex, true);

        return documentEntity;
    }

    /**
     * *
     * Fill the graph with vertex:
     *
     * @param sampleEntityName
     * @return
     * @throws ArangoException
     */
    public DocumentEntity<SampleDocumentEntity> buildDocumentEntity(String sampleEntityName) throws ArangoException {
        SampleDocumentEntity sampleDocumentEntity = new SampleDocumentEntity(sampleEntityName);
        if (!arangoDriver.exists(fromEntityColl, sampleDocumentEntity.getKey())) {
            DocumentEntity<SampleDocumentEntity> documentEntity = arangoDriver.graphCreateVertex(graphName, fromEntityColl,
                    sampleDocumentEntity, true);
            return documentEntity;
        }
        logger.info(Key.MESSAGE, "AlreadyExits : " + sampleEntityName);
        return arangoDriver.getDocument(fromEntityColl, sampleDocumentEntity.getKey(), SampleDocumentEntity.class);

    }

}
