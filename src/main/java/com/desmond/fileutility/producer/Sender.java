package com.desmond.fileutility.producer;

import org.springframework.stereotype.Service;

import java.io.File;

@Service
public interface Sender {

    void send();

    void prepareData(File f);
}
