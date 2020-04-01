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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

/**
 * @author Christian
 *
 */
public class FilesystemFolder extends AbstractFolder {
    private File root;
    private String encoding = "UTF-8";

    private FilesystemFolder(File root, Folder parent, String path, String encoding) {
        super(parent, path);
        this.root = root;
        this.encoding = encoding;
    }

    @Override
    public String getFile(String name) {
        File file = new File(root, name);

        try {
            try (FileInputStream stream = new FileInputStream(file)) {
                return IOUtils.toString(stream, encoding);
            }
        } catch (FileNotFoundException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        }
    }

    @Override
    public Folder getFolder(String name) {
        File folder = new File(root, name);
        if (!folder.exists()) {
            return null;
        }

        return new FilesystemFolder(folder, this, getPath() + name + File.separator, encoding);
    }

    public static FilesystemFolder create(File root, String encoding) {
        File absolute = root.getAbsoluteFile();
        return new FilesystemFolder(absolute, null, absolute.getPath() + File.separator, encoding);
    }
}
