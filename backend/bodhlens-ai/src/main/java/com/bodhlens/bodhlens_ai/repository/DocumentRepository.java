package com.bodhlens.bodhlens_ai.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bodhlens.bodhlens_ai.entity.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, UUID> {

	@Query("""
			SELECT d.fileName
			FROM Document d
			JOIN ChatSession cs ON cs.document = d
			JOIN ChatMessage cm ON cm.chatSession = cs
			WHERE cm.sender = com.bodhlens.bodhlens_ai.enums.MessageSender.USER
			GROUP BY d.id, d.fileName
			ORDER BY COUNT(cm) DESC
			""")
	List<String> findMostQueriedDocuments(Pageable pageable);
	
	@Query(value = """
	        SELECT DATE(created_at) AS uploadDate,
	               COUNT(*) AS uploadCount
	        FROM documents
	        GROUP BY DATE(created_at)
	        ORDER BY uploadDate
	        """, nativeQuery = true)
	List<Object[]> findUploadTrend();
}
