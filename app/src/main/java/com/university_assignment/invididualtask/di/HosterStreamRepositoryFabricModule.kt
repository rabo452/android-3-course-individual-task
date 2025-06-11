package com.university_assignment.invididualtask.di

import com.university_assignment.invididualtask.data.repository.HosterStreamRepository.HosterStreamRepositoryFabric
import com.university_assignment.invididualtask.data.repository.interfaces.IHosterStreamRepositoryFabric
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class HosterStreamRepositoryFabricModule {
    @Binds
    abstract fun bind(impl: HosterStreamRepositoryFabric): IHosterStreamRepositoryFabric
}