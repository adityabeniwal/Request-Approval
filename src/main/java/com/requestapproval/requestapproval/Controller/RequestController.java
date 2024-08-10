package com.requestapproval.requestapproval.Controller;

import com.requestapproval.requestapproval.Dto.Request.CreateRequestRequestDto;
import com.requestapproval.requestapproval.Dto.Request.CreateRequestResponseDto;
import com.requestapproval.requestapproval.Service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/request")
public class RequestController
{
    @Autowired
    RequestService requestService;

    @PostMapping("/createRequest")
    public CreateRequestResponseDto createRequest(@RequestBody CreateRequestRequestDto createRequestRequestDto)
    {
         return requestService.CreateRequest(createRequestRequestDto);
    }

}
