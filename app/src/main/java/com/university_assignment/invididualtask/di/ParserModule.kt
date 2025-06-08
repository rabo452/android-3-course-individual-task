package com.university_assignment.invididualtask.di

import com.university_assignment.invididualtask.api.Parser.Parser
import com.university_assignment.invididualtask.api.interfaces.IParser
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ParserModule {
    @Binds
    abstract fun bindComponentB(
        impl: Parser
    ): IParser
}