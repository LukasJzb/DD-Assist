package com.hsworms_project.dd_assist.classes

import androidx.room.*

@Entity
data class Note (
    val titel: String,
    val inhalt: String,
    val time: Long,
    @PrimaryKey val id: Int? = null
){

}

class InvalidNoteExpectation(message: String): Exception(message)