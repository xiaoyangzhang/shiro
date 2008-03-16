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
package org.jsecurity.session;

import java.io.Serializable;

/**
 * Exception thrown when attempting to interact with the system under the pretense of a
 * particular session (e.g. under a specific session id), and that session does not exist in
 * the system.
 *
 * @since 0.1
 * @author Les Hazlewood
 */
public class UnknownSessionException extends InvalidSessionException {

    /**
     * Creates a new UnknownSessionException.
     */
    public UnknownSessionException() {
        super();
    }

    /**
     * Constructs a new UnknownSessionException.
     * @param message the reason for the exception
     */
    public UnknownSessionException( String message ) {
        super( message );
    }

    /**
     * Constructs a new UnknownSessionException.
     * @param cause the underlying Throwable that caused this exception to be thrown.
     */
    public UnknownSessionException( Throwable cause ) {
        super( cause );
    }

    /**
     * Constructs a new UnknownSessionException.
     * @param message the reason for the exception
     * @param cause the underlying Throwable that caused this exception to be thrown.
     */
    public UnknownSessionException( String message, Throwable cause ) {
        super( message, cause );
    }

    /**
     * Constructs a new UnknownSessionException.
     * @param sessionId the session id given that is unknown to the system.
     */
    public UnknownSessionException( Serializable sessionId ) {
        super( sessionId );
    }

    /**
     * Constructs a new UnknownSessionException.
     * @param message the reason for the exception
     * @param sessionId the session id given that is unknown to the system.
     */
    public UnknownSessionException( String message, Serializable sessionId ) {
        super( message, sessionId );
    }

    /**
     * Constructs a new UnknownSessionException.
     * @param message the reason for the exception
     * @param cause the underlying Throwable that caused this exception to be thrown.
     * @param sessionId the session id given that is unknown to the system.
     */
    public UnknownSessionException( String message, Throwable cause, Serializable sessionId ) {
        super( message, cause, sessionId );
    }
}
