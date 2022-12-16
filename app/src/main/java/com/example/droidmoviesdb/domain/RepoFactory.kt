package com.example.droidmoviesdb.domain

import android.util.Log
import com.example.droidmoviesdb.domain.repos.AccountRepository
import com.example.droidmoviesdb.domain.repos.SearchRepository

object RepoFactory {

    private val repoMap = HashMap<Class<*>, Any>()

    fun getAccountRepository(): AccountRepository {
        return getRepo(AccountRepository::class.java)
    }

    fun getSearchRepository(): SearchRepository {
        return getRepo(SearchRepository::class.java)
    }

    private fun <T : Any> getRepo(clas: Class<T>): T {
        if (!repoMap.contains(clas)) {
            try {
                repoMap[clas] = clas.getConstructor().newInstance()
            } catch (e: Exception) {
                Log.e(
                    RepoFactory::class.java.simpleName,
                    "Get repo ${clas.simpleName} failed with error: ${e.stackTrace}"
                )
            }
        }
        return repoMap[clas] as T
    }
}