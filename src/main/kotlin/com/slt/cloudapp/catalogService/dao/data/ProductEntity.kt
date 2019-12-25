package com.slt.cloudapp.catalogService.dao.data

import org.hibernate.annotations.GenericGenerator
import javax.persistence.*

@Entity
@Table(name = "PRODUCTS")
data class ProductEntity(
        @Id
        @GeneratedValue(generator = "uuid")
        @GenericGenerator(name =  "uuid", strategy = "uuid")
        var id: String? = null,
        var name: String ="",
        var price: Int =0,
        var image: String = "",
        var categoryId: String=""
)