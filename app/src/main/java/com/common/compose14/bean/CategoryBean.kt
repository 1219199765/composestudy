package com.common.compose14.bean

data class CategoryBean(
    val `data`: List<Data>?=null,
    val description: Any?=null,
    val msg: Any?=null,
    val status: Int?=null,
    val success: Boolean?=null
)

{
    data class Data(
        val categoryLcon: String,
        val childNum: Any,
        val children: List<Children>,
        val id: Int,
        val isRotation: String,
        val locn: String,
        val name: String,
        val remarks: String,
        val show: String,
        val type: String
    )

    data class Children(
        val childNum: Any,
        val id: Int,
        val name: String,
        val remarks: String,
        val type: String
    )
}