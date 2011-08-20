package ws;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public final class ResourceHandle {
    private ResourceHandle(){} 

    public static boolean local = true;
    public static boolean bin = true;

    public static String getString(String name) throws IOException{
        InputStream in = local ? new FileInputStream(name) : ResourceHandle.class.getClassLoader().getResourceAsStream(name);

        BufferedInputStream bis = new BufferedInputStream(in);
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        int result = bis.read();
        while(result != -1) {
            byte b = (byte)result;
            buf.write(b);
            result = bis.read();
        }
        bis.close();
        buf.close();
        return buf.toString();
    }

    public static InputStream getInputStream(String name)throws IOException{
        return new BufferedInputStream( local ? new FileInputStream(name) : ResourceHandle.class.getClassLoader().getResourceAsStream(name) );
    }

    public static URL getURL(String name) throws MalformedURLException {
        return local ? new File(name).toURI().toURL() : ResourceHandle.class.getClassLoader().getResource(name);
    }

}
