package com.crazynerve;

import com.crazynerve.models.Address;
import com.crazynerve.models.Person;
import com.crazynerve.models.Sex;
import com.crazynerve.util.DeSerializer;
import com.crazynerve.util.SchemaGenerator;
import com.crazynerve.util.Serializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.Logger;


/**
 * Hello world!
 *
 */
public class App
{
    private static final Logger LOGGER = Logger.getLogger( App.class.getName() );


    public static void main( String[] args ) throws IOException
    {
        LOGGER.info( () -> "Avro example" );
        App app = new App();
        // generating schema
        app.writeSchemaJson();
        // serialize
        app.serializePerson();
        // de-serialize
        app.deserializePerson();
    }


    public void writeSchemaJson()
    {
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        String jsonSchema = schemaGenerator.generateSchema().toString();
        LOGGER.info( () -> jsonSchema );
        try {
            // beautify json
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure( SerializationFeature.INDENT_OUTPUT, true );
            JsonNode treeNode = objectMapper.readTree( jsonSchema );
            String beautifiedJson = objectMapper.writeValueAsString( treeNode );
            File file = new File( "./src/main/resources/avro/person-schema.avsc" );
            LOGGER.info( () -> "File path " + file.getAbsolutePath() );
            if ( !file.exists() && file.createNewFile() ) {
                FileOutputStream fileOutputStream = new FileOutputStream( file );
                fileOutputStream.write( beautifiedJson.getBytes() );
                fileOutputStream.close();
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        }

    }


    public void serializePerson() throws IOException
    {
        Person person = new Person( "Nishit", 39, new Address( "Bangalore", "560062" ), Arrays.asList( "123", "456", "789" ),
            Sex.MALE );
        Serializer serializer = new Serializer();
        // serialize in json
        File jsonEncodedFile = new File( "./src/main/resources/serializedfiles/personjson.data" );
        if ( !jsonEncodedFile.exists() && jsonEncodedFile.createNewFile() ) {
            try ( FileOutputStream outputStream = new FileOutputStream( jsonEncodedFile ) ) {
                outputStream.write( serializer.serializeJsonEncoder( person ) );
            }
        }
        // serialize in binary
        File binaryEncodedFile = new File( "./src/main/resources/serializedfiles/personbinary.data" );
        if ( !binaryEncodedFile.exists() && binaryEncodedFile.createNewFile() ) {
            try ( FileOutputStream outputStream = new FileOutputStream( binaryEncodedFile ) ) {
                outputStream.write( serializer.serializeBinaryEncoder( person ) );
            }
        }
    }

    public void deserializePerson() throws IOException
    {
        DeSerializer deSerializer = new DeSerializer();
        // deserialize json encoded file
        Path jsonPath = Paths.get( "./src/main/resources/serializedfiles/personjson.data" );
        byte[] jsonEncodedData = Files.readAllBytes(jsonPath);
        Person jsonEncodedPerson = deSerializer.deserializeJsonEncoder( jsonEncodedData );
        LOGGER.info( () -> "Person " + jsonEncodedPerson.toString() );

        // deserialize binary encoded file
        Path binaryPath = Paths.get( "./src/main/resources/serializedfiles/personbinary.data" );
        byte[] binaryEncodedData = Files.readAllBytes(binaryPath);
        Person binaryEncodedPerson = deSerializer.deserializeBinaryEncoder( binaryEncodedData );
        LOGGER.info( () -> "Person " + binaryEncodedPerson.toString() );
    }

}
