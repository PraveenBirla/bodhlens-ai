package com.bodhlens.bodhlens_ai.analytics.dto;

import java.util.List;

public record AnalyticsResponse(

		// total documents
		// total chat sessions
		// total users
		// total questions asked
		// total chunks
		// Average response time
		// average chunks per document
		// Most queried document
		// Most active user
		// Upload trend graph

		long totalDocuments, 
		long totalChatSessions, 
		long totalUsers, 
		long totalQuestionsAsked, 
		long totalChunks,
		long averageResponseTime, 
		long averageChunksPerDocument, 
		String mostQueriedDocument, 
		String mostActiveUser,
		List<UploadTrendDto> uploadTrends
		) {

}
