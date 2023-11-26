package zuper.dev.android.dashboard.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import zuper.dev.android.dashboard.data.DataRepository
import zuper.dev.android.dashboard.data.remote.ApiDataSource

@Module
@InstallIn(SingletonComponent::class)
class JobModule {

    @Provides
    fun provideApiDataSource() = ApiDataSource()

    @Provides
    fun provideDataRepo(apiDataSource: ApiDataSource) = DataRepository(apiDataSource)
}