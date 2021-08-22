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
package android.host.test.composer;

import java.util.HashMap;
import java.util.Map;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Unit test the logic for host-side {@link Iterate}
 */
@RunWith(JUnit4.class)
public class IterateTest extends IterateTestBase<Map<String, String>> {
    protected class MapArgumentsBuilder extends ArgumentsBuilder {
        @Override
        public Map<String, String> build() {
            Map<String, String> args = new HashMap<>();
            if (mIterations != null) {
                args.put(IterateTestBase.ITERATIONS_OPTION_NAME, String.valueOf(mIterations));
            }
            if (mAlternateIterations != null) {
                args.put(
                        IterateTestBase.ITERATIONS_OPTION_ALTERNATE_NAME,
                        String.valueOf(mAlternateIterations));
            }
            if (mOrder != null) {
                args.put(IterateTestBase.ORDER_OPTION_NAME, mOrder);
            }
            return args;
        }
    }

    @Override
    protected ArgumentsBuilder getArgumentsBuilder() {
        return new MapArgumentsBuilder();
    }

    @Override
    protected IterateBase<Map<String, String>, Integer> getIterate() {
        return new Iterate<Integer>();
    }
}