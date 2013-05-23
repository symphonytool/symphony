#!/usr/bin/python
import sys
import zipfile;
import os;
AllSymbolicName=[];

def ReadProp(lines, propName):
    result="";
    readingProperty=False;
    for line in lines:

        if (readingProperty):
            if (line[0] != " "):
                break;
            result += line.strip();
            continue;

        if (line.startswith(propName+":")):
            readingProperty=True;
            result += line[line.index(":")+1:len(line)].strip()
            continue;


    return result;


def ReadPropJar(jarFile, propName):
    if jarFile.endswith(".jar"):
        jar = zipfile.ZipFile(jarFile);
        manifest=jar.read("META-INF/MANIFEST.MF");
        jar.close();
        return ReadProp(manifest.split("\n"), propName);
    else:
        return "";

def ReadPropDir(directory, propName):
    manifestfile=directory+"/META-INF/MANIFEST.MF";
    if not os.path.exists(manifestfile):
        return "";
    manifile = open(manifestfile);
    manifest=manifile.readlines();
    manifile.close();
    return ReadProp(manifest, propName);

#
# Extract a property from a bundle in either jar or folder form. 
#
def FindProp(name,propName):
    if (os.path.isdir(name)):
        return ReadPropDir(name,propName)
    else:
        return ReadPropJar(name,propName);
    
#
# Iteratate directory d. For each item add its symbolic name from the
# manifest to the resulting list. 
#
def CollectAllSymbolicNames(d):
    result = [];
    for file in os.listdir(d):
        result.append(FindProp(d+"/"+file,"Bundle-SymbolicName"));
    return result;

#
# When listed in a MANIFEST.MF file in the required bundle a reference
# to another bundle has the form: <name>;<key>=<val>;<key>=<val>... 
# 
# To get the actual name stripping of all attributes this functions
# returns what comes before the first occurance of ';' in str.
#
def GetNameStripAttrs(str):
    if (str.count(";") > 0):
        str = str[0:str.index(";")];

    return     str.strip();

#
# AllSymbolicName aka List.
#
def IsBundleInList(bundle):
    for existingBundle in AllSymbolicName:
        if (GetNameStripAttrs(existingBundle) == 
            GetNameStripAttrs(bundle)):
            return True;
    return False;

#
# Given a directory d iterate through all items and check that
# required bundles listed in META-INF/MANIFEST.MF files in jars and
# folders are present in the AllSymbolicName environment.
#
def CheckBundleClosure(d):
    numberOfMissingBundles=0
    for file in os.listdir(d):
        filePath=d+"/"+file;
        bundleShape=FindProp(filePath,"Eclipse-BundleShape");
        if ( (bundleShape != "" and bundleShape != "jar") and not os.path.isdir(filePath)):
            print "Warning: Plugin "+filePath+" requires to be deployed as "+bundleShape + " but exists as jar file.";
        bundles=FindProp(filePath,"Require-Bundle");
        for bundle in SplitBundlesInRequireBundles(bundles):
            if (not IsBundleInList(bundle)):
                print "Warning:\n\t"+bundle+"\nrequired by\n\t"+file+"\nwere not found";
                numberOfMissingBundles = numberOfMissingBundles + 1;
    print "Total number of missing bundles is: "+str(numberOfMissingBundles)

#
# Going through all items in the given directory, this function prints
# bundle dependencies found grouped by plugin.
#
def ComputeNeededBundles(d):
    for file in os.listdir(d):
        bundles=FindProp(d+"/"+file, "Require-Bundle");
        print file+" needs:"
        for bundle in SplitBundlesInRequireBundles(bundles):
            print "\t"+GetNameStripAttrs(bundle);

#
# Given a Require-Bundle content string from a MANIFEST.MF file this
# function creates a list with the listed bundles.
#
def SplitBundlesInRequireBundles(str):
    res = [];
    inString=False;
    curBndl = "";
    for i in range(0,len(str)):

        if (str[i] == "\""):
            inString=not inString;

        if (inString):
            curBndl += str[i];
            continue;

        if (str[i] == ","):
            res.append(curBndl);
            curBndl = "";
            continue;

        curBndl += str[i];
    
    if (curBndl != ""):
        res.append(curBndl)
    return res;

#
# Build list with all symbols provided from target_platform plugins
# and from the plugins in the parent directory.
#
# Then check that we have dependency closure in both these places
#
def CheckFromBuild2():
    global AllSymbolicName;
    AllSymbolicName = CollectAllSymbolicNames("target_platform/plugins");
    AllSymbolicName += CollectAllSymbolicNames("..");
    CheckBundleClosure("target_platform/plugins");
    CheckBundleClosure("..");

#
# Given the content of a product file list all the plugins listed in
# the plugins section.
#
def ListPluginsReferencedInProductFile(content):
    listed_needed_plugins=[];
    for line in content:
        t=line.strip();
        if (t.startswith("<plugin id=\"")):
            l1 = t[t.index("\"")+1:len(t)];
            l2 = l1[0:l1.index("\"")];
            listed_needed_plugins.append(l2);
    return listed_needed_plugins




#
# Check that plugins listed in the product file are present in the
# target_platform
#
def ExamineProductFile():
    print "Verifying product"
    global AllSymbolicName;
    AllSymbolicName = CollectAllSymbolicNames("target_platform/plugins");
    AllSymbolicName += CollectAllSymbolicNames("..");
    pfile = open("../platform/cml.product");
    content=pfile.readlines();
    pfile.close();
    
    listed_needed_plugins = ListPluginsReferencedInProductFile(content);

    for required_plugin in listed_needed_plugins:
        if not IsBundleInList(required_plugin):
            print required_plugin;
            print "Is listed in the product file but not in the target platform."

#
# Check that if a bundle is Required by some other bundle then that
# bundle is in the cml.product file.
#
def CheckRequiredBundlesAreListedInProduct(d,l):
    global AllSymbolicName;
    pfile = open("../platform/cml.product");
    content=pfile.readlines();
    pfile.close();
    AllSymbolicName = ListPluginsReferencedInProductFile(content);

    bundle_list=[];
    for file in os.listdir(d):
        filePath = d + "/"+file;
        bundles=FindProp(filePath, "Require-Bundle");
        for bundle in SplitBundlesInRequireBundles(bundles):
            if (not IsBundleInList(bundle)):
                if (l):
                    bundle_list.append("<plugin id=\"" + GetNameStripAttrs( bundle ) +"\"/>");
                else:
                    print "Warning:\n\t"+bundle+"\nrequired by\n\t"+file+"\nwere not listed in product file."
    bundle_list = list(set(bundle_list))
    for line in sorted(bundle_list):
       print line;

# Create list of all existing symbolic names for bundles in this
# folder.
#AllSymbolicName=CollectAllSymbolicNames(".");

# Check for closure, e.g. that all required bundles are actually
# present.
#CheckBundleClosure();
#ComputeNeededBundles(".");
#CheckFromBuild2();
#ComputeNeededBundles("..");
#ExamineProductFile();
print "Checking Bundle Dependency Closure:"
CheckFromBuild2();
print "Checking product configuration:"
ExamineProductFile();
CheckRequiredBundlesAreListedInProduct("target_platform/plugins", True);
