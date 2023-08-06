package com.springreview.webfluxreview;

import com.springreview.webfluxreview.controller.ProductController;
import com.springreview.webfluxreview.dto.ProductDto;
import com.springreview.webfluxreview.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest(ProductController.class)
class WebFluxReviewApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private ProductService service;

	@Test
	public void addProductTest() {
		Mono<ProductDto> productDtoMono = Mono.just(new ProductDto("1", "Mobile", 1, 10000));
		when(service.saveProduct(productDtoMono)).thenReturn(productDtoMono);
		webTestClient.post().uri("/products")
				.body(Mono.just(productDtoMono), ProductDto.class)
				.exchange().expectStatus().isOk();
	}

	@Test
	public void getProductsTest() {
		Flux<ProductDto> productDtoFlux = Flux.just(new ProductDto("1", "Mobile", 1, 10000),
				new ProductDto("101", "TV", 1, 50000));
		when(service.getProducts()).thenReturn(productDtoFlux);
		Flux<ProductDto> responseBody = webTestClient.get().uri("/products")
				.exchange().expectStatus().isOk()
				.returnResult(ProductDto.class)
				.getResponseBody();

		StepVerifier.create(responseBody)
				.expectSubscription()
				.expectNext(new ProductDto("1", "Mobile", 1, 10000))
				.expectNext(new ProductDto("101", "TV", 1, 50000))
				.verifyComplete();
	}

	@Test
	public void getProductTest() {
		Mono<ProductDto> productDtoMono = Mono.just(new ProductDto("1", "Mobile", 1, 10000));
		when(service.getProduct(any())).thenReturn(productDtoMono);

		Flux<ProductDto> responseBody = webTestClient.get().uri("/products/id")
				.exchange()
				.expectStatus().isOk()
				.returnResult(ProductDto.class)
				.getResponseBody();

		StepVerifier.create(responseBody)
				.expectSubscription()
				.expectNextMatches(p -> p.getName().equals("Mobile"))
				.verifyComplete();
	}

	@Test
	public void updateProductTest() {
		Mono<ProductDto> productDtoMono = Mono.just(new ProductDto("1", "Mobile", 1, 10000));
		when(service.updateProduct(productDtoMono, "1")).thenReturn(productDtoMono);

		webTestClient.put().uri("/products/1")
				.body(Mono.just(productDtoMono), ProductDto.class)
				.exchange().expectStatus().isOk();
	}

	@Test
	public void deleteProductTest() {
		given(service.deleteProduct(any())).willReturn(Mono.empty());
		webTestClient.delete().uri("/products/1")
				.exchange().expectStatus().isOk();

	}

}
