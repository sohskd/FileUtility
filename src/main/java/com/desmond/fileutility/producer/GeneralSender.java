//package com.desmond.fileutility.producer;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.Data;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.stream.binding.BinderAwareChannelResolver;
//import org.springframework.cloud.stream.messaging.Source;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.support.MessageBuilder;
//import org.springframework.stereotype.Service;
//
//import java.io.*;
//
//@Data
//@Service
//public class GeneralSender implements Sender {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(GeneralSender.class);
//    private static Logger ERRORLOGGER = LoggerFactory.getLogger("errorLogger");
//
//
//    private ObjectMapper objectMapper;
//    private BinderAwareChannelResolver resolver;
//    private Source source;
//    private String data;
//    private String topic;
//
//    @Autowired
//    public GeneralSender(ObjectMapper objectMapper, BinderAwareChannelResolver resolver, Source source) {
//        this.objectMapper = objectMapper;
//        this.resolver = resolver;
//        this.source = source;
//    }
//
////    @Override
////    public void send() {
////        Message<String> message = MessageBuilder
////                .withPayload(this.data)
////                .build();
////        boolean isSent = this.resolver.resolveDestination(this.topic)
////                .send(message);
////        if (!isSent) {
////            LOGGER.info("Data not sent.");
////            ERRORLOGGER.info("Data not sent.");
////        }
////    }
//
//    @Override
//    public void send() {
//        Message<String> message = MessageBuilder
//                .withPayload(this.data)
//                .build();
//        Boolean isSent = this.source.output().send(message);
//        if (!isSent) {
//            LOGGER.info("Data not sent.");
//            ERRORLOGGER.info("Data not sent.");
//        }
//    }
//
//    @Override
//    public void prepareData(File f) {
//        this.data = null;
//        JSONParser jsonParser = new JSONParser();
//        try(Reader reader = new FileReader(f)) {
//            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
//            String jsonObjectStr = String.valueOf(jsonObject);
//            this.data = jsonObjectStr;
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }
//}
