# Restaurant reservation system

Restaurant reservation system is project for Internet software architectures course. 
Technologies used:

  - apache tomee+ 1.5
  - EJB 3.0
  - Servlet 
  - JSP
  - JavaScript/Jquery/Ajax
  - MySQL



### Installation

You need to download:
* Apache Tomee+
* MySql server + [Workbench] 
* Ant

To start your apache server you'll need to do following:

On linux:
```sh
$ cd $APACHE_INST_FOLD/bin
$ chmod +x catalina.sh
$ ./catalina.sh run     
``` 

On Windows:
```sh
$ cd $APACHE_INST_FOLD/bin
$ catalina.bat run
```

To stop server:

On linux:
```sh
$ ./catalina.sh stop     
``` 

On Windows:
```sh
$ catalina.bat stop
```
### Deployment

Open eclipse and build.xml. Right click on 'deployment' target and run as ant. 
In order to use application you'll first need to put at least one system administrator in your database. Also, in order to use application properly tou'll need to add some countries, cities and streets.
You are ready to rock now.
