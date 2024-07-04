package com.harsh.recyclerapplication

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskDataClass(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var tvTittle: String? = "",
    var tvDescription: String? = "",
    var tvPriority: Int? = 0,
)
