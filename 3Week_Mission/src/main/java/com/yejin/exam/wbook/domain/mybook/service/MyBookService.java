package com.yejin.exam.wbook.domain.mybook.service;

import com.yejin.exam.wbook.domain.mybook.entity.MyBook;
import com.yejin.exam.wbook.domain.mybook.repository.MyBookRepository;
import com.yejin.exam.wbook.domain.order.entity.Order;
import com.yejin.exam.wbook.global.result.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyBookService {
    private final MyBookRepository myBookRepository;

    @Transactional
    public ResultResponse add(Order order) {
        order.getOrderItems()
                .stream()
                .map(orderItem -> MyBook.builder()
                        .owner(order.getBuyer())
                        .orderItem(orderItem)
                        .product(orderItem.getProduct())
                        .build())
                .forEach(myBookRepository::save);

        return ResultResponse.of("S-1", "나의 책장에 추가되었습니다.");
    }

    @Transactional
    public ResultResponse remove(Order order) {
        order.getOrderItems()
                .stream()
                .forEach(orderItem -> myBookRepository.deleteByProductIdAndOwnerId(orderItem.getProduct().getId(), order.getBuyer().getId()));

        return ResultResponse.of("S-1", "나의 책장에서 제거되었습니다.");
    }
}