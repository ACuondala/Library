package com.example.nada.Services;

import com.example.nada.Dtos.BookDtos.BookRequestDto;
import com.example.nada.Dtos.BookDtos.BookResponseDto;
import com.example.nada.Mappers.BookMapper;
import com.example.nada.Models.Book;
import com.example.nada.Models.Category;
import com.example.nada.Models.Publisher;
import com.example.nada.Repositories.BookRepository;
import com.example.nada.Repositories.CategoryRepository;
import com.example.nada.Repositories.PublisherRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final PublisherRepository publisherRepository;
    private final BookMapper bookMapper;

    public BookService(
            BookRepository bookRepository,
            BookMapper bookMapper,
            CategoryRepository categoryRepository,
            PublisherRepository publisherRepository
        ){
        this.bookRepository=bookRepository;
        this.bookMapper=bookMapper;
        this.categoryRepository=categoryRepository;
        this.publisherRepository=publisherRepository;
    }

    public Page<BookResponseDto> getAll(Pageable pageable) {
       return this.bookRepository.findAll(pageable).map(this.bookMapper::toDto);
    }

    public BookResponseDto store(BookRequestDto dto) {

        this.findOrCreateCategory(dto.category(),dto.categoryName());

        this.findOrCreatePublisher(dto.publisher(),dto.publisherName());
    }

    public BookResponseDto show(UUID id) {

        Book book= this.bookRepository.findById(id).
                orElseThrow(()-> new EntityNotFoundException("this book id: "+ id+" doesn't exist"));
        return this.bookMapper.toDto(book);
    }

    public BookResponseDto update(UUID id, BookRequestDto dto) {
    }

    public void delete(UUID id) {
        try{
            Book book=this.bookRepository.getReferenceById(id);

            this.bookRepository.delete(book);

        }catch(EntityNotFoundException e){
            throw new EntityNotFoundException("this book id: "+ id+" doesn't exist" );
        }
    }

    public Category findOrCreateCategory(UUID id, String name){

        if(name == null || name.isBlank()){
            return  this.categoryRepository.findById(id)
                    .orElseThrow(()-> new EntityNotFoundException("This category id: "+id+" doesn't exist"));
        }

        return this.categoryRepository.findById(id)
                .orElseGet(()->{
                    Category newCategory= new Category();
                    newCategory.setName(name);
                    return this.categoryRepository.save(newCategory);
                });
                //.orElseThrow(()-> new EntityNotFoundException("This category id: "+id+" doesn't exist"));
    }


    public Publisher findOrCreatePublisher(UUID id,String name){
        if(name.isBlank() || name==null) {
           return this.publisherRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("This publisher id: " + id + " doesn't exist"));
        }

        return this.publisherRepository.findById(id).
                orElseGet(()->{
                    Publisher newPublisher= new Publisher();
                    newPublisher.setName(name);
                    return this.publisherRepository.save(newPublisher);
                });
    }


}
