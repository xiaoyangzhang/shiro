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
package org.jsecurity.authc.event.mgt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsecurity.authc.Account;
import org.jsecurity.authc.AuthenticationException;
import org.jsecurity.authc.AuthenticationToken;
import org.jsecurity.authc.event.AuthenticationEvent;
import org.jsecurity.authc.event.AuthenticationEventListener;

import java.util.Collection;

/**
 * @author Les Hazlewood
 * @since 0.9
 */
public class DefaultAuthenticationEventManager implements AuthenticationEventManager {

    protected transient final Log log = LogFactory.getLog( getClass() );

    protected AuthenticationEventFactory authenticationEventFactory = new DefaultAuthenticationEventFactory();
    protected AuthenticationEventSender authenticationEventSender = new DefaultAuthenticationEventSender();

    public DefaultAuthenticationEventManager(){}

    public AuthenticationEventFactory getAuthenticationEventFactory() {
        return authenticationEventFactory;
    }

    public void setAuthenticationEventFactory(AuthenticationEventFactory authenticationEventFactory) {
        this.authenticationEventFactory = authenticationEventFactory;
    }

    public AuthenticationEventSender getAuthenticationEventSender() {
        return authenticationEventSender;
    }

    public void setAuthenticationEventSender(AuthenticationEventSender authenticationEventSender) {
        this.authenticationEventSender = authenticationEventSender;
    }

    public AuthenticationEvent createFailureEvent(AuthenticationToken token, AuthenticationException ex) {
        return this.authenticationEventFactory.createFailureEvent(token,ex);
    }

    public AuthenticationEvent createSuccessEvent(AuthenticationToken token, Account account) {
        return this.authenticationEventFactory.createSuccessEvent(token,account);
    }

    public AuthenticationEvent createLogoutEvent(Object subjectPrincipal) {
        return this.authenticationEventFactory.createLogoutEvent(subjectPrincipal);
    }

    /**
     * Utility method that first creates a failure event based on the given token and exception and then actually sends
     * the event.
     *
     * @param token the authentication token reprenting the subject (user)'s authentication attempt.
     * @param ae    the <tt>AuthenticationException</tt> that occurred as a result of the attempt.
     */
    public void sendFailureEvent(AuthenticationToken token, AuthenticationException ae) {
        if (isSendingEvents()) {
            AuthenticationEvent event = createFailureEvent(token, ae);
            send(event);
        }
    }

    /**
     * Utility method that first creates a success event based on the given token and account and then actually sends
     * the event.
     *
     * @param token   the authentication token reprenting the subject (user)'s authentication attempt.
     * @param account the <tt>Account</tt> obtained after the successful attempt.
     */
    public void sendSuccessEvent(AuthenticationToken token, Account account) {
        if (isSendingEvents()) {
            AuthenticationEvent event = createSuccessEvent(token, account);
            send(event);
        }
    }

    /**
     * Utility method that first creates a logout event based on the given subjectIdentifier and then actually
     * sends the event.
     *
     * @param subjectPrincipal the application-specific Subject/user identifier.
     */
    public void sendLogoutEvent(Object subjectPrincipal) {
        if (isSendingEvents()) {
            AuthenticationEvent event = createLogoutEvent(subjectPrincipal);
            send(event);
        }

    }

    public void send(AuthenticationEvent ae) {
        this.authenticationEventSender.send(ae);
    }

    public void setAuthenticationEventListeners(Collection<AuthenticationEventListener> listeners) {
        this.authenticationEventSender.setAuthenticationEventListeners(listeners);
    }

    public void add(AuthenticationEventListener listener) {
        this.authenticationEventSender.add(listener);
    }

    public boolean remove(AuthenticationEventListener listener) {
        return authenticationEventSender.remove(listener);
    }

    public boolean isSendingEvents() {
        return authenticationEventSender.isSendingEvents();
    }
}
