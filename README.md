DevNote
=======

Note orginizer system optimized for developers. 

**Current version:** *alpha*

DevNote is a simple Java EE application for saving text notes in tree-like directory structure. App is multiuser, so all notes and directories belongs to one user. Any note edits saved in another version of note, so there is some versioning for now.

##Planned features:
* Simple text, html and source code notes saving and correct displaying;
* Note attachments (*file, links etc*);
* Notes complete versioning (first release exclude header from version);
* API for remote access from Android/iOS devices (*in plans only Android client*);
* Localization;

##Dependencies:
* Java 7 runtime;
* GlassFish 4 (*may work on 3.x, I didn't test)*;
* Mysql server for database;

##Copyright and license terms

Application distributed under terms of GNU GPLv3 license.

**© Stanislav Nepochatov 2014**