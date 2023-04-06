/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package org.example;

import io.quarkus.test.junit.QuarkusTest;
import org.example.domain.Applicant;
import org.example.domain.LoanApplication;
import org.junit.jupiter.api.Test;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class RuleTest {
    static final Logger LOG = LoggerFactory.getLogger(RuleTest.class);

    @Inject
    SessionsManager sessionsManager;

    @Test
    public void test() {
        KieSession session = sessionsManager.newSession();

        AtomicInteger approvedApplicationsAmount = new AtomicInteger();
        session.setGlobal("approvedApplicationsAmount", approvedApplicationsAmount);
        session.setGlobal("maxAmount", 5000);

        LoanApplication app1 = new LoanApplication("ABC10001", new Applicant("John", 45), 2000, 100);
        LoanApplication app2 = new LoanApplication("ABC10002", new Applicant("Paul", 25), 5000, 100);
        LoanApplication app3 = new LoanApplication("ABC10015", new Applicant("George", 12), 1000, 100);

        session.insert(app1);
        session.insert(app2);
        session.insert(app3);
        session.fireAllRules();

        assertThat(approvedApplicationsAmount.get()).isEqualTo(2000);
    }
}