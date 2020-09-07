package com.erp.mastro.model.request;

import com.erp.mastro.entities.Branch;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
public class DeliveryChellanRequestModel {

    private Long id;
    private Branch branch;
}
