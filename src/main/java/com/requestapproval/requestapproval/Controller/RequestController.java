package com.requestapproval.requestapproval.Controller;

import com.requestapproval.requestapproval.Dto.RequestDTO.*;
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

    @PostMapping("submitRequest/{reqId}/{revId}")
    public SubmitRequestResponseDto submitRequest (@PathVariable("reqId")  int reqId,@PathVariable("revId") int revId , @RequestBody SubmitRequestRequestDto submitRequestRequestDto)
    {
        return requestService.SubmitRequest(reqId,revId,submitRequestRequestDto);
    }

    @PostMapping("revokeRequest/{reqId}/{revId}")
    public RevokeRequestResponseDto revokeRequest (@PathVariable("reqId")  int reqId,@PathVariable("revId") int revId)
    {
        return requestService.RevokeRequest(reqId,revId);
    }

    @PostMapping("newRevision/{reqId}/{revId}")
    public NewRevisionResponseDto newRevision (@PathVariable("reqId")  int reqId,@PathVariable("revId") int revId)
    {
        return requestService.NewRevision(reqId,revId);
    }

}
