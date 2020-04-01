# JSR223 Automation Extension

This automation extension adds a extended JavaScript environment to openHAB. The main features of this extension are

- Add ``require()`` function to global scope, see [CommonJS Modules Support for Nashorn](https://github.com/coveo/nashorn-commonjs-modules) for more details
- Only load root level JavaScipt files
- Auto reload all root files if a file in a sub directory has changed


## Installation

### ZIP file

Copy the jar file into the `openhab\addon` directory.

## Use

Create a new folder ``jsr223-ext`` in your openhab ``conf`` folder. Place you script files in this directory. Keep in mind that only files in the root directory will be loaded. All files in the sub directories must be loaded by the ``required()`` function.

## Compile

* Clone the official ``openhab-core`` repository on your computer and switch to the ``2.5.0`` tag.
* Cloine this repository to the ``bundles`` directory of the ``openhab-core`` project.
* Add the module ``org.openhab.core.automation.module.script.jsext`` to ``bundles/pomx.xml``
* Build with ``mvn install`` from root directory

## License

EPL

We use ``CommonJS Modules Support for Nashorn`` which is licensed under MIT license.