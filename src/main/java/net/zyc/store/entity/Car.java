package net.zyc.store.entity;

import lombok.Data;

@Data
public class Car extends BaseEntity {
    private Integer cid;
    private Integer uid;
    private Integer pid;
    private Long price;
    private Integer num;
}
