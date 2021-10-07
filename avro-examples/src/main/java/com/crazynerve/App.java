package com.crazynerve;

import com.crazynerve.util.SchemaGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;


/**
 * Hello world!
 *
 */
public class App
{
    private static final Logger LOGGER = Logger.getLogger( App.class.getName() );


    public static void main( String[] args )
    {
        LOGGER.info( () -> "Avro example" );
        App app = new App();
        // generating schema
        app.writeSchemaJson();
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
            String beautifiedJson = objectMapper.writeValueAsString(treeNode);
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
}
