package org.example;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.PersistedSessionOption;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class SessionsManager {
    static {
        System.setProperty("drools.reliability.cache.allowedpackages", "org.example");
    }

    private static final KieContainer kContainer = KieServices.Factory.get().newKieClasspathContainer();

    private Map<Long, KieSession> sessionsStore = new HashMap<>();

    public KieSession newSession() {
        KieSessionConfiguration conf = KieServices.get().newKieSessionConfiguration();
        conf.setOption(PersistedSessionOption.newSession(PersistedSessionOption.Strategy.STORES_ONLY));
        KieSession session = kContainer.newKieSession(conf);
        sessionsStore.put(session.getIdentifier(), session);
        return session;
    }

    public KieSession getSession(long sessionId) {
        KieSession session = sessionsStore.get(sessionId);
        if (session == null) {
            KieSessionConfiguration conf = KieServices.get().newKieSessionConfiguration();
            conf.setOption(PersistedSessionOption.fromSession(sessionId, PersistedSessionOption.Strategy.STORES_ONLY));
            session = kContainer.newKieSession(conf);
        }
        return session;
    }
}
