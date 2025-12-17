package com.leoholmer.AllMusic.backend.config;

import com.leoholmer.AllMusic.backend.dto.SongResponseDTO;
import com.leoholmer.AllMusic.backend.model.Song;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Converter para transformar Genre a String
        Converter<?, String> genreToStringConverter = ctx ->
                ctx.getSource() == null ? null : ctx.getSource().toString();

        // Mapeo personalizado entre Song y SongResponseDTO
        TypeMap<Song, SongResponseDTO> typeMap = modelMapper.createTypeMap(Song.class, SongResponseDTO.class);
        typeMap.addMappings(mapper -> {
            mapper.map(Song::getTitle, SongResponseDTO::setName);
            mapper.using(genreToStringConverter).map(Song::getGenre, SongResponseDTO::setGenre);
        });

        return modelMapper;
    }
}
