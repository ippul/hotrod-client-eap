package org.ippul.infinispan.example.hotrod.cache.managers;

import org.ippul.infinispan.example.hotrod.cache.CacheProxy;
import org.ippul.infinispan.example.model.ObjectStoredInCache0;

import javax.enterprise.context.ApplicationScoped;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@ApplicationScoped
public class CacheManager0 extends CacheProxy<ObjectStoredInCache0> {

    @Override
    public String getProtoName() {
        return "ObjectStoredInCache0";
    }

    @Override
    public String getCacheName() {
        return getClass().getSimpleName().toUpperCase();
    }

    @Override
    protected void preload(Integer entryToLoad) {
        try(Jsonb jsonb = JsonbBuilder.create()) {
            List<ObjectStoredInCache0> data = jsonb.fromJson(loadDummyData(), new ArrayList<ObjectStoredInCache0>(){}.getClass().getGenericSuperclass());
            data.stream()
                    .limit(entryToLoad)
                    .forEach( instance -> indexedCache.put(UUID.randomUUID().toString(), instance));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}