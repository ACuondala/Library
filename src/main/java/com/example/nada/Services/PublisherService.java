package com.example.nada.Services;

import com.example.nada.Dtos.PublisherDto.PublisherDto;
import com.example.nada.Dtos.PublisherDto.PublisherRequestDto;
import com.example.nada.Exceptions.EntitiesNotFoundException;
import com.example.nada.Mappers.PublisherMapper;
import com.example.nada.Models.Publisher;
import com.example.nada.Repositories.PublisherRespository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PublisherService {
    private final PublisherRespository publisherRespository;

    public PublisherService(PublisherRespository publisherRespository){
        this.publisherRespository=publisherRespository;
    }

    @Transactional(readOnly = true)
    public Page<PublisherDto> getAll(Pageable pageable) {
        return this.publisherRespository.findAll(pageable).map(PublisherMapper::modelToDto);
    }

    @Transactional
    public PublisherDto store(PublisherRequestDto dto){
        Publisher publisher= new Publisher();
        PublisherMapper.dtoToModel(publisher, dto);

        publisher=this.publisherRespository.save(publisher);

        return PublisherMapper.modelToDto(publisher);

    }
    @Transactional(readOnly = true)
    public PublisherDto show(UUID id) {

        Publisher publisher=this.publisherRespository.findById(id).
                orElseThrow(()-> new EntitiesNotFoundException("this publisher doesn't exist"));

        return PublisherMapper.modelToDto(publisher);
    }

    @Transactional
    public void delete(UUID id) {
        try{
            Publisher publisher=this.publisherRespository.getReferenceById(id);
            this.publisherRespository.delete(publisher);
        }catch(EntitiesNotFoundException e){
            throw new EntitiesNotFoundException("this publisher doesn't exist");
        }
    }
}
