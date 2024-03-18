package com.example.productsdemo.app.di

import android.content.Context
import androidx.room.Room
import com.example.productsdemo.app.utils.ConnectionManager
import com.example.productsdemo.app.utils.Constant
import com.example.moviesdemo.data.local.ProductDao
import com.example.productsdemo.data.local.ProductDatabase
import com.example.productsdemo.data.remote.ProductService
import com.example.productsdemo.data.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MovieModule {
    @Singleton
    @Provides
    fun providesRetrofit() = Retrofit.Builder()
        .baseUrl(Constant.baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit) = retrofit.create(ProductService::class.java)

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(appContext, ProductDatabase::class.java, "productsDB")
            .allowMainThreadQueries()
            .build()

    @Singleton
    @Provides
    fun provideDao(productDatabase: ProductDatabase) = productDatabase.productDao()

    @Singleton
    @Provides
    fun provideRepository(service: ProductService, dao: ProductDao) = ProductRepository(service, dao)

    @Singleton
    @Provides
    fun provideNetwork(@ApplicationContext context: Context) =
        ConnectionManager(context)
}