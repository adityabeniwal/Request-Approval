package com.requestapproval.requestapproval.Controller;

import com.requestapproval.requestapproval.Dto.RequestDTO.CreateRequestRequestDto;
import com.requestapproval.requestapproval.Dto.RequestDTO.CreateRequestResponseDto;
import com.requestapproval.requestapproval.Dto.RequestDTO.GetRequestDetailsResponseDto;
import com.requestapproval.requestapproval.Service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{reqId}/{revId}")
    public GetRequestDetailsResponseDto getRequestDetails (@PathVariable("reqId")  int reqId,@PathVariable("revId") int revId) throws Exception
    {
         return requestService.GetRequestDetails(reqId,revId);
    }

}
