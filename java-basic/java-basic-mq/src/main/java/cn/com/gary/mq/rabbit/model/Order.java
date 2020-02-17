/*
 * Copyright (c) 2020 PioneerService, Inc. All Rights Reserved.
 */
package cn.com.gary.mq.rabbit.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2020-02-17 22:28
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order implements java.io.Serializable {
    private Long id;
    private String name;
    private String orderNo;
    private BigDecimal total;

}
