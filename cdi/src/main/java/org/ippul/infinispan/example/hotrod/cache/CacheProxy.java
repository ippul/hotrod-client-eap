package org.ippul.infinispan.example.hotrod.cache;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.Search;
import org.infinispan.commons.api.CacheContainerAdmin;
import org.infinispan.commons.configuration.StringConfiguration;
import org.infinispan.query.dsl.QueryFactory;
import org.ippul.infinispan.example.hotrod.HotRodClientBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class CacheProxy<E> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheProxy.class.getName());

    @Inject
    private HotRodClientBean hotrodClient;

    protected RemoteCache<String, E> indexedCache;

    protected QueryFactory queryFactory;

    @PostConstruct
    public void init(){
        LOGGER.info("Initialise of {} started [{}]", this.getClass().getName(), this);
        if(!hotrodClient.getRemoteCacheManager().getCacheNames().contains(getCacheName())) {
            indexedCache = hotrodClient //
                    .getRemoteCacheManager() //
                    .administration() //
                    .withFlags(CacheContainerAdmin.AdminFlag.VOLATILE) //
                    .getOrCreateCache(getCacheName(), new StringConfiguration(buildCacheDefinition()));
        } else {
            indexedCache = hotrodClient.getRemoteCacheManager().getCache(getCacheName());
        }
        queryFactory = Search.getQueryFactory(indexedCache);
        LOGGER.info("Initialise of {} completed [{}]", this.getClass().getName(), this);
    }

    public abstract String getProtoName();

    public abstract String getCacheName();

    protected abstract void preload(Integer entryToLoad);

    public E getByKey(String key) {
        return indexedCache.get(key);
    }

    public List<E> getAll(){
        return indexedCache.values().stream().collect(Collectors.toList());
    }

    public void put(E entry){
        indexedCache.put(UUID.randomUUID().toString(), entry);
    }

    protected InputStream loadDummyData(){
        return getClass().getClassLoader().getResourceAsStream("./dummy-data.json");
    }

    private String buildCacheDefinition(){
        return "<local-cache name=\"" + getCacheName() + "\">\n" //
                + "\t\t<encoding media-type=\"application/x-protostream\"/>\n" //
                + "\t\t<indexing>\n" //
                + "\t\t\t\t<indexed-entities>\n" //
                + "\t\t\t\t\t\t<indexed-entity>" + getProtoName() +"</indexed-entity>\n" //
                + "\t\t\t\t</indexed-entities>\n" //
                + "\t\t</indexing>\n" //
                + "</local-cache>";
    }
}
