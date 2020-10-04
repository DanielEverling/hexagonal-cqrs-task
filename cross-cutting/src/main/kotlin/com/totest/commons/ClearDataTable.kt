package com.totest.commons

import java.sql.Connection
import java.sql.DriverManager
import java.util.Properties

object ClearDataTable {

    fun clearAllTables() {
        val connection = createConnection()
        connection.autoCommit = false
        val query = connection.createStatement()
        CommandsToTruncate.values().forEach {
            query.execute(it.commandSql)
        }
        connection.commit()
        connection.close()
    }

    private fun createConnection(): Connection {
        val props = Properties()
        val baseUrl = getEnv("DB_HOST", "jdbc:postgresql://localhost:5432/physys")
        props.setProperty("user", "postgres")
        props.setProperty("password", "postgres")
        return DriverManager.getConnection(baseUrl, props)
    }

    enum class CommandsToTruncate(val commandSql: String) {
        TRUNCATE_CANCELEDPERSON("truncate person.canceled_person cascade"),
        TRUNCATE_CLINICALCAREADDRESS("truncate person.clinical_care_address cascade"),
        TRUNCATE_PERSON("truncate person.person cascade"),
        TRUNCATE_DOCTOR("truncate person.doctor cascade")
    }

}
