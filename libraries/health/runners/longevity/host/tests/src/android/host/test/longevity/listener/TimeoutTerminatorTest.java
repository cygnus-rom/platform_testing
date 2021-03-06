/*
 * Copyright (C) 2017 The Android Open Source Project
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
package android.host.test.longevity.listener;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

/**
 * Unit tests for the core {@link TimeoutTerminator}.
 */
@RunWith(JUnit4.class)
public class TimeoutTerminatorTest {
    private TimeoutTerminator mListener;
    @Mock private RunNotifier mNotifier;

    @Before
    public void setupListener() {
        MockitoAnnotations.initMocks(this);
        Map<String, String> args = new HashMap<>();
        args.put(TimeoutTerminator.OPTION, String.valueOf(50L));
        mListener = new TimeoutTerminator(mNotifier, args);
    }
    /**
     * Unit test the listener's kill logic.
     */
    @Test
    public void testTimeoutTerminator_pass() throws Exception {
        mListener.testRunStarted(Description.EMPTY);
        Thread.sleep(10L);
        mListener.testFinished(Description.EMPTY);
        verify(mNotifier, never()).pleaseStop();
    }

    /**
     * Unit test the listener's kill logic.
     */
    @Test
    public void testTimeoutTerminator_timeout() throws Exception {
        mListener.testRunStarted(Description.EMPTY);
        Thread.sleep(60L);
        mListener.testFinished(Description.EMPTY);
        verify(mNotifier).pleaseStop();
    }
}
