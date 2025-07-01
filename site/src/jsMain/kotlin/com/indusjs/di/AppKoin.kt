package com.indusjs.di

import com.indusjs.data.repo.ProfileRepoImpl
import com.indusjs.data.repo.LoginRepoImpl
import com.indusjs.domain.repo.IUserRepository
import com.indusjs.domain.usecase.login.SignInUseCase
import com.indusjs.domain.usecase.login.SignUpUseCase
import com.indusjs.repository.ILoginRepo
import com.indusjs.repository.IProfileRepo
import com.indusjs.repository.UserRepositoryImpl
import com.indusjs.statustracker.viewmodel.SignInViewModule
import com.indusjs.statustracker.viewmodel.SignUpViewModel
//import com.indusjs.statustracker.viewmodel.SignUpViewModule
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            viewModelModule,
            useCasesModule,
            repositoryModule,
            ktorModule,
            baseUrlModule,
            dispatcherModule,
            coroutineScopeModule
            //platformModule()
        )
    }



val viewModelModule: Module = module {
    factory { SignInViewModule(get(), get()) }
    factory { SignUpViewModel(get(), get()) }
    //factory { com.indusjs.statustracker.viewmodel.SignUpViewModule(get(), get()) }
    /*factory { SignUpViewModel(get()) }
    factory { EditProfileViewModel(get()) }
    factory { GetProfileViewModel(get()) }*/
}

val useCasesModule: Module = module {
    factory{ SignInUseCase(get(), get()) }
    factory { SignUpUseCase(get(), get()) }
    /*factory { ForgotPasswordUseCase(get(), get()) }
    factory { SignInUseCase(get(), get()) }
    factory { SignUpUseCase(get(), get()) }
    factory { EditProfileUseCase(get(), get()) }
    factory { GetProfileUseCase(get(), get()) }*/
}

val baseUrlModule:Module = module {
//    single<String>(named("BaseUrl")) { "https://rickandmortyapi.com" }
    single<String>(named("BaseUrl")) { "https://rickandmortyapi.com" }
    single<String>(named("thg")) {  "https://app.thehindu.com/hindu/service/api_v1.006" }
}

val repositoryModule = module {
    single<IUserRepository> { UserRepositoryImpl(get(), get()) }
    single<ILoginRepo> { LoginRepoImpl(get(), get()) }
    single<IProfileRepo> { ProfileRepoImpl(get(), get()) }
}

val dispatcherModule = module {
    factory { Dispatchers.Default }
}

val coroutineScopeModule = module {
    factory { CoroutineScope(Dispatchers.Default) }
}

val ktorModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        isLenient = true
                    }
                )
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.HEADERS
                filter { request ->
                    request.url.host.contains("ktor.io")
                }
                sanitizeHeader { header ->
                    header == HttpHeaders.Authorization
                }
            }
        }
    }

    single { "https://learning-tracker-api-production.up.railway.app" }
}

fun initKoin(module: Module) {
    val koinApp = initKoin {}
    koinApp.modules(module)
}