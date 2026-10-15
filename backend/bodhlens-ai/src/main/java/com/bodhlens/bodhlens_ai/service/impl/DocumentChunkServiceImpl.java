package com.bodhlens.bodhlens_ai.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bodhlens.bodhlens_ai.ai.EmbeddingService;
import com.bodhlens.bodhlens_ai.entity.Document;
import com.bodhlens.bodhlens_ai.entity.DocumentChunk;
import com.bodhlens.bodhlens_ai.repository.DocumentChunkRepository;
import com.bodhlens.bodhlens_ai.service.DocumentChunkService;
import com.bodhlens.bodhlens_ai.util.DocumentTextExtractor;
import com.pgvector.PGvector;

@Service
public class DocumentChunkServiceImpl implements DocumentChunkService {
	
	@Autowired
	DocumentTextExtractor documentTextExtractor;
	
	private static final int CHUNK_SIZE = 1000;
	
	private static final int CHUNK_OVERLAP = 200;
	
	@Autowired
	DocumentChunkRepository documentChunkRepository;
	
	@Autowired
	EmbeddingService embeddingService;

	@Override
	public void createChunks(Document document) throws IOException {
		// 1. extract text
		// 2. split into chunks
		// 3. save chunks
		
		String text = documentTextExtractor.extractText(document);
		List<String> chunks = splitIntoChunks(text);
		
		List<DocumentChunk> chunksList = buildChunks(chunks, document);
		System.out.println("chunksList-"+chunksList);
		documentChunkRepository.saveAll(chunksList);
	}

	private List<String> splitIntoChunks(String text) {
		List<String> chunks = new ArrayList<String>();
		
		int start =0;
		
		while(start < text.length()) {
			int end = Math.min(start + CHUNK_SIZE, text.length());
			chunks.add(text.substring(start, end));
			start+=CHUNK_SIZE-CHUNK_OVERLAP;
		}
		return chunks;
	}
	
	private List<DocumentChunk> buildChunks(List<String> chunks, Document document) {
		List<DocumentChunk> documentChunks = new ArrayList<DocumentChunk>();
		
		for(int i=0; i<chunks.size(); i++) {
			String chunk = chunks.get(i);
			DocumentChunk documentChunk = new DocumentChunk();
			documentChunk.setDocument(document);
			documentChunk.setContent(chunk);
			documentChunk.setChunkIndex(i);	
			documentChunk.setEmbedding(generateEmbeddings(chunk));
			
			documentChunks.add(documentChunk);
		}
		return documentChunks;
	}
	
	private float[] generateEmbeddings(String chunk) {
		List<Float> embedding = embeddingService.generateEmbedding(chunk);
		
		float[] vector = new float[embedding.size()];
		for(int j=0; j<embedding.size(); j++) {
			vector[j] = embedding.get(j);
		}
		System.out.println("Embedding size: " + vector.length);
//		return new PGvector(vector);
		return vector;
	}
}
