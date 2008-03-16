/*
 * Copyright 2005-2008 Les Hazlewood
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jsecurity.session.mgt;

import org.jsecurity.cache.CacheManager;
import org.jsecurity.session.InvalidSessionException;
import org.jsecurity.session.Session;
import org.jsecurity.session.mgt.eis.MemorySessionDAO;
import org.jsecurity.session.mgt.eis.SessionDAO;
import org.jsecurity.util.Destroyable;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Date;

/**
 * Default business-tier implementation of the {@link ValidatingSessionManager} interface.
 *
 * @since 0.1
 * @author Les Hazlewood
 */
public class DefaultSessionManager extends AbstractValidatingSessionManager
    implements Destroyable {

    protected SessionDAO sessionDAO = null;

    public DefaultSessionManager() {}

    public void setSessionDAO(SessionDAO sessionDAO) {
        this.sessionDAO = sessionDAO;
    }

    public SessionDAO getSessionDAO() {
        return this.sessionDAO;
    }

    protected void afterSessionValidationStarted() {
        ensureSessionDAO();
    }

    protected void ensureSessionDAO() {
        SessionDAO sessionDAO = getSessionDAO();
        if ( sessionDAO == null ) {
            if ( log.isDebugEnabled() ) {
                log.debug( "No sessionDAO set.  Attempting to create default instance." );
            }
            sessionDAO = createSessionDAO();
            setSessionDAO( sessionDAO );
        }
    }

    /**
     * Creates a default <tt>SessionDAO</tt> during {@link #init initialization} as a fail-safe mechanism if one has
     * not already been explicitly set via {@link #setSessionDAO}, relying upon the configured
     * {@link #setCacheManager cacheManager} to determine caching strategies.
     *
     * <p><b>N.B.</b> This implementation constructs a {@link org.jsecurity.session.mgt.eis.MemorySessionDAO} instance, relying on a configured
     * {@link #setCacheManager cacheManager} to provide production-quality cache management.  Please ensure that
     * the <tt>CacheManager</tt> property is configured for production environments, since the
     * <tt>MemorySessionDAO</tt> implementation defaults to a
     * {@link org.jsecurity.cache.HashtableCacheManager HashtableCacheManager}
     * (the <tt>HashtableCacheManager</tt> is NOT RECOMMENDED for production environments).
     *
     * @return a lazily created SessionDAO instance that this SessionManager will use for all Session EIS operations.
     */
    protected SessionDAO createSessionDAO() {

        if ( log.isDebugEnabled() ) {
            log.debug( "No sessionDAO set.  Creating default instance..." );
        }

        MemorySessionDAO dao = new MemorySessionDAO();

        CacheManager cacheManager = getCacheManager();
        if ( cacheManager != null ) {
            dao.setCacheManager(cacheManager);
        }

        dao.init();

        return dao;
    }

    protected Session createSession(InetAddress originatingHost) {

        if (log.isTraceEnabled()) {
            log.trace("Creating session for originating host [" + originatingHost + "]");
        }

        Session s = newSessionInstance(originatingHost);
        create(s);

        return s;
    }

    protected Session newSessionInstance(InetAddress inetAddress) {
        return new SimpleSession(inetAddress);
    }

    protected void create(Session session) {
        if (log.isDebugEnabled()) {
            log.debug("Creating new EIS record for new session instance [" + session + "]");
        }
        sessionDAO.create(session);
    }

    protected void onStop(Session session) {
        if ( session instanceof SimpleSession ) {
            Date stopTs = session.getStopTimestamp();
            ((SimpleSession)session).setLastAccessTime(stopTs);
        }
        onChange(session);
    }

    protected void onExpiration(Session session) {
        if ( session instanceof SimpleSession ) {
            ((SimpleSession)session).setExpired(true);
        }
        onChange(session);
    }

    protected void onChange(Session session) {
        sessionDAO.update(session);
    }

    protected Session doGetSession(Serializable sessionId) throws InvalidSessionException {
        if (log.isTraceEnabled()) {
            log.trace("Retrieving session with id [" + sessionId + "]");
        }
        Session s = sessionDAO.readSession(sessionId);
        validate(s);
        return s;
    }

    protected Collection<Session> getActiveSessions() {
        return sessionDAO.getActiveSessions();
    }

}
