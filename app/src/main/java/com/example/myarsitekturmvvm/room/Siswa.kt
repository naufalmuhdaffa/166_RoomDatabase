package com.example.myarsitekturmvvm.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tblSiswa")
data class Siswa(
    @PrimaryKey(true)
    val id : Int = 0,
    val nama : String,
    val alamat : String,
    val telpon : String
)
