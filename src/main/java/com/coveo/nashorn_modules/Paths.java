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
public class Paths {
    public static String[] splitPath(String path) {
        return path.split("[\\\\/]");
    }
}
