//package com.desmond.fileutility.utils;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.io.FileNotFoundException;
//import java.text.ParseException;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest
////@ExtendWith(SpringExtension.class)
//public class ValidateInputsTests {
//
////    @Autowired
//    private ValidateInputs validateInputs;
//
//    @Mock
//    private FileUtility fileUtility;
//
////    @Autowired
////    public ValidateInputsTests (ValidateInputs validateInputs) {
////        this.validateInputs = validateInputs;
////    }
//
//    @BeforeEach
//    public void setUp() throws FileNotFoundException {
//        this.validateInputs = new ValidateInputs(fileUtility);
//        String inputDirectory = "/test/dummy";
//        Mockito.when(fileUtility.checkIfFileDirectoryExist(inputDirectory)).thenReturn(true);
//    }
//
//    @DisplayName("Test Get Length Of Byte")
//    @Test
//    public void testValidateCompressionInputs() throws FileNotFoundException, ParseException {
//
//
//        String[] args = new String[] {"test/dummyIn", "test/dummyOut", "10"};
//        String inputDirectory = "/test/dummy";
//
//
//        assertEquals(true, validateInputs.validateCompressionInputs(args));
//    }
//}
