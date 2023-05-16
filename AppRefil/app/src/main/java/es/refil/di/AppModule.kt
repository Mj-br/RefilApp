package es.refil.di

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.refil.data.utils.auth.AuthRepository
import es.refil.data.utils.auth.AuthRepositoryImpl
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()
    @Provides
    @Singleton
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

}