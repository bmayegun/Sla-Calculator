package org.biz.slaapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SlaResponse {
    private LocalDateTime dueDateTime;
}
