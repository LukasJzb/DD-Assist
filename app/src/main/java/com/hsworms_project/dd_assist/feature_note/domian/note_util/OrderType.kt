package com.hsworms_project.dd_assist.feature_note.domian.note_util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}