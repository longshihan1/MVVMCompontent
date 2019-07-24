package com.longshihan.mvvmlibrary.di.component

import android.app.Application
import com.google.gson.Gson
import com.longshihan.mvvmlibrary.http.IRepositoryManager
import okhttp3.OkHttpClient
import java.util.concurrent.ExecutorService
import com.longshihan.mvvmlibrary.di.module.ClientModule
import com.longshihan.mvvmlibrary.di.module.AppModule
import com.longshihan.mvvmlibrary.di.module.GlobalConfigModule
import com.longshihan.mvvmlibrary.http.RepositoryManager
import retrofit2.Retrofit


/**
 * Created by LONGHE001.
 * @time 2019/7/24 0024
 * @des
 * @function
 */
class AppComponentImpl(
    appModule1: AppModule,
    clientModule1: ClientModule,
    globalConfigModule1: GlobalConfigModule,
    application1: Application
) :
    AppComponent {
    private val appModule: AppModule = appModule1
    private val clientModule: ClientModule = clientModule1
    private val globalConfigModule: GlobalConfigModule = globalConfigModule1
    private val application: Application = application1
    lateinit var repositoryManager: RepositoryManager
    lateinit var okHttpClient: OkHttpClient

    init {
        okHttpClient = clientModule.provideClient(
            application,
            globalConfigModule.provideOkhttpConfiguration(), globalConfigModule.provideInterceptors()
        )
        val retrofit = clientModule.provideRetrofit(
            application,
            globalConfigModule.provideRetrofitConfiguration(),
            okHttpClient, gson()
        )
        repositoryManager = RepositoryManager(retrofit)
    }

    override fun application(): Application {
        return application
    }

    override fun okHttpClient(): OkHttpClient {
        return okHttpClient
    }

    override fun gson(): Gson = appModule.provideGson(application, globalConfigModule.provideGsonConfiguration())

    override fun repositoryManager(): IRepositoryManager {
        return repositoryManager
    }

    override fun executorService(): ExecutorService = globalConfigModule.provideExecutorService()

}