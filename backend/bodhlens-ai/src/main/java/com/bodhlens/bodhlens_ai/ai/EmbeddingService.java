package com.bodhlens.bodhlens_ai.ai;

import java.util.List;

public interface EmbeddingService {

	List<Float> generateEmbedding(String text);
}
