/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.comcast.logic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.comcast.exceptions.NullObjectParameterException;
import org.comcast.exceptions.UnderflowException;
import org.comcast.router.Message;
import org.comcast.router.OutputChannel;
import org.comcast.structures.BinaryHeap;

/**
 *
 * @author Quality of Service
 */
public class Server implements Comparable<Server>, OutputChannel {

    private BinaryHeap<Message> messageToSend;
    private int serverPriority;
    private FTPClient client;
    private ServerConfig config;

    public Server(ServerConfig c) {
        messageToSend = new BinaryHeap<>();
        client = new FTPClient();
        config = c;
    }

    public Server(BinaryHeap<Message> group, ServerConfig c) {
        messageToSend = group;
        client = new FTPClient();
        config = c;
    }

    public Server(Message[] group, ServerConfig c) {
        messageToSend = new BinaryHeap<>(group);
        client = new FTPClient();
        config = c;
    }

    @Override
    public int compareTo(Server o) {
        if (o == null) {
            throw new NullObjectParameterException("El objeto servidor esta vacio");
        }

        return (this.getServerPriority() - o.getServerPriority());
    }

    private void uploadMessage(Message message) throws SocketException, IOException {

        try {
            FileInputStream fis = null;
            /*client.connect(config.getIpAddress());
             client.login(config.getUserLogin(), config.getPassLogin());*/

            String local = message.getLocalPath();
            String remote = message.getRemotePath();

            client.setFileTransferMode(FTP.BINARY_FILE_TYPE);

            fis = new FileInputStream(local);
            client.storeFile(remote, fis);

            client.noop();

            fis.close();
            /*client.logout();
             client.disconnect();*/

        } catch (SocketException ex) {
            closeConnection();
            throw new SocketException(ex.getLocalizedMessage());
        } catch (IOException ex) {
            closeConnection();
            throw new IOException(ex);
        }
    }

    @Override
    public void uploadMessages() throws SocketException, IOException, UnderflowException {
        Message toSend = null;

        openConnection();

        while (!this.messageToSend.isEmpty()) {
            toSend = this.messageToSend.deleteMin();
            uploadMessage(toSend);
        }

        closeConnection();
    }

    private void openConnection() throws SocketException, IOException {
        client.connect(config.getIpAddress());
        client.login(config.getUserLogin(), config.getPassLogin());
    }

    private void closeConnection() throws SocketException, IOException {
        client.logout();
        client.disconnect();
    }

    private void downloadMessage(Message message) throws SocketException, IOException {
        try {

            FileOutputStream fos = null;

            String local = message.getLocalPath();
            String remote = message.getRemotePath();

            fos = new FileOutputStream(local);
            client.retrieveFile(remote, fos);

            client.noop();
            fos.close();

        } catch (SocketException ex) {
            client.disconnect();
            throw new SocketException(ex.getLocalizedMessage());
        } catch (IOException ex) {
            client.disconnect();
            throw new IOException(ex);
        }
    }

    @Override
    public void downloadMessages() throws SocketException, IOException, UnderflowException {
        Message toSend = null;

        openConnection();

        while (!this.messageToSend.isEmpty()) {
            toSend = this.messageToSend.deleteMin();
            downloadMessage(toSend);
        }

        closeConnection();
    }

    public void downloadSingle(Message mess) throws SocketException, IOException {
        openConnection();

        this.downloadMessage(mess);

        closeConnection();
    }

    @Override
    public FTPFile[] retrieveMesseges(String dir) throws SocketException, IOException {

        openConnection();

        FTPFile[] buffer = client.mlistDir(dir);

        closeConnection();

        return buffer;
    }

    /**
     * @return the serverPriority
     */
    public int getServerPriority() {
        return serverPriority;
    }

    /**
     * @param serverPriority the serverPriority to set
     */
    public void setServerPriority(int serverPriority) {
        this.serverPriority = serverPriority;
    }
}
