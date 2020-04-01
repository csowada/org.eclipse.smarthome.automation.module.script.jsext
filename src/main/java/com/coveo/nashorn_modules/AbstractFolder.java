/**
 * Copyright (c) 2010-2019 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package com.coveo.nashorn_modules;

/**
 * @author Christian
 *
 */
public abstract class AbstractFolder implements Folder {
    private Folder parent;
    private String path;

    @Override
    public Folder getParent() {
        return parent;
    }

    @Override
    public String getPath() {
        return path;
    }

    protected AbstractFolder(Folder parent, String path) {
        this.parent = parent;
        this.path = path;
    }
}
