package com.linker.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.linker.domain.Link;

@Repository
public interface LinkRepository extends CrudRepository<Link, Long> {
	
	Link findFirstByShortLink(String shortlink);
	
	Link findFirstByOriginalLink(String originalLink);

}
