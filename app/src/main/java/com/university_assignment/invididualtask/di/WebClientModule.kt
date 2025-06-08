package com.university_assignment.invididualtask.di

import com.university_assignment.invididualtask.api.client.WebClient
import com.university_assignment.invididualtask.api.interfaces.IWebClient
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class WebClientModule {
    @Binds
    abstract fun bindComponentB(
        impl: WebClient
    ): IWebClient
}