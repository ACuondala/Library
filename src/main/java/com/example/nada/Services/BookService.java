package com.example.nada.Services;

import com.example.nada.Dtos.BookDtos.BookRequestDto;
import com.example.nada.Dtos.BookDtos.BookResponseDto;
import com.example.nada.Mappers.BookMapper;
import com.example.nada.Models.Author;
import com.example.nada.Models.Book;
import com.example.nada.Models.Category;
import com.example.nada.Models.Publisher;
import com.example.nada.Repositories.AuthorRepository;
import com.example.nada.Repositories.BookRepository;
import com.example.nada.Repositories.CategoryRepository;
import com.example.nada.Repositories.PublisherRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final PublisherRepository publisherRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;

    public BookService(
            BookRepository bookRepository,
            BookMapper bookMapper,
            CategoryRepository categoryRepository,
            PublisherRepository publisherRepository,
            AuthorRepository authorRepository
        ){
        this.bookRepository=bookRepository;
        this.bookMapper=bookMapper;
        this.categoryRepository=categoryRepository;
        this.publisherRepository=publisherRepository;
        this.authorRepository=authorRepository;
    }

    @Transactional(readOnly = true)
    public Page<BookResponseDto> getAll(Pageable pageable) {
       return this.bookRepository.findAll(pageable).map(this.bookMapper::toDto);
    }

    @Transactional
    public BookResponseDto store(BookRequestDto dto) {

       Category category= this.findOrCreateCategory(dto.category(),dto.categoryName());

       Publisher publisher= this.findOrCreatePublisher(dto.publisher(),dto.publisherName());

       List<Author> authors=this.findOrCreateAuthors(dto.authors(),dto.authorsName());

        Book book= this.bookMapper.toEntity(dto);
        book.setAuthors(authors);
        book.setCategory(category);
        book.setPublisher(publisher);

        bookRepository.save(book);

        return this.bookMapper.toDto(book);


    }

    @Transactional(readOnly = true)
    public BookResponseDto show(UUID id) {

        Book book= this.bookRepository.findById(id).
                orElseThrow(()-> new EntityNotFoundException("this book id: "+ id+" doesn't exist"));
        return this.bookMapper.toDto(book);
    }

    @Transactional
    public BookResponseDto update(UUID id, BookRequestDto dto) {

            Book book= bookRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Book id: " + id + " not found"));
            if(dto.category() != null || ! dto.categoryName().isBlank()) {
                Category category = this.findOrCreateCategory(dto.category(), dto.categoryName());
                book.setCategory(category);
            }
            if(dto.publisher() != null || ! dto.publisherName().isBlank()) {
                Publisher publisher = this.findOrCreatePublisher(dto.publisher(), dto.publisherName());
                book.setPublisher(publisher);
            }

            if(dto.authors() != null || !dto.authorsName().isEmpty()) {
                List<Author> authors = this.findOrCreateAuthors(dto.authors(), dto.authorsName());
                book.setAuthors(authors);

            }


            this.bookMapper.updateBook(dto,book);
            this.bookRepository.save(book);
            return this.bookMapper.toDto(book);

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

        if(id != null){
            return  this.categoryRepository.findById(id)
                    .orElseThrow(()-> new EntityNotFoundException("This category id: "+id+" doesn't exist"));
        }
        if(name != null && !name.isBlank()) {
            return this.categoryRepository.findByNameIgnoreCase(name)
                    .orElseGet(() -> {
                        Category newCategory = new Category();
                        newCategory.setName(name);
                        return this.categoryRepository.save(newCategory);
                    });
            //.orElseThrow(()-> new EntityNotFoundException("This category id: "+id+" doesn't exist"));
        }
        throw new IllegalArgumentException("Either category ID or name must be provided");

    }


    public Publisher findOrCreatePublisher(UUID id,String name){
        if(id != null) {
           return this.publisherRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("This publisher id: " + id + " doesn't exist"));
        }

        if(name != null && !name.isBlank()) {
            return this.publisherRepository.findByNameIgnoreCase(name).
                    orElseGet(() -> {
                        Publisher newPublisher = new Publisher();
                        newPublisher.setName(name);
                        return this.publisherRepository.save(newPublisher);
                    });
        }
        throw new IllegalArgumentException("Either publisher ID or name must be provided");

    }

    public List<Author> findOrCreateAuthors(List<UUID>id, List<String>names){
        List<Author>authors= new ArrayList<>();

        if (id != null && !id.isEmpty()) {
            authors.addAll(authorRepository.findAllById(id));
        }
        if(names != null && !names.isEmpty()){
            for(String name:names){
                Author existingAuthorName=this.authorRepository.findByNameIgnoreCase(name).orElse(null);

                if(existingAuthorName != null){
                    authors.add(existingAuthorName);
                }

               Author author= new Author();
               author.setName(name);
               authors.add(this.authorRepository.save(author));
            }
        }
        return authors;
    }


}
