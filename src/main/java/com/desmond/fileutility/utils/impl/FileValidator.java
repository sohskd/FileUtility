package com.desmond.fileutility.utils.impl;

import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class FileValidator {

    public boolean isHiddenFile(File file) {
        return file.isHidden();
    }

    public int getLengthOfByte(int totalByteLengthOfFile, int off, int len) {
        int ans;
        if (off + len > totalByteLengthOfFile)
            ans = totalByteLengthOfFile - off;
        else
            ans = len;
        return ans;
    }

    public int getLengthOfByteBigFile(int totalByteLengthOfFile, int off, int len) {
        int ans;
        if (totalByteLengthOfFile - off < len)
            ans = totalByteLengthOfFile - off;
        else
            ans = len;
        return ans;
    }
}
