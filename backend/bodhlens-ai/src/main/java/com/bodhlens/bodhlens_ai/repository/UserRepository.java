package com.bodhlens.bodhlens_ai.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bodhlens.bodhlens_ai.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

	Optional<User> findByEmail(String email);
	
	boolean existsByEmail(String email);
	
	@Query("""
			SELECT u.email
			FROM User u
			JOIN Document d ON d.user = u
			JOIN ChatSession cs ON cs.document = d
			JOIN ChatMessage cm ON cm.chatSession = cs
			WHERE cm.sender = com.bodhlens.bodhlens_ai.enums.MessageSender.USER
			GROUP BY u.id, u.email
			ORDER BY COUNT(cm) DESC
			""")
	List<String> findMostActiveUsers(Pageable pageable);
}
