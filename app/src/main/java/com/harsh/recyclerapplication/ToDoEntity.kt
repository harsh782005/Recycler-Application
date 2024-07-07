package com.harsh.recyclerapplication

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = ToDoEntity::class,
            parentColumns = ["id"],
            childColumns = ["taskId"],
            onDelete = CASCADE
        )
    ]
)
data class ToDoEntity(
    @PrimaryKey
    var id: Int = 0,
    var taskId: Int = 0,
    var todo: String? = null,
    var isCompleted: Boolean? = false
)
