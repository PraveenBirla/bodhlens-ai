package com.bodhlens.bodhlens_ai.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bodhlens.bodhlens_ai.entity.ChatMessage;
import com.bodhlens.bodhlens_ai.enums.MessageSender;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, UUID> {

	List<ChatMessage> findByChatSessionIdOrderByCreatedAtAsc(UUID chatSessionId);
	
	long countBySender(MessageSender sender);

	@Query("""
			SELECT COUNT(cm)
			FROM ChatMessage cm 
			WHERE cm.sender = com.bodhlens.bodhlens_ai.enums.MessageSender.USER
			""")
	long countUserQuestions();
	
	@Query("""
			SELECT cm
			FROM ChatMessage cm
			ORDER BY cm.chatSession.id, cm.createdAt
			""")
	List<ChatMessage> findAllOrderBySessionAndCreatedAt();
}
