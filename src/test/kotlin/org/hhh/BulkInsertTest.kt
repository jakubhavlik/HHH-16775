package org.hhh

import jakarta.persistence.EntityManagerFactory
import jakarta.persistence.EntityTransaction
import org.hibernate.cfg.AvailableSettings.AUTOCOMMIT
import org.hibernate.cfg.AvailableSettings.DIALECT
import org.hibernate.cfg.AvailableSettings.GENERATE_STATISTICS
import org.hibernate.cfg.AvailableSettings.HBM2DDL_AUTO
import org.hibernate.cfg.AvailableSettings.JAKARTA_JDBC_DRIVER
import org.hibernate.cfg.AvailableSettings.JAKARTA_JDBC_PASSWORD
import org.hibernate.cfg.AvailableSettings.JAKARTA_JDBC_URL
import org.hibernate.cfg.AvailableSettings.JAKARTA_JDBC_USER
import org.hibernate.cfg.AvailableSettings.QUERY_STARTUP_CHECKING
import org.hibernate.cfg.AvailableSettings.SHOW_SQL
import org.hibernate.cfg.AvailableSettings.STATEMENT_BATCH_SIZE
import org.hibernate.cfg.AvailableSettings.USE_QUERY_CACHE
import org.hibernate.cfg.AvailableSettings.USE_SECOND_LEVEL_CACHE
import org.hibernate.cfg.AvailableSettings.USE_STRUCTURED_CACHE
import org.hibernate.dialect.SQLServerDialect
import org.hibernate.jpa.HibernatePersistenceProvider
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.Properties


class BulkInsertTest {

    private lateinit var emf: EntityManagerFactory

    @BeforeEach
    fun init() {
        val properties = Properties()
        properties.putAll(config())
        emf = HibernatePersistenceProvider().createContainerEntityManagerFactory(PersistenceUnitInfoImpl("hhh-test", listOf(TestEntity::class.qualifiedName!!), properties), config())
    }

    @Test
    fun insertBulk() {
        //setup
        val em = emf.createEntityManager()
        val tx: EntityTransaction = em.transaction

        // insert
        tx.begin()
        val ids = 1..10L
        val entities = ids.map { TestEntity(it, "test") }
        entities.forEach { em.persist(it) }
        tx.commit()

        // cleanup
        tx.begin()
        ids.map { em.find(TestEntity::class.java, it) }.forEach(em::remove)
        tx.commit()

        em.close()
    }

    companion object {

        private fun config(): Map<String, Any> = mapOf<String, Any>(
            JAKARTA_JDBC_DRIVER to "com.microsoft.sqlserver.jdbc.SQLServerDriver",
            JAKARTA_JDBC_URL to "jdbc:sqlserver://localhost:1433;database=hhh_test;sendStringParametersAsUnicode=false;trustServerCertificate=true;useBulkCopyForBatchInsert=true;",
            JAKARTA_JDBC_USER to "hhh_user",
            JAKARTA_JDBC_PASSWORD to "guessme",
            DIALECT to SQLServerDialect::class.java,
            HBM2DDL_AUTO to "create-drop",
            SHOW_SQL to "true",
            QUERY_STARTUP_CHECKING to "false",
            GENERATE_STATISTICS to "false",
            USE_SECOND_LEVEL_CACHE to "false",
            USE_QUERY_CACHE to "false",
            USE_STRUCTURED_CACHE to "false",
            STATEMENT_BATCH_SIZE to "20",
            AUTOCOMMIT to "false",
            "hibernate.hikari.minimumIdle" to "5",
            "hibernate.hikari.maximumPoolSize" to "15",
            "hibernate.hikari.idleTimeout" to "30000",
            "hibernate.default_batch_fetch_size" to "30",
        )
    }

}
