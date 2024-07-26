package com.requestapproval.requestapproval.Utils;

import org.springframework.stereotype.Service;

import java.util.Objects;
@Service
public class BasicUtils {
    public Boolean checkNullOrBlank(String input){
        if(Objects.isNull(input)) return true;
        input = input.trim();
        return input.equals("");
    }

}
