package org.control;

import javax.jms.JMSException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws JMSException{
        EventReceiver eventReceiver = new EventReceiver();
        List<String> listaDeEventos = eventReceiver.recieve();
        System.out.println(listaDeEventos.toString());
    }
}