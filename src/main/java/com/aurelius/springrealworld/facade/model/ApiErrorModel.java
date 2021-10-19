package com.aurelius.springrealworld.facade.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ApiErrorModel {
    private List<String> body;
}
