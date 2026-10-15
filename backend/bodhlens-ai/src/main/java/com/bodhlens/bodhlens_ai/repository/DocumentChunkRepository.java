package com.bodhlens.bodhlens_ai.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bodhlens.bodhlens_ai.entity.DocumentChunk;

public interface DocumentChunkRepository extends JpaRepository<DocumentChunk, UUID> {

	List<DocumentChunk> findByDocumentIdOrderByChunkIndex(UUID documentId);
	
	@Query(value = """
			SELECT * FROM document_chunks WHERE document_id = :documentId ORDER BY embedding <-> CAST(:embedding AS vector) LIMIT 5
			""", nativeQuery = true)
	List<DocumentChunk> findSimilarChunks(UUID documentId, float[] embedding);
}
