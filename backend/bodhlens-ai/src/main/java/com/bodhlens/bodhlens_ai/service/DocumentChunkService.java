package com.bodhlens.bodhlens_ai.service;

import java.io.IOException;

import com.bodhlens.bodhlens_ai.entity.Document;

public interface DocumentChunkService {

	void createChunks(Document document) throws IOException;
}
