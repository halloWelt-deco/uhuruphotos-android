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
package com.savvasdalkitsis.uhuruphotos.people.viewmodel

import com.savvasdalkitsis.uhuruphotos.navigation.ControllersProvider
import com.savvasdalkitsis.uhuruphotos.people.viewmodel.PeopleEffect.ErrorLoadingPeople
import com.savvasdalkitsis.uhuruphotos.people.viewmodel.PeopleEffect.NavigateBack
import com.savvasdalkitsis.uhuruphotos.people.viewmodel.PeopleEffect.NavigateToPerson
import com.savvasdalkitsis.uhuruphotos.person.api.navigation.PersonNavigationTarget
import com.savvasdalkitsis.uhuruphotos.strings.R
import com.savvasdalkitsis.uhuruphotos.toaster.Toaster
import com.savvasdalkitsis.uhuruphotos.viewmodel.EffectHandler
import javax.inject.Inject

class PeopleEffectHandler @Inject constructor(
    private val controllersProvider: ControllersProvider,
    private val toaster: Toaster,
) : EffectHandler<PeopleEffect> {

    override suspend fun invoke(effect: PeopleEffect) {
        when (effect) {
            ErrorLoadingPeople -> toaster.show(R.string.error_refreshing_people)
            NavigateBack -> controllersProvider.navController!!.popBackStack()
            is NavigateToPerson -> controllersProvider.navController!!.navigate(
                PersonNavigationTarget.name(effect.person.id)
            )
        }
    }

}