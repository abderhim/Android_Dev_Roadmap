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
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase =
        Room
            .databaseBuilder(
                context,
                AppDatabase::class.java,
                AppDatabase.DATABASE_NAME,
            ).build()

    @Provides
    fun provideProgressDao(database: AppDatabase): ProgressDao = database.progressDao()

    @Provides
    @Singleton
    fun provideLearningRepository(progressDao: ProgressDao): LearningRepository = LearningRepositoryImpl(progressDao)

    @Provides
    @Singleton
    fun provideGetAllTopicsUseCase(repository: LearningRepository): GetAllTopicsUseCase = GetAllTopicsUseCase(repository)

    @Provides
    @Singleton
    fun provideGetTopicByIdUseCase(repository: LearningRepository): GetTopicByIdUseCase = GetTopicByIdUseCase(repository)

    @Provides
    @Singleton
    fun provideGetLessonByIdUseCase(repository: LearningRepository): GetLessonByIdUseCase = GetLessonByIdUseCase(repository)

    @Provides
    @Singleton
    fun provideObserveProgressUseCase(repository: LearningRepository): ObserveProgressUseCase = ObserveProgressUseCase(repository)

    @Provides
    @Singleton
    fun provideMarkLessonCompleteUseCase(repository: LearningRepository): MarkLessonCompleteUseCase = MarkLessonCompleteUseCase(repository)

    @Provides
    @Singleton
    fun provideMarkLessonIncompleteUseCase(repository: LearningRepository): MarkLessonIncompleteUseCase =
        MarkLessonIncompleteUseCase(repository)

    @Provides
    @Singleton
    fun provideClearProgressUseCase(repository: LearningRepository): ClearProgressUseCase = ClearProgressUseCase(repository)
}
