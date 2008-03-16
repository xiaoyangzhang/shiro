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
package org.jsecurity.web.attr;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @since 0.2
 * @author Les Hazlewood
 */
public class RequestParamAttribute<T> extends AbstractWebAttribute<T> {

    public RequestParamAttribute() {
        setMutable(false);
        setCheckRequestParams( false );
    }

    public RequestParamAttribute( String name ) {
        super( name );
        setMutable(false);
        setCheckRequestParams( false );
    }

    protected T onRetrieveValue( ServletRequest request, ServletResponse response ) {
        return getFromRequestParam( request );
    }

    protected void onStoreValue( T value, ServletRequest request, ServletResponse response ) {
        throw new UnsupportedOperationException( "RequestParamStores are read-only." );
    }

    public void removeValue(ServletRequest request, ServletResponse response) {
        //no op - can't alter request attributes
        if ( log.isWarnEnabled() ) {
            String msg = "Asked to remove WebAttribute value.  A " + getClass().getName() + " implementation " +
                "cannot remove values from the request params.";
            log.warn(msg);
        }
    }
}
