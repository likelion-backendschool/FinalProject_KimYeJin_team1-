package com.yejin.exam.wbook.domain.cart.entity;

import com.yejin.exam.wbook.domain.member.entity.Member;
import com.yejin.exam.wbook.domain.product.entity.Product;
import com.yejin.exam.wbook.domain.product.entity.ProductOption;
import com.yejin.exam.wbook.global.base.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class CartItem extends BaseEntity {
    @ManyToOne(fetch = LAZY)
    private Member buyer;
    @ManyToOne(fetch = LAZY)
    private Product product;
    private int quantity; // 쇼핑몰에서 보유한 물건 개수
}