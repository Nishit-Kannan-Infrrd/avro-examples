package com.crazynerve.util;

import com.crazynerve.models.Person;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;

import java.io.IOException;


public class DeSerializer
{
    public Person deserializeJsonEncoder(byte[] data){
        DatumReader<Person> reader = new SpecificDatumReader<>(Person.class);
        Decoder decoder = null;
        try {
            decoder = DecoderFactory.get().jsonDecoder(Person.getClassSchema(), new String(data));
            return reader.read( null, decoder );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        return null;
    }

    public Person deserializeBinaryEncoder(byte[] data){
        DatumReader<Person> reader = new SpecificDatumReader<>(Person.class);
        Decoder decoder = null;
        try {
            decoder = DecoderFactory.get().binaryDecoder(data, null);
            return reader.read( null, decoder );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        return null;
    }
}
