package com.college.campuscollab.repository;

import com.college.campuscollab.entity.ContributionRequest;
import com.college.campuscollab.entity.Project;
import com.college.campuscollab.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContributionRequestRepository extends JpaRepository<ContributionRequest, Long> {

    List<ContributionRequest> findByProject(Project project);

    List<ContributionRequest> findByRequestedBy(User user);
}
