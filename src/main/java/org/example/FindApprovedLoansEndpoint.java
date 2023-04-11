/*
 * Copyright 2021 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

@Path("session/{sessionId}/find-approved-amount/")
public class FindApprovedLoansEndpoint {

    @Inject
    SessionsManager sessionsManager;

    @POST()
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response execute(@PathParam("sessionId") Long sessionId, LoanUnit loanUnit) {
        KieSession session = sessionsManager.getSession(sessionId);

        AtomicInteger approvedApplicationsAmount = new AtomicInteger();
        session.setGlobal("approvedApplicationsAmount", approvedApplicationsAmount);
        session.setGlobal("maxAmount", loanUnit.getMaxAmount());

        loanUnit.getLoanApplications().forEach(session::insert);
        session.fireAllRules();

        return new Response(sessionId, approvedApplicationsAmount.get());
    }
}
