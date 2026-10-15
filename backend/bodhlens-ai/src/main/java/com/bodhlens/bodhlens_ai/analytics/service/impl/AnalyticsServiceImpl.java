package com.bodhlens.bodhlens_ai.analytics.service.impl;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.bodhlens.bodhlens_ai.analytics.dto.AnalyticsResponse;
import com.bodhlens.bodhlens_ai.analytics.dto.UploadTrendDto;
import com.bodhlens.bodhlens_ai.analytics.service.AnalyticsService;
import com.bodhlens.bodhlens_ai.entity.ChatMessage;
import com.bodhlens.bodhlens_ai.entity.User;
import com.bodhlens.bodhlens_ai.enums.MessageSender;
import com.bodhlens.bodhlens_ai.repository.ChatMessageRepository;
import com.bodhlens.bodhlens_ai.repository.ChatSessionRepository;
import com.bodhlens.bodhlens_ai.repository.DocumentChunkRepository;
import com.bodhlens.bodhlens_ai.repository.DocumentRepository;
import com.bodhlens.bodhlens_ai.repository.UserRepository;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {
	
	@Autowired
	DocumentRepository documentRepository;
	
	@Autowired
	ChatSessionRepository chatSessionRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ChatMessageRepository chatMessageRepository;
	
	@Autowired
	DocumentChunkRepository documentChunkRepository;

	@Override
	public AnalyticsResponse getAnalytics() {
		
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
		
		long totalDocuments = documentRepository.count();
		long totalChatSessions = chatSessionRepository.count();
		long totalUsers = userRepository.count();
		long totalQuestionsAsked = chatMessageRepository.countUserQuestions();
		long totalChunks = documentChunkRepository.count();
		long averageResponseTime = calculateAverageResponseTime();
		long averageChunksPerDocument = totalDocuments == 0 ? 0 : totalChunks / totalDocuments;
		
		List<String> documents = documentRepository.findMostQueriedDocuments(PageRequest.of(0,1));
		String mostQueriedDocument = documents.isEmpty() ? null : documents.get(0);
		
		List<String> users = userRepository.findMostActiveUsers(PageRequest.of(0, 1));
		String mostActiveUser = users.isEmpty() ? null : users.get(0);
		
		List<Object[]> result = documentRepository.findUploadTrend();
		
		List<UploadTrendDto> uploadTrends = result.stream().map(row -> 
											new UploadTrendDto(row[0].toString(), ((Number) row[1]).longValue())
				).toList();
		
		return new AnalyticsResponse(totalDocuments, 
				totalChatSessions, 
				totalUsers, 
				totalQuestionsAsked, 
				totalChunks, 
				averageResponseTime, 
				averageChunksPerDocument, 
				mostQueriedDocument, 
				mostActiveUser, 
				uploadTrends);
	}
	
	private long calculateAverageResponseTime() {
		List<ChatMessage> messages = chatMessageRepository.findAllOrderBySessionAndCreatedAt();
		long totalResponseTime = 0;
		int responseCount = 0;;
		
		ChatMessage previousMessage = null;
		
		for(ChatMessage currentMessage: messages) {
			
			if(previousMessage != null 
					&& previousMessage.getChatSession().getId().equals(currentMessage.getChatSession().getId())
					&& previousMessage.getSender() == MessageSender.USER
					&& currentMessage.getSender() == MessageSender.AI) {
				
				long responseTime = Duration.between(previousMessage.getCreatedAt(), currentMessage.getCreatedAt()).toMillis();
				totalResponseTime+=responseTime;
				responseCount++;
			}
			
			previousMessage = currentMessage;
		}
		return responseCount == 0 ? 0 : totalResponseTime/responseCount;
	}

}
