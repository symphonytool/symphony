MISSION:
-------

OSGi handles bundles (modules) potential with the exact same classes
defined. It does so in a fine grained allowing us to control which
bundle's class-loader will see what packages. The Mission with is to
use let the Overture-AST 2.2.0 co-exists with the CML AST 0.0.1. 

Equinox is also the core of Eclipse and the OSGi framework is pretty
neat offering a wide range of functionallity for dynamic
configuration. In particular Eclipse-plugins are controlled by the
Equinox container and hence understading Equinox is one step on the
way to wards understanding Eclipse Plugins.

BACKGROUND:
-----------

From Eclipse 3.0 its core has been build around the OSGi architecture
to manage plug-ins. OSGi is a conceptual framework for dynamically
managing modules (aka. bundles). The concrete implementation of OSGi
in Eclipse is known as Equinox. 

In this folder experiments with Equinox are conducted to get a better
understading of the inner workings of Eclipse. An on going small
example is build and the build-process is integrated with the Maven
set up for CML.

HOPE:
----

On vision could be that the COMPASS Tool kit is a set of bundles
running with in the Equinox OSGi container along side with the Eclipse
UI its plug-ins.

LET GET STARTED:
----------------

All right, to start getting this going with "mvn install" the Eclipse
Equinox OSGi jar must be installed, currently we do that with 

Download: http://www.eclipse.org/downloads/download.php?file=/equinox/drops/R-3.7-201106131736/org.eclipse.osgi_3.7.0.v20110613.jar

mvn install:install-file \
    -Dfile=org.eclipse.osgi_3.7.0.v20110613.jar \
    -DgroupId=org.eclipse.equinox \
    -DartifactId=org.eclipse.osgi -Dversion=3.7.0 \
    -DgeneratePom=true \
    -Dpackaging=jar		  

Now build this with mvn install.

To startup the Equinox container run:

java -jar org.eclipse.osgi_3.7.0.v20110613.jar -console 

this will drop you into the OSGi console: osgi>

run the ss (short status) command to see whether or not the Producer
and Consumer bundles are installed. If they are they can be started
and stopped. Now they are not install them:

producer:
install file:./producer/target/EquinoxProducer-0.0.1.jar

consumer:
install file:./consumer/target/EquinoxConsumer-0.0.1.jar

to install them. We they can be started, type ss in to obtain the <id>
of the producer (consumer).  Type start <id> to start the bundle.

If successful you should see the producer cooperating:

Consumer: The Producer says hey for the !0th time
Consumer: The Producer says hey for the !1th time
Consumer: The Producer says hey for the !2th time
Consumer: The Producer says hey for the !3th time
Consumer: The Producer says hey for the !4th time
...


FUTURE WORK:
------------

(*) Try to make a bundle that invokes the CML-parser. E.g. the producer
    could produce a bit of CML the consumer could parse it printing the
    tree.

(*) Add just enough Eclipse jars to make a bare-bone Eclipse UI run
    from the Equinox container starting it from the osgi console.

(*) Start incrementally adding CML plugins and their dependencies to
    make the CML-Eclipse Tool build with Maven alone. That is, without
    PDE support !

phd/jointpub/phds/RasmusLauritsen/papers/DRM_Overture_2012/paper/

bin.includes = plugin.xml,\
               META-INF/,\
               .,\
               icons/,\
               splash.bmp

