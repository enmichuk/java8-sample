package com.enmichuk.json.jackson.typeserialization;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Foo.class, name = "Foo"),
        @JsonSubTypes.Type(value = Bar.class, name = "Bar")
})
public abstract class AbstractData {}

class Foo extends AbstractData {
    public Data data;
    @Override
    public String toString() {
        return "Foo[" + super.toString() + "; data=" + data + ']';
    }
}

class Bar extends AbstractData {
    public String data;
    public String toString() {
        return "Bar[" + super.toString() + "; data=" + data + ']';
    }
}


class Data {
    public CustomObject object;
    @Override
    public String toString() {
        return "Data[object=" + object + ']';
    }
}

class CustomObject {
    public long id;
    public String fizz;
    @Override
    public String toString() {
        return "CustomObject[id=" + id + "; fizz=" + fizz + ']';
    }
}
