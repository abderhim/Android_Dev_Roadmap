package com.example.learnapp.di

import android.content.Context
import androidx.room.Room
import com.example.learnapp.data.local.AppDatabase
import com.example.learnapp.data.local.dao.ProgressDao
import com.example.learnapp.data.repository.LearningRepositoryImpl
import com.example.learnapp.domain.repository.LearningRepository
import com.example.learnapp.domain.usecase.ClearProgressUseCase
import com.example.learnapp.domain.usecase.GetAllTopicsUseCase
import com.example.learnapp.domain.usecase.GetLessonByIdUseCase
import com.example.learnapp.domain.usecase.GetTopicByIdUseCase
import com.example.learnapp.domain.usecase.MarkLessonCompleteUseCase
import com.example.learnapp.domain.usecase.MarkLessonIncompleteUseCase
import com.example.learnapp.domain.usecase.ObserveProgressUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideProgressDao(database: AppDatabase): ProgressDao {
        return database.progressDao()
    }

    @Provides
    @Singleton
    fun provideLearningRepository(
        progressDao: ProgressDao
    ): LearningRepository {
        return LearningRepositoryImpl(progressDao)
    }

    @Provides
    @Singleton
    fun provideGetAllTopicsUseCase(repository: LearningRepository): GetAllTopicsUseCase {
        return GetAllTopicsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetTopicByIdUseCase(repository: LearningRepository): GetTopicByIdUseCase {
        return GetTopicByIdUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetLessonByIdUseCase(repository: LearningRepository): GetLessonByIdUseCase {
        return GetLessonByIdUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideObserveProgressUseCase(repository: LearningRepository): ObserveProgressUseCase {
        return ObserveProgressUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideMarkLessonCompleteUseCase(repository: LearningRepository): MarkLessonCompleteUseCase {
        return MarkLessonCompleteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideMarkLessonIncompleteUseCase(repository: LearningRepository): MarkLessonIncompleteUseCase {
        return MarkLessonIncompleteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideClearProgressUseCase(repository: LearningRepository): ClearProgressUseCase {
        return ClearProgressUseCase(repository)
    }
}
