package es.refil.di

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.refil.data.network.auth.AuthRepository
import es.refil.data.network.auth.AuthRepositoryImpl
import es.refil.data.network.qrCode.QrRepository
import es.refil.data.network.qrCode.QrRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

    @Singleton
    @Provides
    fun provideQrRepository(impl: QrRepositoryImpl): QrRepository = impl


}