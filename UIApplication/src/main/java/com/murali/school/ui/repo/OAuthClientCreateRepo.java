package com.murali.school.ui.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.murali.school.models.OAuthClientDetails;
@Repository
public interface OAuthClientCreateRepo extends JpaRepository<OAuthClientDetails, String>{

}
