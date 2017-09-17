package oxim.digital.sanq.dagger.application;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import oxim.digital.sanq.dagger.ComponentFactory;

/**
 * Copyright 2017 Mihael Franceković
 * <p>
 * Whole application code licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public final class SanqApplication extends Application {

    private ApplicationComponent applicationComponent;

    public static SanqApplication from(final Context context) {
        return (SanqApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        applicationComponent = ComponentFactory.createApplicationComponent(this);
        applicationComponent.inject(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
