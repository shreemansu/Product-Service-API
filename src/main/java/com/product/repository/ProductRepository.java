package com.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.product.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	List<Product> getByCatagory(String catagory);
	
	@Query("select p from Product p where p.price between :start and :end")
	List<Product> getByPriceRange(@Param("start") Integer start,@Param("end") Integer end);
}
