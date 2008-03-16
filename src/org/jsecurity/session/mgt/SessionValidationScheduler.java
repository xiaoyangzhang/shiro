/*
 * Copyright 2005-2008 Jeremy Haile
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

/**
 * Interface that should be implemented by classes that can control validating sessions on a regular
 * basis.  This interface is used as a delegate for session validation by the {@link DefaultSessionManager}
 *
 * @see DefaultSessionManager#setSessionValidationScheduler(SessionValidationScheduler)
 *
 * @since 0.1
 * @author Jeremy Haile
 */
public interface SessionValidationScheduler {

    /**
     * Starts the session validation job.
     */
    void startSessionValidation();

    /**
     * Stops the session validation job.
     */    
    void stopSessionValidation();

}