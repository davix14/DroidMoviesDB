package com.example.droidmoviesdb.domain

import android.util.Log
import com.example.droidmoviesdb.domain.repos.AccountRepository

class RepoFactory {

    private val repoMap = HashMap<Class<*>, Any>()


    fun accountRepository(): AccountRepository {
        return getRepo(AccountRepository::class.java)
    }

    private fun <T : Any> getRepo(clas: Class<T>): T {
        if (!repoMap.contains(clas)){
            try {
                repoMap[clas] = clas.getConstructor().newInstance()
            } catch (e: Exception){
                Log.e(RepoFactory::class.java.simpleName, "Get repo ${clas.simpleName} failed with error: ${e.stackTrace}")
            }
        }
        return repoMap[clas] as T
    }
}