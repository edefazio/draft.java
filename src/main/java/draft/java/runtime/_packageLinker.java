package draft.java.runtime;

import draft.DraftException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

/**
 * Links a package at runtime (call
 * @link ClassLoader#definePackage(String, String, String, String, String, String, String, URL
 * which makes the package accessible in the JVM runtime
 *
 * @author Eric
 */
public final class _packageLinker {

    /**
     * https://docs.oracle.com/javase/tutorial/deployment/jar/packageman.html
     *
     * this class creates an entity as part of the JLS
     * provides versioning information on the package level
     * it is written into the "META-INF/manifest.mf"
     *
     * and the properties are accessible at runtime
     *
     * Name: java/util/
     * Specification-Title: Java Utility Classes
     * Specification-Version: 1.2
     * Specification-Vendor: Example Tech, Inc.
     * Implementation-Title: java.util
     * Implementation-Version: build57
     * Implementation-Vendor: Example Tech, Inc.
     */
    public static class _versionSpecification{
        public String name;
        public String specificationTitle = "";
        public String specificationVersion = "";
        public String specificationVendor = "";
        public String implementationTitle = "";
        public String implementationVersion = "";
        public String implementationVendor = "";
        public URL sealBase = null;

        public static _versionSpecification of( String pkgName ){
            _versionSpecification _pv =
                    new _versionSpecification( pkgName );
            _pv.implementationTitle = "draftAutoPackage";
            _pv.implementationVendor = pkgName;
            _pv.implementationVersion = "1.0";
            _pv.specificationVersion = "1.0";
            _pv.specificationTitle = _versionSpecification.class.getCanonicalName();
            _pv.specificationVendor = pkgName;
            _pv.sealBase = null;
            return _pv;
        }

        //i.e. versionSpecification
        public _versionSpecification( String name ){
            //this.NAME = NAME.load( ".", "/");
            this.name = name.replace( '/', '.' );
        }

        @Override
        public String toString(){
            StringBuilder sb = new StringBuilder();
            sb.append("Name: "); sb.append(name); sb.append( System.lineSeparator() );
            sb.append("Specification-Title: "); sb.append(this.specificationTitle); sb.append( System.lineSeparator() );
            sb.append("Specification-Version: ");sb.append(specificationVersion);sb.append( System.lineSeparator() );
            sb.append("Specification-Vendor: ");sb.append(specificationVendor);sb.append( System.lineSeparator() );

            sb.append("Implementation-Title: ");sb.append(implementationTitle);sb.append( System.lineSeparator() );
            sb.append("Implementation-Version: ");sb.append(implementationVersion);sb.append( System.lineSeparator() );
            sb.append("Implementation-Vendor: ");sb.append(implementationVendor);sb.append( System.lineSeparator() );
            if( this.sealBase != null ){
                sb.append("Sealed: true"); sb.append( System.lineSeparator() );
            }
            return sb.toString();
        }
    }



    /**
     * This is a reference to the method :
     * @link ClassLoader#definePackage(String, String, String, String, String, String, String, URL
     *
     * which is protected by default.
     *
     * we have to "hack" the JVM accessibility to make this method available
     * to us at runtime (so we can define Packages into a ClassLoader
     * that we dont "own")
     *
     * NOTE: the NAME "definePackage" is badly worded, what the method ACTUALLY
     * does is creates an instance of a Package and LINKS it to a specific
     * {@link ClassLoader} instance... should be "createAndLinkPackage(...)"
     * or something
     */
    private static final Method DEFINE_PACKAGE_METHOD;

    static {
        try {
            DEFINE_PACKAGE_METHOD
                    = ClassLoader.class.getDeclaredMethod( "definePackage",
                    String.class, //NAME
                    String.class, //specTitle,
                    String.class, // specVersion,
                    String.class, // specVendor,
                    String.class, // implTitle,
                    String.class, // implVersion,
                    String.class, // implVendor,
                    URL.class ); // sealBase))

            DEFINE_PACKAGE_METHOD.setAccessible( true );
        }
        catch( NoSuchMethodException e ) {
            throw new AssertionError( e );
        }
    }


    public static Package of(
            ClassLoader targetClassLoader, _versionSpecification pkgVersionSpec ){
        try {
            return doLinkPackage(targetClassLoader, pkgVersionSpec );
        }
        catch( IllegalAccessException ex ) {
            throw new DraftException("could not link package "+ pkgVersionSpec, ex );
        }
        catch( IllegalArgumentException ex ) {
            throw new DraftException("could not link package "+ pkgVersionSpec, ex );
        }
        catch( InvocationTargetException ex ) {
            throw new DraftException("could not link package "+ pkgVersionSpec, ex );
        }
    }

    private static Package doLinkPackage(
            ClassLoader targetClassLoader,
            _versionSpecification pkgVersionSpec )
            throws IllegalAccessException,
            IllegalArgumentException,
            InvocationTargetException {

        return (Package)DEFINE_PACKAGE_METHOD.invoke(targetClassLoader,
                pkgVersionSpec.name,
                pkgVersionSpec.specificationTitle,
                pkgVersionSpec.specificationVersion,
                pkgVersionSpec.specificationVendor,
                pkgVersionSpec.implementationTitle,
                pkgVersionSpec.implementationVersion,
                pkgVersionSpec.implementationVendor,
                pkgVersionSpec.sealBase );
    }

    /**
     * Call the definePackage method on a given targetClassLoader instance (Probably
     * the parent targetClassLoader of the _classLoader2)
     *
     * @param targetClassLoader the targetClassLoader to publish the package
     * @param packageSpec the definition of the package
     * @return the Package Object that was created
     */
    public static Package of(
            ClassLoader targetClassLoader, Package packageSpec )
            throws DraftException {
        try {
            Package adHocPackage = (Package)DEFINE_PACKAGE_METHOD
                    .invoke(
                            targetClassLoader,
                            packageSpec.getName(),
                            packageSpec.getSpecificationTitle(),
                            packageSpec.getSpecificationVersion(),
                            packageSpec.getSpecificationVendor(),
                            packageSpec.getImplementationTitle(),
                            packageSpec.getImplementationVersion(),
                            packageSpec.getImplementationVendor(),
                            null ); // sealBase))

            //System.out.println( "SEALED " + adHocPackage.isSealed() );
            return adHocPackage;

        }
        catch( IllegalAccessException e ) {
            throw new DraftException( "Illegal Access defining package", e );
        }
        catch( InvocationTargetException e ) {
            //noinspection ThrowInsideCatchBlockWhichIgnoresCaughtException
            throw new DraftException(
                    "Invocation Target Exception definining package", e.getCause() );
        }
    }
}
