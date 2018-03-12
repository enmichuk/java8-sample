package com.enmichuk.json.jackson.typeserialization;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SerializerBaseOnType {
    public static void main(String[] args) throws Exception {
        // Foo
        Foo foo = new Foo();
        CustomObject customObject = new CustomObject();
        customObject.id = 20l;
        customObject.fizz = "Example";
        Data data = new Data();
        data.object = customObject;
        foo.data = data;
        System.out.println("Foo: " + foo);

        // Bar
        Bar bar = new Bar();
        bar.data = "A String in Bar";

        ObjectMapper om = new ObjectMapper();

        // Test Foo:
        String foojson = om.writeValueAsString(foo);
        System.out.println(foojson);
        AbstractData fooDeserialised = om.readValue(foojson, AbstractData.class);
        System.out.println(fooDeserialised);

        // Test Bar:
        String barjson = om.writeValueAsString(bar);
        System.out.println(barjson);
        AbstractData barDeserialised = om.readValue(barjson, AbstractData.class);
        System.out.println(barDeserialised);
    }
}
