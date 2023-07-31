package com.hsworms_project.dd_assist.classes

import androidx.room.*


@Entity
data class Note (
    val titel: String,
    val inhalt: String,
    val time: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
){

}