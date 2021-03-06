/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sendemailtls;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import javax.net.SocketFactory;
import javax.net.ssl.*;
/**
 *
 * @author azec
 */

public class DummySSLSocketFactory extends SSLSocketFactory
{
    private SSLSocketFactory factory;

    public DummySSLSocketFactory()
    {
        try
        {
            SSLContext sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(null,
                 new TrustManager[] { new DummyTrustManager()},
                 new java.security.SecureRandom());
            factory = (SSLSocketFactory)sslcontext.getSocketFactory();
        }
        catch(Exception ex)
        {
            // ignore
        }
    }

    public SSLSocketFactory getFactory() {
        return factory;
    }

    public void setFactory(SSLSocketFactory factory) {
        this.factory = factory;
    }

    public static SocketFactory getDefault()
    {
        return new DummySSLSocketFactory();
    }

    @Override
    public Socket createSocket() throws IOException
    {
        return factory.createSocket();
    }

    @Override
    public Socket createSocket(Socket socket, String s, int i, boolean  flag) throws IOException
    {
        return factory.createSocket(socket, s, i, flag);
    }

    @Override
    public Socket createSocket(InetAddress inaddr, int i,
                InetAddress inaddr1, int j) throws IOException
    {
        return factory.createSocket(inaddr, i, inaddr1, j);
    }

    @Override
    public Socket createSocket(InetAddress inaddr, int i)
                throws IOException
    {
        return factory.createSocket(inaddr, i);
    }

    @Override
    public Socket createSocket(String s, int i, InetAddress inaddr, int j)
                throws IOException
    {
        return factory.createSocket(s, i, inaddr, j);
    }

    @Override
    public Socket createSocket(String s, int i) throws IOException
    {
        return factory.createSocket(s, i);
    }

    @Override
    public String[] getDefaultCipherSuites()
    {
        return factory.getDefaultCipherSuites();
    }

    @Override
    public String[] getSupportedCipherSuites()
    {
        return factory.getSupportedCipherSuites();
    }
}