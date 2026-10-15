package com.bodhlens.bodhlens_ai.entity;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
//import com.pgvector.PGvector;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
	    name = "document_chunks",
	    indexes = {
	        @Index(
	            name = "idx_document_chunks_document_id",
	            columnList = "document_id"
	        )
	    }
	)
@Getter
@Setter
public class DocumentChunk extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "document_id")
	@JsonIgnore
	private Document document;
	
	@Column(name = "chunk_index")
	private Integer chunkIndex;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	@JdbcTypeCode(SqlTypes.VECTOR)
	@Column(columnDefinition = "vector(768)")
	private float[] embedding;
}
