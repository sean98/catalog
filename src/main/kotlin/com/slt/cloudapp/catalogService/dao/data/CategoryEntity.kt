package com.slt.cloudapp.catalogService.dao.data

import javax.persistence.*

@Entity
@Table(name = "CATEGORIES")
 data class CategoryEntity(
        @Id
        var name: String ="",
        var description: String? = null,
        var parentCategory: String? = null
)

