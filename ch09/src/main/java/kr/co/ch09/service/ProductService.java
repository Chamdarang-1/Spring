package kr.co.ch09.service;


import kr.co.ch09.dto.PageRequestDTO;
import kr.co.ch09.dto.PageResponseDTO;
import kr.co.ch09.dto.ProductDTO;
import kr.co.ch09.entity.Product;
import kr.co.ch09.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public int register(ProductDTO productDTO){

        Product product = productDTO.toEntity();
        Product savedProduct = productRepository.save(product);

        return savedProduct.getPno();
    }

    public PageResponseDTO<ProductDTO> list(PageRequestDTO pageRequestDTO) {
        log.info("pageRequestDTO : " + pageRequestDTO);

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPg() - 1,  // 1페이지가 0이므로 주의
                pageRequestDTO.getSize(),
                Sort.by("pno").descending());

        Page<Product> productPage = productRepository.findByCategory(pageRequestDTO.getCategory(), pageable);

        List<ProductDTO> dtoList = productPage.getContent().stream()
                .map(Product::toDTO)
                .toList();

        int total = (int) productPage.getTotalElements();

        return PageResponseDTO.<ProductDTO>builder()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDTO)
                .total(total)
                .build();
    }


	/*
		application.yml 파일 upload 경로 및 업로드 파일 크기 설정
		
		spring:
			servlet:
				multipart:
					max-file-size: 10MB
		
		file:
			upload:
				path: uploads
	*/

    @Value("${file.upload.path}")
    private String uploadPath;

    public Resource getProductImage(String imageName) {
        return new FileSystemResource(uploadPath + File.separator + imageName);
    }


    public Map<String, String> saveFiles(List<MultipartFile> files)throws RuntimeException{

        if(files == null || files.size() == 0){
            return null;
        }
        Map<String, String> uploadNames = new HashMap<>();
        for (MultipartFile multipartFile : files) {
            String fieldName = multipartFile.getName();
            String originalFilename = multipartFile.getOriginalFilename();
            String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
            String savedName = UUID.randomUUID().toString() + ext;

            Path savePath = Paths.get(uploadPath, savedName);

            try {
                Files.copy(multipartFile.getInputStream(), savePath);
                uploadNames.put(fieldName, savedName);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }

        }//end for
        return uploadNames;
    }

}