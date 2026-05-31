package com.smartpos.api.model.request;

import com.smartpos.api.common.TableStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTableStatusRequest {

    @NotNull(message = "Table status is required")
    private TableStatus status;
}
