//package com.desmond.fileutility.utils;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class SenderFactory {
//
//    private static Logger LOGGER = LoggerFactory.getLogger(SenderFactory.class);
//    private GeneralSender generalSender;
//
//    @Autowired
//    public SenderFactory(GeneralSender generalSender) {
//        this.generalSender = generalSender;
//    }
//
//    public Sender getSender(String senderIdentifier) {
//        this.generalSender.setTopic(senderIdentifier);
//        return this.generalSender;
//    }
//}
