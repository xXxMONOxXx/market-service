package edu.azati.marketservice.service.impl;

import edu.azati.marketservice.dto.ImageDto;
import edu.azati.marketservice.dto.ImageResponseDto;
import edu.azati.marketservice.exception.ImageNotFoundException;
import edu.azati.marketservice.exception.ImageServiceException;
import edu.azati.marketservice.model.Image;
import edu.azati.marketservice.repository.FileSystemRepository;
import edu.azati.marketservice.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class ImageService {

    private final FileSystemRepository fileSystemRepository;

    private final ImageRepository imageRepository;

    public void saveImage(ImageDto imageDto) {
        log.info("Trying to save image");
        try {
            imageRepository.save(Image.builder()
                    .serviceId(imageDto.getServiceId())
                    .productId(imageDto.getProductId())
                    .isPreview(imageDto.getIsPreview())
                    .filename(fileSystemRepository.save(imageDto.getImage().getBytes(),
                            imageDto.getImage().getOriginalFilename()))
                    .build());
        } catch (IOException e) {
            log.info("Invalid image");
            throw new ImageServiceException("Could not parse image into bytes");
        }
    }

    public List<ImageResponseDto> mapImagesToImageResponseDto(List<Image> images) {
        return images.stream().map(image -> {
            File file = fileSystemRepository.findFile(image.getFilename());
            try (FileInputStream fl = new FileInputStream(file)) {
                byte[] imageAsBytes = new byte[(int) file.length()];
                fl.read(imageAsBytes);
                return ImageResponseDto.builder()
                        .id(image.getId())
                        .isPreview(image.getIsPreview())
                        .image(imageAsBytes)
                        .build();
            } catch (IOException e) {
                log.info("Could not find file to convert to bytes array");
                throw new ImageServiceException(e.getMessage());
            }
        }).toList();
    }

    public Optional<Image> getPreview(Long targetId, boolean isProduct) {
        Optional<Image> optionalImage;
        if (isProduct) {
            optionalImage = imageRepository.findByProductIdAndIsPreview(targetId, true);
        } else {
            optionalImage = imageRepository.findByServiceIdAndIsPreview(targetId, true);
        }
        return optionalImage;
    }

    public void changePreview(Long id, boolean isProduct) {
        Optional<Image> optionalImage = getPreview(id, isProduct);
        if (optionalImage.isPresent()) {
            Image image = optionalImage.get();
            image.setIsPreview(false);
            imageRepository.save(image);
        }
    }

    public void deleteImage(Long id) {
        log.info("Deleting image from database image");
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new ImageNotFoundException(id));
        imageRepository.delete(image);
        log.info("Deleting image from file storage");
        fileSystemRepository.deleteFile(image.getFilename());
    }
}
