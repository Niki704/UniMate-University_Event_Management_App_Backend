package com.unimate.config;

import com.unimate.dto.AnnouncementRequestDTO;
import com.unimate.model.Announcement;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper customModelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Example: Custom mapping logic (if necessary)
        modelMapper.addMappings(new PropertyMap<AnnouncementRequestDTO, Announcement>() {
            @Override
            protected void configure() {
                // Skip the 'id' field during the mapping (it is auto-generated)
                skip(destination.getId());

                // Explicitly map teacherID and eventID
                map(source.getTeacherID(), destination.getTeacherID());
                map(source.getEventID(), destination.getEventID());
            }
        });

        return modelMapper;
    }
}
