package com.harsh.recyclerapplication

interface TaskClickInterface {
    fun updateTask(position: Int)
    fun deleteTask(position: Int)
    fun itemClick(position: Int)
}