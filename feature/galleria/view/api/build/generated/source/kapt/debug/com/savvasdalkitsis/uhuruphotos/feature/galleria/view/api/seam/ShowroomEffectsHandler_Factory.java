// Generated by Dagger (https://dagger.dev).
package com.savvasdalkitsis.uhuruphotos.feature.galleria.view.api.seam;

import com.savvasdalkitsis.uhuruphotos.foundation.navigation.api.Navigator;
import com.savvasdalkitsis.uhuruphotos.foundation.toaster.api.Toaster;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class ShowroomEffectsHandler_Factory implements Factory<GalleriaEffectsHandler> {
  private final Provider<Navigator> navigatorProvider;

  private final Provider<Toaster> toasterProvider;

  public ShowroomEffectsHandler_Factory(Provider<Navigator> navigatorProvider,
      Provider<Toaster> toasterProvider) {
    this.navigatorProvider = navigatorProvider;
    this.toasterProvider = toasterProvider;
  }

  @Override
  public GalleriaEffectsHandler get() {
    return newInstance(navigatorProvider.get(), toasterProvider.get());
  }

  public static ShowroomEffectsHandler_Factory create(Provider<Navigator> navigatorProvider,
      Provider<Toaster> toasterProvider) {
    return new ShowroomEffectsHandler_Factory(navigatorProvider, toasterProvider);
  }

  public static GalleriaEffectsHandler newInstance(Navigator navigator, Toaster toaster) {
    return new GalleriaEffectsHandler(navigator, toaster);
  }
}
