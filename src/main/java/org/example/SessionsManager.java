package org.example;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieRuntimeBuilder;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.PersistedSessionOption;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class SessionsManager {

    @Inject
    KieRuntimeBuilder runtimeBuilder;

    private Map<Long, KieSession> sessionsStore = new HashMap<>();

    public KieSession newSession() {
        KieSessionConfiguration conf = KieServices.get().newKieSessionConfiguration();
        conf.setOption(PersistedSessionOption.newSession(PersistedSessionOption.Strategy.STORES_ONLY));
        KieSession session = runtimeBuilder.newKieSession(conf);
        sessionsStore.put(session.getIdentifier(), session);
        return session;
    }

    public KieSession getSession(long sessionId) {
        KieSession session = sessionsStore.get(sessionId);
        if (session == null) {
            KieSessionConfiguration conf = KieServices.get().newKieSessionConfiguration();
            conf.setOption(PersistedSessionOption.fromSession(sessionId, PersistedSessionOption.Strategy.STORES_ONLY));
            session = runtimeBuilder.newKieSession(conf);
        }
        return session;
    }
}
