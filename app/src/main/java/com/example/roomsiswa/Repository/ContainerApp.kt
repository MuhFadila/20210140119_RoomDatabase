package com.example.roomsiswa.Repository

import android.content.Context
import com.example.roomsiswa.Data.DatabaseSiswa

interface ContainerApp {
    val repositorySiswa : RepositorySiswa
}

class ContainerDataApp(private val context: Context): ContainerApp{
    override val repositorySiswa: RepositorySiswa by lazy{
        OfflinerepositoriSiswa(DatabaseSiswa.getDatabase(context).siswaDao())
    }
}