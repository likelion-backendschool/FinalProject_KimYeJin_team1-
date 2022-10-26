package com.yejin.exam.wbook.domain.cart.service;

import com.yejin.exam.wbook.domain.cart.entity.CartItem;
import com.yejin.exam.wbook.domain.cart.repository.CartItemRepository;
import com.yejin.exam.wbook.domain.member.entity.Member;
import com.yejin.exam.wbook.domain.product.entity.Product;
import com.yejin.exam.wbook.domain.product.entity.ProductOption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {
    private final CartItemRepository cartItemRepository;

    public CartItem addItem(Member member, ProductOption productOption, int quantity) {
        CartItem oldCartItem = cartItemRepository.findByMemberIdAndProductOptionId(member.getId(), productOption.getId()).orElse(null);

        if ( oldCartItem != null ) {
            oldCartItem.setQuantity(oldCartItem.getQuantity() + quantity);
            cartItemRepository.save(oldCartItem);

            return oldCartItem;
        }
        CartItem cartItem = CartItem.builder()
                .member(member)
                .productOption(productOption)
                .quantity(quantity)
                .build();

        cartItemRepository.save(cartItem);
        return cartItem;
    }
    public List<CartItem> getItemsByMember(Member member) {
        return cartItemRepository.findAllByMemberId(member.getId());
    }

    public void deleteItem(CartItem cartItem) {
        cartItemRepository.delete(cartItem);
    }

    public boolean hasItem(Member member, Product product) {
        return cartItemRepository.existsByMemberIdAndProductId(member.getId(), product.getId());
    }
    @Transactional
    public boolean removeItem(Member member, Product product) {
        CartItem oldCartItem = cartItemRepository.findByMemberIdAndProductId(member.getId(), product.getId()).orElse(null);
        if (oldCartItem != null) {
            cartItemRepository.delete(oldCartItem);
            return true;
        }
        return false;
    }
    public void removeItem(CartItem cartItem) {
        cartItemRepository.delete(cartItem);
    }

    public void removeItem(
            Member member,
            Long productId
    ) {
        Product product = new Product(productId);
        removeItem(member, product);
    }
}