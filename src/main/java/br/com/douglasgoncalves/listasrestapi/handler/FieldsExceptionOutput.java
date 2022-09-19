package br.com.douglasgoncalves.listasrestapi.handler;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FieldsExceptionOutput {

	private String name;
	private String message;

}
