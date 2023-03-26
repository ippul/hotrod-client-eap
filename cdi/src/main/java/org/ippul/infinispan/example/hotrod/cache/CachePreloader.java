package org.ippul.infinispan.example.hotrod.cache;

import org.ippul.infinispan.example.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@ApplicationScoped
public class CachePreloader {

    private static final Logger LOGGER = LoggerFactory.getLogger(CachePreloader.class.getName());

    private static final Integer PREFILL_SIZE = System.getenv("ENTRY_TO_PREFILL") == null ? 10_000 : Integer.getInteger(System.getenv("ENTRY_TO_PREFILL"));

    private final Executor executor = Executors.newFixedThreadPool(4);

    @Inject
    private CacheProxy<ObjectStoredInCache0> cacheProxy0;

    @Inject
    private CacheProxy<ObjectStoredInCache1> cacheProxy1;

    @Inject
    private CacheProxy<ObjectStoredInCache2> cacheProxy2;

    @Inject
    private CacheProxy<ObjectStoredInCache3> cacheProxy3;

    @Inject
    private CacheProxy<ObjectStoredInCache4> cacheProxy4;

    @Inject
    private CacheProxy<ObjectStoredInCache5> cacheProxy5;

    @Inject
    private CacheProxy<ObjectStoredInCache6> cacheProxy6;

    @Inject
    private CacheProxy<ObjectStoredInCache7> cacheProxy7;

    @Inject
    private CacheProxy<ObjectStoredInCache8> cacheProxy8;

    @Inject
    private CacheProxy<ObjectStoredInCache9> cacheProxy9;

    public void preloadAllCaches(@Observes @Initialized(ApplicationScoped.class) Object init){
        try {
            CompletableFuture cf0 = createCompletableFuture(cacheProxy0);
            CompletableFuture cf1 = createCompletableFuture(cacheProxy1);
            CompletableFuture cf2 = createCompletableFuture(cacheProxy2);
            CompletableFuture cf3 = createCompletableFuture(cacheProxy3);
            CompletableFuture cf4 = createCompletableFuture(cacheProxy4);
            CompletableFuture cf5 = createCompletableFuture(cacheProxy5);
            CompletableFuture cf6 = createCompletableFuture(cacheProxy6);
            CompletableFuture cf7 = createCompletableFuture(cacheProxy7);
            CompletableFuture cf8 = createCompletableFuture(cacheProxy8);
            CompletableFuture cf9 = createCompletableFuture(cacheProxy9);
            CompletableFuture.allOf(cf0,cf1,cf2,cf3,cf4,cf5,cf6,cf7,cf8,cf9).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private CompletableFuture createCompletableFuture(CacheProxy<?> cacheProxy){
        return CompletableFuture.runAsync(() -> {
            LOGGER.info("Start preload cache {}", cacheProxy.getCacheName());
            cacheProxy.preload(PREFILL_SIZE);
            LOGGER.info("End preload cache {}", cacheProxy.getCacheName());
        }, executor);
    }
}
