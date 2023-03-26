package org.ippul.infinispan.example.hotrod.cache.managers;

import org.ippul.infinispan.example.hotrod.cache.CacheProxy;
import org.ippul.infinispan.example.model.ObjectStoredInCache0;
import org.ippul.infinispan.example.model.ObjectStoredInCache5;

import javax.enterprise.context.ApplicationScoped;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CacheManager5 extends CacheProxy<ObjectStoredInCache5> {

    @Override
    public String getProtoName() {
        return "ObjectStoredInCache5";
    }

    @Override
    public String getCacheName() {
        return getClass().getSimpleName().toUpperCase();
    }

    @Override
    protected void preload(Integer entryToLoad) {
        try(Jsonb jsonb = JsonbBuilder.create()) {
            List<ObjectStoredInCache5> data = jsonb.fromJson(loadDummyData(), new ArrayList<ObjectStoredInCache5>(){}.getClass().getGenericSuperclass());
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