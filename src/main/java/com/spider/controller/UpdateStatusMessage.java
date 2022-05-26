package com.spider.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pengmin
 * @date 2022/3/16 23:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateStatusMessage {
    private String srcId;

    private String status;
}
