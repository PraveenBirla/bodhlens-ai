package com.bodhlens.bodhlens_ai.ai.dto;

import java.util.List;

public record EmbeddingResponse(
		String model,
		List<List<Float>> embeddings
		) {

}
