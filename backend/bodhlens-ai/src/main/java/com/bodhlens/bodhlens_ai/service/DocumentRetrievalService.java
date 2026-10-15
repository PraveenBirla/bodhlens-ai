package com.bodhlens.bodhlens_ai.service;

import java.util.List;
import java.util.UUID;

import com.bodhlens.bodhlens_ai.entity.DocumentChunk;

public interface DocumentRetrievalService {

	List<DocumentChunk> retrieveChunks(UUID documentId, String question);
}
