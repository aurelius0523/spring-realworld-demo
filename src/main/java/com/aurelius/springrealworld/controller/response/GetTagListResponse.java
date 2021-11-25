package com.aurelius.springrealworld.controller.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetTagListResponse {
    private List<String> tags;
}
