package com.portofolio.demo.models.json.order;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class ChangeOrderStatusRequestJson {

    @NotNull(message = "Status must not be null")
    @Pattern(regexp = "(?:^|(?<= ))(DRAFT|PROCESSING|DONE)(?:(?= )|$)", message = "Status possible values: DRAFT, PROCESSING,DONE")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
