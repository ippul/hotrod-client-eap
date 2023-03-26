package org.ippul.infinispan.example.model;

import org.infinispan.protostream.annotations.ProtoDoc;
import org.infinispan.protostream.annotations.ProtoField;
import org.infinispan.protostream.annotations.ProtoName;

@ProtoName("ObjectStoredInCache4")
@ProtoDoc("@Indexed")
public class ObjectStoredInCache4 {
    private String property1;

    private String property2;

    @ProtoField(number = 1)
    @ProtoDoc("@Field(index=Index.YES, analyze = Analyze.NO, store = Store.YES)")
    public String getProperty1() {
        return property1;
    }

    public void setProperty1(String property1) {
        this.property1 = property1;
    }

    @ProtoField(number = 2)
    @ProtoDoc("@Field(index=Index.YES, analyze = Analyze.NO, store = Store.YES)")
    public String getProperty2() {
        return property2;
    }

    public void setProperty2(String property2) {
        this.property2 = property2;
    }

    @Override
    public String toString() {
        return "ObjectStoredInCache4{" +
                "property1='" + property1 + '\'' +
                ", property2='" + property2 + '\'' +
                '}';
    }
}
