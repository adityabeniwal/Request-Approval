package com.requestapproval.requestapproval.Dto.RequestDTO;

import lombok.Data;

@Data
public class SubmitRequestResponseDto
{
    private int reqId;
    private int revId;
    private String status;
}
