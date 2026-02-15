package com.anson.internshiptracker.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EmailClassifierTest {
    
    @Test
    public void testOfferClassification() {
        String result = EmailClassifier.classify("Job Offer from Google", "");
        assertEquals("OFFER", result);
        
        result = EmailClassifier.classify("", "We are pleased to offer you the position");
        assertEquals("OFFER", result);
    }
    
    @Test
    public void testInterviewClassification() {
        String result = EmailClassifier.classify("Interview Invitation", "");
        assertEquals("INTERVIEW", result);
        
        result = EmailClassifier.classify("", "We would like to schedule an interview");
        assertEquals("INTERVIEW", result);
    }
    
    @Test
    public void testRejectionClassification() {
        String result = EmailClassifier.classify("Application Update", "");
        assertEquals("OTHER", result); // Generic subject
        
        result = EmailClassifier.classify("", "Unfortunately, we will not be moving forward");
        assertEquals("REJECTED", result);
    }
    
    @Test
    public void testOtherClassification() {
        String result = EmailClassifier.classify("Random Email", "Just checking in");
        assertEquals("OTHER", result);
    }
    
    @Test
    public void testNullAndEmpty() {
        String result = EmailClassifier.classify("", "");
        assertEquals("OTHER", result);
        
        result = EmailClassifier.classify(null, null);
        assertEquals("OTHER", result);
    }
    
    @Test
    public void testCaseInsensitivity() {
        String result = EmailClassifier.classify("OFFER FROM AMAZON", "");
        assertEquals("OFFER", result);
        
        result = EmailClassifier.classify("", "INTERVIEW SCHEDULED");
        assertEquals("INTERVIEW", result);
    }
}
