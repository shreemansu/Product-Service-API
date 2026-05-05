package com.product.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.product.dto.AddProductDto;
import com.product.dto.ApiResponse;
import com.product.dto.ProductResponseDto;
import com.product.entity.Product;
import com.product.mapping.ModelMapper;
import com.product.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1.0/product")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;
	
	private final ModelMapper modelMapper;
	
	@PostMapping
	public ResponseEntity<ApiResponse> addNewproductController(@Valid @RequestBody AddProductDto dto){
		String ServiceResponse=productService.addNewproductService(dto);
		ApiResponse apiResponse=ApiResponse.builder()
				.serviceName("PRODUCT_SERVICE")
				.status(true)
				.type("string")
				.payload(ServiceResponse)
				.build();
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.CREATED); 
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse> getProductByIdController(@PathVariable("id") Long id){
		Product product=productService.getProductByIdService(id);
		ProductResponseDto dto=modelMapper.entityToProductResponseDtoMapper(product);
		ApiResponse response=ApiResponse.builder()
				.serviceName("PRODUCT_SERVICE")
				.status(true)
				.type("object")
				.payload(dto)
				.build();
		return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteProductByIdController(@PathVariable("id") Long id){
		Product product=productService.deleteProductByIdService(id);
		ApiResponse response=ApiResponse.builder()
				.serviceName("PRODUCT_SERVICE")
				.status(true)
				.type("object")
				.payload("Product Deleted!")
				.build();
		return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse> updateProductByIdController(@PathVariable("id") Long id,@RequestBody AddProductDto dto){
		Product product=productService.updateProductByIdService(id, dto);
		ProductResponseDto response=modelMapper.entityToProductResponseDtoMapper(product);
		ApiResponse apiResponse=ApiResponse.builder()
				.serviceName("PRODUCT_SERVICE")
				.status(true)
				.type("object")
				.payload(response)
				.build();
		return ResponseEntity.ok(apiResponse);
	}                                                       
	@GetMapping("/catagory")                              //@PathVariable(required = false)
	public ResponseEntity<ApiResponse> getProductByCatagory(@RequestParam(name = "catagory", required = false, defaultValue = "FMCG") String catagory,@RequestParam(name = "sorting", required = false, defaultValue = "ASC") String sorting){
		List<Product> products=productService.getProductByCatagory(catagory,sorting);
		List<ProductResponseDto> dtos=products.stream().map(p->modelMapper.entityToProductResponseDtoMapper(p)).toList();
		ApiResponse response=ApiResponse.builder()
				.serviceName("PRODUCT_SERVICE")
				.status(true)
				.type("Object Array")
				.payload(dtos)
				.build();
		return ResponseEntity.ok(response);
	}
	@GetMapping("/page")
	public ResponseEntity<ApiResponse> getProductByPageController(@RequestParam(name = "pageNo", required = false, defaultValue = "0") Integer pageNo,@RequestParam(name = "pageSize", required = false, defaultValue = "20") Integer pageSize, @RequestParam(name = "sorting", required = false, defaultValue = "NONE") String sorting){
		List<Product> products=productService.getProductByPageService(pageNo, pageSize, sorting);
		List<ProductResponseDto> dtos=products.stream().map(i->modelMapper.entityToProductResponseDtoMapper(i)).toList();
		ApiResponse apiResponse=ApiResponse.builder()
				.serviceName("PRODUCT_SERVICE")
				.status(true)
				.type("Object Array")
				.payload(dtos)
				.build();
		return ResponseEntity.ok(apiResponse);
	}
	
	@GetMapping("/range")
	public ResponseEntity<ApiResponse> getProductByPriceRangeController(@RequestParam(name = "start", required = false, defaultValue = "0")Integer start,@RequestParam(name = "pageNo", required = false, defaultValue = "30000") Integer end){
		List<Product> products=productService.getProductByPriceRangeService(start, end);
		List<ProductResponseDto> dtos=products.stream().map(i->modelMapper.entityToProductResponseDtoMapper(i)).toList();
		ApiResponse apiResponse=ApiResponse.builder()
				.serviceName("PRODUCT_SERVICE")
				.status(true)
				.type("Object Array")
				.payload(dtos)
				.build();
		return ResponseEntity.ok(apiResponse);
	}
	
	@PatchMapping("/stock/inc/{id}/{amount}")
	public ResponseEntity<ApiResponse> incStockController(@PathVariable("id") Long id,@PathVariable("amount") Integer stockAmount){
		String serviceResponse=productService.incProductStock(id, stockAmount);
		ApiResponse apiResponse=ApiResponse.builder()
				.serviceName("PRODUCT_SERVICE")
				.status(true)
				.type("String")
				.payload(serviceResponse)
				.build();
		return ResponseEntity.ok(apiResponse);
	}
	
	@PatchMapping("/stock/dec/{id}/{amount}")
	public ResponseEntity<ApiResponse> decStockController(@PathVariable("id") Long id,@PathVariable("amount") Integer stockAmount){
		String serviceResponse=productService.decProductStock(id, stockAmount);
		ApiResponse apiResponse=ApiResponse.builder()
				.serviceName("PRODUCT_SERVICE")
				.status(true)
				.type("String")
				.payload(serviceResponse)
				.build();
		return ResponseEntity.ok(apiResponse);
	}
}
