package dev.materii.gloom.di.module

import com.apollographql.apollo.ApolloClient
import dev.materii.gloom.domain.manager.AuthManager
import dev.materii.gloom.domain.manager.DialogManager
import dev.materii.gloom.domain.manager.DownloadManager
import dev.materii.gloom.domain.manager.LibraryManager
import dev.materii.gloom.domain.manager.PreferenceManager
import dev.materii.gloom.domain.manager.ShareManager
import dev.materii.gloom.domain.manager.ToastManager
import dev.materii.gloom.util.Logger
import dev.materii.gloom.util.SettingsProvider
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual fun managerModule() = module {

    singleOf(::ToastManager)
    singleOf(::DownloadManager)
    singleOf(::ShareManager)
    singleOf(::LibraryManager)

    fun providePreferenceManager(settings: SettingsProvider) = PreferenceManager(settings)
    fun provideDialogManager(settings: SettingsProvider) = DialogManager(settings)
    fun provideAuthManager(settings: SettingsProvider, apollo: ApolloClient, json: Json, logger: Logger) = AuthManager(settings, apollo, json, logger)

    single { providePreferenceManager(get(named("prefs"))) }
    single { provideDialogManager(get(named("dialogs"))) }
    single { provideAuthManager(get(named("auth")), get(), get(), get()) }

}