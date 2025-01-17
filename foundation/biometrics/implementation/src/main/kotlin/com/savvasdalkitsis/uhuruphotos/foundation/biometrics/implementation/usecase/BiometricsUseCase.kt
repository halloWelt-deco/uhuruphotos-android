/*
Copyright 2022 Savvas Dalkitsis

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.savvasdalkitsis.uhuruphotos.foundation.biometrics.implementation.usecase

import androidx.annotation.StringRes
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE
import androidx.biometric.BiometricManager.BIOMETRIC_SUCCESS
import com.afollestad.assure.Prompt
import com.afollestad.assure.authenticate
import com.savvasdalkitsis.uhuruphotos.foundation.activity.api.CurrentActivityHolder
import com.savvasdalkitsis.uhuruphotos.foundation.biometrics.api.model.Biometrics
import com.savvasdalkitsis.uhuruphotos.foundation.biometrics.api.model.Biometrics.Enrolled
import com.savvasdalkitsis.uhuruphotos.foundation.biometrics.api.model.Biometrics.NoHardware
import com.savvasdalkitsis.uhuruphotos.foundation.biometrics.api.model.Biometrics.NotEnrolled
import com.savvasdalkitsis.uhuruphotos.foundation.biometrics.api.usecase.BiometricsUseCase
import com.savvasdalkitsis.uhuruphotos.foundation.launchers.api.awaitOnMain
import com.savvasdalkitsis.uhuruphotos.foundation.log.api.log
import com.savvasdalkitsis.uhuruphotos.foundation.log.api.runCatchingWithLog
import javax.inject.Inject
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

internal class BiometricsUseCase @Inject constructor(
    private val biometricManager: BiometricManager,
    private val currentActivityHolder: CurrentActivityHolder,
) : BiometricsUseCase {

    override fun getBiometrics(): Biometrics = when (biometricManager.canAuthenticate()) {
        BIOMETRIC_SUCCESS -> Enrolled
        BIOMETRIC_ERROR_NO_HARDWARE -> NoHardware
        else -> NotEnrolled
    }

    override suspend fun authenticate(
        @StringRes
        title: Int,
        @StringRes
        subtitle: Int,
        @StringRes
        description: Int,
        confirmRequired: Boolean,
    ): Result<Unit> = runCatchingWithLog {
        with(currentActivityHolder.currentActivity!!) {
            awaitOnMain {
                suspendCoroutine { continuation ->
                    authenticate(
                        Prompt(
                            title = title,
                            subtitle = subtitle,
                            description = description,
                            confirmRequired = confirmRequired,
                            deviceCredentialsAllowed = true,
                        )
                    ) { exception ->
                        try {
                            exception?.let { continuation.resumeWithException(it) }
                                ?: continuation.resumeWith(Result.success(Unit))
                        } catch (e: Exception) {
                            log(e)
                        }
                    }
                }
            }
        }
    }
}