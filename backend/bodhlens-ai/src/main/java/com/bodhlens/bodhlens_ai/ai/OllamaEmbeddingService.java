package com.bodhlens.bodhlens_ai.ai;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.bodhlens.bodhlens_ai.ai.dto.EmbeddingRequest;
import com.bodhlens.bodhlens_ai.ai.dto.EmbeddingResponse;

@Service
public class OllamaEmbeddingService implements EmbeddingService {
	
	@Value("${ollama.url}")
	private String ollamaUrl;
	
	@Value("${ollama.embedding-model}")
	private String embeddingModel;
	
	private final RestClient restClient;
	
	public OllamaEmbeddingService(RestClient restClient) {
		this.restClient = restClient;
	}

	@Override
	public List<Float> generateEmbedding(String text) {
		EmbeddingRequest embeddingRequest = new EmbeddingRequest(embeddingModel, text);
		EmbeddingResponse embeddingResponse = restClient.post()
				.uri(ollamaUrl + "/api/embed")
				.body(embeddingRequest)
				.retrieve()
				.body(EmbeddingResponse.class);
		if(embeddingResponse == null || embeddingResponse.embeddings() == null || embeddingResponse.embeddings().isEmpty()) {
			throw new RuntimeException("No embedding returned from Ollama.");
		}
		return embeddingResponse.embeddings().get(0);
	}
	
}
