package com.example.roomsiswa.Repository

import com.example.roomsiswa.Data.Siswa
import com.example.roomsiswa.Data.SiswaDao
import kotlinx.coroutines.flow.Flow

class OfflinerepositoriSiswa(private val siswaDao: SiswaDao):RepositorySiswa {

    override fun getAllSiswa(): Flow<List<Siswa>> = siswaDao.getAllSiswa()

    override fun getSiswaStream(id: Int): Flow<Siswa?> = siswaDao.getSiswa(id)

    override suspend fun insertSiswa(siswa: Siswa) = siswaDao.insert(siswa)

    override suspend fun deleteSiswa(siswa: Siswa) = siswaDao.delete(siswa)

    override suspend fun updateSiswa(siswa: Siswa) = siswaDao.update(siswa)

}

