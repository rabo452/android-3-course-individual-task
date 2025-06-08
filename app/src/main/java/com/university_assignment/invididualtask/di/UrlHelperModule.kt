package com.university_assignment.invididualtask.di

import com.university_assignment.invididualtask.utils.WebsiteUrlHelper.UrlHelper
import com.university_assignment.invididualtask.utils.interfaces.IUrlHelper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UrlHelperModule {
    @Binds
    abstract fun bindComponentB(
        impl: UrlHelper
    ): IUrlHelper
}