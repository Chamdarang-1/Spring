
package kr.co.ch09.controller;


import kr.co.ch09.dto.PageRequestDTO;
import kr.co.ch09.dto.PageResponseDTO;
import kr.co.ch09.dto.ProductDTO;
import kr.co.ch09.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@Controller
public class ProductController {

    private final ProductService productService;

    @ResponseBody
    @GetMapping("/product")
    public PageResponseDTO<ProductDTO> list(PageRequestDTO pageRequestDTO){
        log.info("pageRequestDTO : " + pageRequestDTO);

        PageResponseDTO<ProductDTO> pageResponseDTO = productService.list(pageRequestDTO);
        log.info("pageResponseDTO : " + pageResponseDTO);

        return pageResponseDTO;
    }

    @GetMapping("/product/register")
    public String register(){
        return "product/register";
    }

    @ResponseBody
    @PostMapping("/product")
    public ResponseEntity<Map<String, Integer>> register(ProductDTO productDTO){

        log.info(productDTO);

        List<MultipartFile> files = productDTO.getThumbFiles();

        Map<String, String> uploadFileNames = productService.saveFiles(files);
        productDTO.setThumbNames(uploadFileNames);

        int pno = productService.register(productDTO);

        return ResponseEntity.ok(Map.of("pno", pno));
    }

    @ResponseBody
    @GetMapping("/product/image/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName){

        Resource resource = productService.getProductImage(imageName);
        HttpHeaders headers = new HttpHeaders();

        try{
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
        } catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().headers(headers).body(resource);
    }
}