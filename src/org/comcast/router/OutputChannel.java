/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.comcast.router;

import java.io.IOException;
import java.io.Serializable;
import java.net.SocketException;
import org.comcast.exceptions.UnderflowException;
import org.comcast.structures.BinaryHeap;
import org.comcast.structures.SimpleList;

/**
 *
 * @author Quality of Service
 */
public interface OutputChannel extends Serializable{
    
    public static final int MAX_PRIORITY = 1;
    public static final int NORMAL_PRIORITY = 5;
    public static final int LOW_PRIORITY = 10;
    
    public void uploadMessage(Message message) throws SocketException, IOException, UnderflowException;
    public void uploadMessages(BinaryHeap<Message> messages) throws SocketException, IOException, UnderflowException;
    
    public void downloadMessage(Message message);
    public void downloadMessages(BinaryHeap<Message> messages);
    
    public Message retrieveMessage();
    public SimpleList<Message> retrieveMesseges();
}