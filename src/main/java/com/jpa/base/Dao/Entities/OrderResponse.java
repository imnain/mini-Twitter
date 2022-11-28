package com.jpa.base.Dao.Entities;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderResponse {
    private String name;
    private String description;
    private int tId;

}
