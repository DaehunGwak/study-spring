package io.ordi.jpabook.jpashop.service;

import io.ordi.jpabook.jpashop.domain.item.Book;
import io.ordi.jpabook.jpashop.domain.item.Item;
import io.ordi.jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveBook(String name, int price, int stockQuantity, String author, String isbn) {
        Book book = new Book();
        book.update(name, price, stockQuantity, author, isbn);
        saveItem(book);
    }

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Transactional
    public void updateBook(Long id, String name, int price, int stockQuantity, String author, String isbn) {
        Book book = (Book) itemRepository.findOne(id);
        book.update(name, price, stockQuantity, author, isbn);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
