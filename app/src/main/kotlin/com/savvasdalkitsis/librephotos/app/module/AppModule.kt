package com.savvasdalkitsis.librephotos.app.module

import android.content.Context
import androidx.preference.PreferenceManager
import androidx.work.WorkManager
import com.fredporciuncula.flow.preferences.FlowSharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun workManager(@ApplicationContext context: Context): WorkManager = WorkManager
        .getInstance(context)

    @Provides
    fun preferences(@ApplicationContext context: Context): FlowSharedPreferences =
        FlowSharedPreferences(PreferenceManager.getDefaultSharedPreferences(context))

}