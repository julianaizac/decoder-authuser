package com.izac.ead.authuser.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Map;

@JsonInclude(Include.NON_NULL)
public record ErrorRecordResponse(int errorCode,
                                  String errorMessage,
                                  Map<String, String> errorDetails) {
}
