package com.sms;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.modelmapper.AbstractConverter;
import org.modelmapper.AbstractProvider;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.Provider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sms.domain.ContactDomain;
import com.sms.model.ContactModel;
import com.sms.util.CommonLiterals;

@Configuration
public class ApplicationConfig {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new CustomModelMapper();
		stringToLocalDateTimeConverter(modelMapper);
		localDateToStringConverter(modelMapper);
		addCustomIgnoreCases(modelMapper);
		return modelMapper;
	}

	class CustomModelMapper extends ModelMapper {
		@Override
		public <D> D map(Object source, Class<D> destinationType) {
			if (source == null)
				return null;

			return super.map(source, destinationType);
		}
	}

	private void addCustomIgnoreCases(ModelMapper modelMapper) {

		modelMapper.addMappings(new PropertyMap<ContactModel, ContactDomain>() {
			@Override
			protected void configure() {
				skip(destination.getRequests());
				skip(destination.getGroups());

			}
		});

	}

	public static void stringToLocalDateTimeConverter(ModelMapper modelMapper) {
		Provider<LocalDateTime> localDateProvider = new AbstractProvider<LocalDateTime>() {
			@Override
			public LocalDateTime get() {
				return LocalDateTime.now();
			}
		};

		AbstractConverter<String, LocalDateTime> toStringDate = new AbstractConverter<String, LocalDateTime>() {
			@Override
			protected LocalDateTime convert(String source) {
				DateTimeFormatter format = DateTimeFormatter.ofPattern(CommonLiterals.DATE_FORMAT_YMD_HHMMSS);
				return source == null ? LocalDateTime.now() : LocalDateTime.parse(source, format);
			}
		};

		modelMapper.createTypeMap(String.class, LocalDateTime.class);
		modelMapper.addConverter(toStringDate);
		modelMapper.getTypeMap(String.class, LocalDateTime.class).setProvider(localDateProvider);
	}

	public static void localDateToStringConverter(ModelMapper modelMapper) {
		Provider<String> localDateProvider = new AbstractProvider<String>() {
			@Override
			public String get() {
				DateTimeFormatter format = DateTimeFormatter.ofPattern(CommonLiterals.DATE_FORMAT_YMD_HHMMSS);
				return format.format(LocalDateTime.now());
			}
		};

		AbstractConverter<LocalDateTime, String> toStringDate = new AbstractConverter<LocalDateTime, String>() {
			@Override
			protected String convert(LocalDateTime source) {
				DateTimeFormatter format = DateTimeFormatter.ofPattern(CommonLiterals.DATE_FORMAT_YMD_HHMMSS);
				return source == null ? "" : source.format(format);
			}
		};

		modelMapper.createTypeMap(LocalDateTime.class, String.class);
		modelMapper.addConverter(toStringDate);
		modelMapper.getTypeMap(LocalDateTime.class, String.class).setProvider(localDateProvider);
	}
}
