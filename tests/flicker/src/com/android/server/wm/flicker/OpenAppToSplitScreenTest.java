/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.server.wm.flicker;

import static com.android.server.wm.flicker.CommonTransitions.appToSplitScreen;
import static com.android.server.wm.flicker.WindowUtils.getDisplayBounds;
import static com.android.server.wm.flicker.WmTraceSubject.assertThat;

import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

/**
 * Test open app to split screen.
 * To run this test: {@code atest FlickerTest:OpenAppToSplitScreenTest}
 */
public class OpenAppToSplitScreenTest extends FlickerTestBase {
    private static final String NAVIGATION_BAR_WINDOW_TITLE = "NavigationBar";
    private static final String STATUS_BAR_WINDOW_TITLE = "StatusBar";

    public OpenAppToSplitScreenTest() {
        this.testApp = new StandardAppHelper(InstrumentationRegistry.getInstrumentation(),
                "com.android.server.wm.flicker.testapp", "SimpleApp");
    }

    @Before
    public void runTransition() {
        super.runTransition(appToSplitScreen(testApp, uiDevice).includeJankyRuns().build());
    }

    @Test
    public void checkVisibility_navBarIsAlwaysVisible() {
        checkResults(result -> assertThat(result)
                .showsAboveAppWindow(NAVIGATION_BAR_WINDOW_TITLE).forAllEntries());
    }

    @Test
    public void checkVisibility_statusBarIsAlwaysVisible() {
        checkResults(result -> assertThat(result)
                .showsAboveAppWindow(STATUS_BAR_WINDOW_TITLE).forAllEntries());
    }

    @Test
    public void checkVisibility_dividerBecomesVisible() {
        checkResults(result -> assertThat(result)
                .hidesAboveAppWindow("DockedStackDivider")
                .then()
                .showsAboveAppWindow("DockedStackDivider")
                .forAllEntries());
    }

    @Test
    public void checkCoveredRegion_noUncoveredRegions() {
        checkResults(result ->
                LayersTraceSubject.assertThat(result)
                        .coversRegion(getDisplayBounds()).forAllEntries());
    }
}
