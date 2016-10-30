package com.example;

import com.example.Builders.PasoBuilder;
import com.example.Builders.RutinaBuilder;
import com.example.Builders.SecurityTokenBuilder;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class Generator {

    private static final String PATH = "../app/src/main/java";
    private static final String DOMAIN_PACKAGE = "com.fitnesstime.fitnesstime.Builders";
    private static final String DAO_PACKAGE = "com.fitnesstime.fitnesstime.Builders";

    private static int SCHEMA_VERSION = 1;

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(SCHEMA_VERSION, DOMAIN_PACKAGE);
        Builder.setSchema(schema);

        Entity paso = PasoBuilder.build();

        schema.setDefaultJavaPackageDao(DAO_PACKAGE);

        new DaoGenerator().generateAll(schema, PATH);
    }
}
