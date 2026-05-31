package com.smartpos.api.model.response;

import com.smartpos.api.common.TableStatus;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TableResponse {
    private Long id;
    private String tableNumber;
    private Integer capacity;
    private TableStatus status;
}

