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

    @PostMapping("/submitRequest/{reqId}/{revId}")
    public SubmitRequestResponseDto submitRequest (@PathVariable("reqId")  int reqId,@PathVariable("revId") int revId , @RequestBody SubmitRequestRequestDto submitRequestRequestDto)
    {
        return requestService.SubmitRequest(reqId,revId,submitRequestRequestDto);
    }

    @PostMapping("/revokeRequest/{reqId}/{revId}")
    public RevokeRequestResponseDto revokeRequest (@PathVariable("reqId")  int reqId,@PathVariable("revId") int revId)
    {
        return requestService.RevokeRequest(reqId,revId);
    }

    @PostMapping("/newRevision/{reqId}/{revId}")
    public NewRevisionResponseDto newRevision (@PathVariable("reqId")  int reqId,@PathVariable("revId") int revId)
    {
        return requestService.NewRevision(reqId,revId);
    }

    @PostMapping("/{reqId}/{revId}/approve")
    public ApproveRequestResponseDto approveRequest(@PathVariable("reqId")  int reqId,@PathVariable("revId") int revId, @RequestBody ApproveRequestRequestDto approveRequestRequestDto) throws Exception
    {
        return requestService.ApproveRequest(reqId,revId,approveRequestRequestDto);

    }

    @PostMapping("/{reqId}/{revId}/decline")
    public DeclineRequestResponseDto declineRequest(@PathVariable("reqId")  int reqId,@PathVariable("revId") int revId, @RequestBody DeclineRequestRequestDto declineRequestRequestDto) throws Exception
    {
        return requestService.DeclineRequest(reqId,revId,declineRequestRequestDto);

    }
    @PostMapping("/update/{reqId}/{revId}")
    public UpdateRequestResponseDto updateRequest(@PathVariable("reqId")  int reqId,@PathVariable("revId") int revId, @RequestBody UpdateRequestRequestDto updateRequestRequestDto) throws Exception
    {
        return requestService.UpdateRequest(reqId,revId,updateRequestRequestDto);

    }
}
