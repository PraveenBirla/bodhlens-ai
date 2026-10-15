package com.bodhlens.bodhlens_ai.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bodhlens.bodhlens_ai.ai.EmbeddingService;
import com.bodhlens.bodhlens_ai.entity.DocumentChunk;
import com.bodhlens.bodhlens_ai.repository.DocumentChunkRepository;
import com.bodhlens.bodhlens_ai.service.DocumentRetrievalService;

@Service
public class DocumentRetrievalServiceImpl implements DocumentRetrievalService {

	@Autowired
	private DocumentChunkRepository documentChunkRepository;
	
	@Autowired
	private EmbeddingService embeddingService;
	
	@Override
	public List<DocumentChunk> retrieveChunks(UUID documentId, String question) {
		
		List<Float> embeddingList = embeddingService.generateEmbedding(question);
		
		float[] embedding = new float[embeddingList.size()];
		
		for(int i=0; i<embeddingList.size(); i++) {
			embedding[i] = embeddingList.get(i);
		}
		
		return documentChunkRepository.findSimilarChunks(documentId, embedding);
	}

}
