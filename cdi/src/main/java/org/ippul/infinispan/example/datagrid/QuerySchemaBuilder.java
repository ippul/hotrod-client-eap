package org.ippul.infinispan.example.datagrid;

import org.infinispan.protostream.SerializationContextInitializer;
import org.infinispan.protostream.annotations.AutoProtoSchemaBuilder;
import org.ippul.infinispan.example.model.*;

@AutoProtoSchemaBuilder(
        schemaFilePath = "protofiles",
        schemaFileName = "custom-java-types.proto",
        includeClasses = {
                ObjectStoredInCache0.class,
                ObjectStoredInCache1.class,
                ObjectStoredInCache2.class,
                ObjectStoredInCache3.class,
                ObjectStoredInCache4.class,
                ObjectStoredInCache5.class,
                ObjectStoredInCache6.class,
                ObjectStoredInCache7.class,
                ObjectStoredInCache8.class,
                ObjectStoredInCache9.class
        })
public interface QuerySchemaBuilder extends SerializationContextInitializer {
}
