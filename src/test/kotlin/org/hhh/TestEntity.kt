package org.hhh

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "test_entity")
data class TestEntity(

    @Id
    val id: Long,
    val name: String,
)
