package com.crazynerve.util;

import com.crazynerve.models.Person;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class Serializer
{
    public byte[] serializeJsonEncoder( Person person )
    {
        DatumWriter<Person> writer = new SpecificDatumWriter<>( Person.class );
        byte[] data = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Encoder jsonEncoder = null;
        try {
            jsonEncoder = EncoderFactory.get().jsonEncoder( Person.getClassSchema(), byteArrayOutputStream );
            writer.write( person, jsonEncoder );
            jsonEncoder.flush();
            data = byteArrayOutputStream.toByteArray();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        return data;
    }

    public byte[] serializeBinaryEncoder( Person person )
    {
        DatumWriter<Person> writer = new SpecificDatumWriter<>( Person.class );
        byte[] data = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Encoder binaryEncoder = null;
        try {
            binaryEncoder = EncoderFactory.get().binaryEncoder( byteArrayOutputStream, null );
            writer.write( person, binaryEncoder );
            binaryEncoder.flush();
            data = byteArrayOutputStream.toByteArray();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        return data;
    }
}
