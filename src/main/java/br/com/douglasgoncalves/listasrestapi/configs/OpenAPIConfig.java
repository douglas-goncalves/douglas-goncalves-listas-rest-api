package br.com.douglasgoncalves.listasrestapi.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;

@Configuration
public class OpenAPIConfig {

	@Bean
	public OpenAPI springShopOpenAPI() {
		OpenAPI openApi = new OpenAPI();

		Info info = new Info();
		info.title("3ºAvaliação Do Acelera: Lista de Itens");
		info.version("0.0.1");
		info.description("<div>"
				+ "       	<h2>Descrição:<h2>"
				+ "			<h4>Este é o Swagger do ListaApi que é composto por Lista e itens da lista</h4>"
				+ "		  </div>"
				 );

		openApi.info(info);
		openApi.addTagsItem(new Tag().name("Lista:").description("Endpoints do controller das Listas de Itens"));
		openApi.addTagsItem(new Tag().name("Item:").description("Endpoints do controller dos Itens de uma Lista"));
		return openApi;
	}
}
