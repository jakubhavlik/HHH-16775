package org.hhh

import jakarta.persistence.SharedCacheMode
import jakarta.persistence.SharedCacheMode.UNSPECIFIED
import jakarta.persistence.ValidationMode
import jakarta.persistence.ValidationMode.AUTO
import jakarta.persistence.spi.ClassTransformer
import jakarta.persistence.spi.PersistenceUnitInfo
import jakarta.persistence.spi.PersistenceUnitTransactionType
import jakarta.persistence.spi.PersistenceUnitTransactionType.JTA
import jakarta.persistence.spi.PersistenceUnitTransactionType.RESOURCE_LOCAL
import org.hibernate.jpa.HibernatePersistenceProvider
import java.net.URL
import java.util.Properties
import javax.sql.DataSource


class PersistenceUnitInfoImpl(
    private val persistenceUnitName: String,
    private val managedClassNames: List<String>,
    private val properties: Properties
) : PersistenceUnitInfo {
    private var transactionType = RESOURCE_LOCAL
    private val mappingFileNames: List<String> = ArrayList()
    private var jtaDataSource: DataSource? = null
    private var nonJtaDataSource: DataSource? = null
    override fun getPersistenceUnitName(): String {
        return persistenceUnitName
    }

    override fun getPersistenceProviderClassName(): String {
        return HibernatePersistenceProvider::class.java.name
    }

    override fun getTransactionType(): PersistenceUnitTransactionType {
        return transactionType
    }

    override fun getJtaDataSource(): DataSource {
        return jtaDataSource!!
    }

    fun setJtaDataSource(jtaDataSource: DataSource?): PersistenceUnitInfoImpl {
        this.jtaDataSource = jtaDataSource
        nonJtaDataSource = null
        transactionType = JTA
        return this
    }

    override fun getNonJtaDataSource(): DataSource {
        return nonJtaDataSource!!
    }

    fun setNonJtaDataSource(nonJtaDataSource: DataSource?): PersistenceUnitInfoImpl {
        this.nonJtaDataSource = nonJtaDataSource
        jtaDataSource = null
        transactionType = RESOURCE_LOCAL
        return this
    }

    override fun getMappingFileNames(): List<String> {
        return mappingFileNames
    }

    override fun getJarFileUrls(): List<URL> {
        return emptyList()
    }

    override fun getPersistenceUnitRootUrl(): URL? {
        return null
    }

    override fun getManagedClassNames(): List<String> {
        return managedClassNames
    }

    override fun excludeUnlistedClasses(): Boolean {
        return false
    }

    override fun getSharedCacheMode(): SharedCacheMode {
        return UNSPECIFIED
    }

    override fun getValidationMode(): ValidationMode {
        return AUTO
    }

    override fun getProperties(): Properties {
        return properties
    }

    override fun getPersistenceXMLSchemaVersion(): String {
        return JPA_VERSION
    }

    override fun getClassLoader(): ClassLoader {
        return Thread.currentThread().contextClassLoader
    }

    override fun addTransformer(transformer: ClassTransformer) {}
    override fun getNewTempClassLoader(): ClassLoader? {
        return null
    }

    companion object {
        const val JPA_VERSION = "3.1"
    }
}
