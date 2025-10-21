package com.example.nada.Services;

import com.example.nada.Dtos.PublisherDto.PublisherRequestDto;
import com.example.nada.Dtos.PublisherDto.PublisherResponseDto;
import com.example.nada.Mappers.PublisherMapper;
import com.example.nada.Models.Publisher;
import com.example.nada.Repositories.PublisherRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;
    private final PublisherMapper publisherMapper;

    public PublisherService(PublisherRepository publisherRepository,PublisherMapper publisherMapper){
        this.publisherRepository=publisherRepository;
        this.publisherMapper=publisherMapper;
    }

    @Transactional(readOnly = true)
    public Page<PublisherResponseDto> getAll(Pageable pageable) {
        return this.publisherRepository.findAll(pageable).map(this.publisherMapper::toDto);
    }

    @Transactional
    public PublisherResponseDto store(PublisherRequestDto dto) {
        Publisher publisher=this.publisherMapper.toEntity(dto);
        return this.publisherMapper.toDto(this.publisherRepository.save(publisher));
    }

    @Transactional(readOnly = true)
    public PublisherResponseDto show(UUID id) {
        Publisher publisher=this.publisherRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("this publisher id: "+id+" doesn't exist"));
        return this.publisherMapper.toDto(publisher);

    }

    @Transactional
    public PublisherResponseDto update(UUID id, PublisherRequestDto dto) {
        try{
            Publisher publisher=this.publisherRepository.getReferenceById(id);
            this.publisherMapper.updatePublisher(dto,publisher);
            return this.publisherMapper.toDto(this.publisherRepository.save(publisher));
        }catch(EntityNotFoundException e){
            throw new EntityNotFoundException("this publisher id: "+id+" doesn't exist");
        }
    }

    @Transactional
    public void delete(UUID id) {
        try{
            Publisher publisher=this.publisherRepository.getReferenceById(id);
            this.publisherRepository.delete(publisher);

        }catch(EntityNotFoundException e){
            throw new EntityNotFoundException("this publisher id: "+id+" doesn't exist");
        }
    }
}
