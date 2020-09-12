/* (c) Copyright 2020 Paul Nguyen. All Rights Reserved */

package umlparser ;

import java.io.Console;
import java.io.File;


/**
 * Main Entry Point.
 */
final class Main {

    static boolean DEBUG = false ;

    /**
     * Prevent Construction.
     */
    private Main() {
        // Utility Class
        return ;
    }

    /**
     * Create Dummy File
     * @param dir directory location for file
     * @param file file to create (.ext included)
     */
    private static void touch ( String dir, String filename ) {
        File file = new File( dir, filename ) ;
        if ( !file.exists() ) {
            try { 
                file.createNewFile()  ;
            } catch ( Exception e ) {
                System.out.println( e ) ;
            }
        }
    }

    /**
     * Main App Entry Point.
     * @param args No args expected.
     */
    public static void main(final String[] args) {
        String basedir = "" ;
        String testdir = "" ;
        String testcase = "" ;
        String outdir = "" ;
        if ( DEBUG ) {
            System.out.printf( "[DEBUG] UML Parser #Args: %d%n", args.length ) ;
            for (int i = 0; i < args.length; i++)  {
                System.out.printf( "[DEBUG] args[%d] = %s%n", i, args[i] );
            }
        }
        if ( args.length == 2 ) {
            basedir = args[0] ;
            testcase = args[1] ;
            testdir = basedir + File.separator + testcase ;
            outdir = basedir + File.separator + "output" + File.separator + testcase ;
            System.out.printf("basedir: %s%n", basedir) ;
            System.out.printf("testdir: %s%n", testdir) ;
            System.out.printf("outdir: %s%n", outdir) ;
            if ( testcase.equals("sequence") ) {
                touch(outdir, testcase+".seq") ;    // sequence source
                touch(outdir, testcase+".jpg") ;    // sequence output
            } else {
                touch(outdir, testcase+".java") ;   // umlgraph source
                touch(outdir, testcase+".png") ;    // umlgraph output
            }
        } else {
             System.out.println( "java -cp [jar file].jar umlparser.Main [base directory] [test case]" ) ;
        }


    }

}

