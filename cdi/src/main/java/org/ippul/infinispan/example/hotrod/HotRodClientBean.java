package org.ippul.infinispan.example.hotrod;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ClientIntelligence;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.impl.ConfigurationProperties;
import org.infinispan.protostream.GeneratedSchema;
import org.ippul.infinispan.example.datagrid.QuerySchemaBuilderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import static org.infinispan.query.remote.client.ProtobufMetadataManagerConstants.PROTOBUF_METADATA_CACHE_NAME;

@ApplicationScoped
public class HotRodClientBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(HotRodClientBean.class.getName());

    public static final String USER = System.getenv("HOTROD_USER") == null ? "admin" : System.getenv("HOTROD_USER");

    public static final String PASSWORD = System.getenv("HOTROD_PASSWORD") == null ? "password" : System.getenv("HOTROD_PASSWORD");
    
    public static final String HOST = System.getenv("HOTROD_URL") == null ? "127.0.0.1" : System.getenv("HOTROD_URL");

    private RemoteCacheManager remoteCacheManager;

    @PostConstruct
    public void init() {
        LOGGER.info("Initialise of {} started [{}]", this.getClass().getName(), this);
        remoteCacheManager = createRemoteCacheManager();
        LOGGER.info("Initialise of {} completed [{}]", this.getClass().getName(), this);
    }

    public RemoteCacheManager getRemoteCacheManager(){
        return remoteCacheManager;
    }

    private RemoteCacheManager createRemoteCacheManager() {
        final ConfigurationBuilder builder = new ConfigurationBuilder();
        // create client
        builder.addServer()
                    .host(HOST)
                    .port(ConfigurationProperties.DEFAULT_HOTROD_PORT)
                .security()
                    .authentication()
                        .username(USER)
                        .password(PASSWORD)
                .clientIntelligence(ClientIntelligence.BASIC)
                .marshaller(org.infinispan.commons.marshall.ProtoStreamMarshaller.class)
                .addContextInitializer(new QuerySchemaBuilderImpl());
        final RemoteCacheManager cacheManager = new RemoteCacheManager(builder.build());
        final RemoteCache<String, String> metadataCache = cacheManager.getCache(PROTOBUF_METADATA_CACHE_NAME);
        final GeneratedSchema querySchemaBuilder = new QuerySchemaBuilderImpl();
        metadataCache.put(querySchemaBuilder.getProtoFileName(), querySchemaBuilder.getProtoFile());
        return cacheManager;
    }

}
