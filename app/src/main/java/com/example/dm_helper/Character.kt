package com.example.dm_helper

data class Character(
    val name: String, 
    val initiative: Int, 
    val portrait: Int, 
    val conditions: List<Int>,
    val currentHP: Int,
    val maximumHP: Int
)