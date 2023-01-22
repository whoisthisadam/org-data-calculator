package com.chumachenko.orgsinfo.config;

import org.flywaydb.core.Flyway;

public class FlywayMigrationConfig {


    public static void initialize() {
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:postgresql://localhost:5432/javafxtest",
                        "postgres",
                        "root")
                .baselineOnMigrate(true)
                .baselineDescription("Flyway Scripts")
                .schemas("orgsinfo")
                .load();
        flyway.migrate();
    }

}
