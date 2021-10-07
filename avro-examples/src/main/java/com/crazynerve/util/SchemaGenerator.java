package com.crazynerve.util;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;


public class SchemaGenerator
{
    public Schema generateSchema(){
        Schema addressSchema = SchemaBuilder.record( "Address" )
            .namespace( "com.crazynerve.models" )
            .fields()
            .optionalString( "street" )
            .optionalString( "zipcode" )
            .endRecord();
        return SchemaBuilder.record( "Person" )
            .namespace( "com.crazynerve.models" )
            .fields().requiredString( "name" )
            .requiredInt( "age" )
            .name( "address" )
            .type(addressSchema)
            .noDefault()
            .name( "phoneNumbers" )
            .type()
            .array()
            .items()
            .stringType()
            .noDefault()
            .name( "sex" )
            .type()
            .enumeration( "Sex" )
            .symbols( "MALE", "FEMALE" )
            .noDefault()
            .endRecord();

    }
}
