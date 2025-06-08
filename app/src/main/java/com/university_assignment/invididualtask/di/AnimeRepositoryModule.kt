package com.university_assignment.invididualtask.di

import com.university_assignment.invididualtask.data.repository.AnimeRepository.WebsiteAnimeRepository
import com.university_assignment.invididualtask.data.repository.interfaces.IAnimeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class AnimeRepositoryModule {
    @Binds
    abstract fun bindComponentB(
        impl: WebsiteAnimeRepository
    ): IAnimeRepository
}