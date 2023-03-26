package org.ippul.infinispan.example.hotrod.cache.managers;

import org.ippul.infinispan.example.hotrod.cache.CacheProxy;
import org.ippul.infinispan.example.model.ObjectStoredInCache0;
import org.ippul.infinispan.example.model.ObjectStoredInCache7;

import javax.enterprise.context.ApplicationScoped;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CacheManager7 extends CacheProxy<ObjectStoredInCache7> {

    @Override
    public String getProtoName() {
        return "ObjectStoredInCache7";
    }

    @Override
    public String getCacheName() {
        return getClass().getSimpleName().toUpperCase();
    }

    @Override
    protected void preload(Integer entryToLoad) {
        try(Jsonb jsonb = JsonbBuilder.create()) {
            List<ObjectStoredInCache7> data = jsonb.fromJson(loadDummyData(), new ArrayList<ObjectStoredInCache7> (){}.getClass().getGenericSuperclass());
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