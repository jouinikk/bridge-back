package com.example.cars.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {
    @Value("AC9eabe8927720dc4d0ef30aea4977993b")
    private String accountSid;

    @Value("8b7d06b4a684fb05108101cf4f497d70")
    private String authToken;

    @Value("+16085085729")
    private String fromNumber;
    public void sendSms(String to, String message) {
        Twilio.init(accountSid, authToken);
        Message.creator(
                new PhoneNumber(to),
                new PhoneNumber(fromNumber),
                message
        ).create();
    }
}
